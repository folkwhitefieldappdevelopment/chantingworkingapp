package com.iskcon.folk.app.chantandhear.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.util.LoaderAlertDialog;

import java.io.File;
import java.util.Date;
import java.util.List;

public class HomePageLevelSelectionFragment extends Fragment {
    private int completedRounds;
    private UserDetails userDetails;
    private boolean gotData = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.level_selection, container, false);
        ImageView level1MindFullJapa = view.findViewById(R.id.level1MindFullJapa);
        userDetails = (UserDetails) getActivity().getIntent().getExtras().get("userDetails");
        level1MindFullJapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gotData) {
                    view.animate().setDuration(200).scaleX(1.2f).scaleY(1.2f)
                            .withEndAction(() -> view.animate().setDuration(300).scaleX(1f).scaleY(1f));
                    enterMain(userDetails, getCompletedRounds());
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        this.getUserRoundDetails(userDetails);
        super.onStart();
    }

    private void getUserRoundDetails(UserDetails userDetails) {
        LoaderAlertDialog loaderAlertDialog = new LoaderAlertDialog(this.getActivity());
        loaderAlertDialog.show();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<RoundDataEntity>> typeIndicator = new GenericTypeIndicator<List<RoundDataEntity>>() {
                };
                if (snapshot != null && snapshot.exists()) {
                    setCompletedRounds(snapshot.getValue(typeIndicator).size());
                }
                downloadFile();
                gotData = true;
                loaderAlertDialog.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loaderAlertDialog.close();
            }
        };
        new ChantingDataDao(userDetails).getCurrentDayData(new Date(), userDetails.getId(), valueEventListener);
    }

    private void enterMain(UserDetails userDetails, int completedRounds) {
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        intent.putExtra("userDetails", userDetails);
        intent.putExtra("completedRounds", completedRounds);
        startActivity(intent);
    }

    public int getCompletedRounds() {
        return completedRounds;
    }

    public void setCompletedRounds(int completedRounds) {
        this.completedRounds = completedRounds;
    }

    private void downloadFile() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        File rootDirectory =
                new File(this.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), ".chantAndHear_v1");

        if (!rootDirectory.exists()) {
            rootDirectory.mkdir();
        }

        firebaseStorage.getReference("flip_videos").listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    for (StorageReference storageReference : task.getResult().getItems()) {
                        File videoFilePath = new File(rootDirectory,storageReference.getName() );
                        if (!videoFilePath.exists()) {
                            storageReference.getFile(videoFilePath)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("TAG", e.getMessage(), e);
                                            Toast.makeText(getContext(), "Unable to download the file, please contact support team.",
                                                            Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });
                        }
                    }
                }
            }
        });
    }
}