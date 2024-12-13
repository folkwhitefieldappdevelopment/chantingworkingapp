package com.iskcon.folk.app.chantandhear.homepage;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iskcon.folk.app.chantandhear.MainActivity;
import com.iskcon.folk.app.chantandhear.R;
import com.iskcon.folk.app.chantandhear.constant.ApplicationConstants;
import com.iskcon.folk.app.chantandhear.dao.ChantingDataDao;
import com.iskcon.folk.app.chantandhear.history.model.RoundDataEntity;
import com.iskcon.folk.app.chantandhear.model.UserDetails;
import com.iskcon.folk.app.chantandhear.util.CommonUtils;
import com.iskcon.folk.app.chantandhear.util.LoaderAlertDialog;
import com.iskcon.folk.app.chantandhear.util.OpenAlertDialogRqModel;

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
        new ChantingDataDao(userDetails).get(new Date(), userDetails.getId(), valueEventListener);
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

        for (int i = 1; i <= ApplicationConstants.KRISHNA_VIDEO_NO_OF_FILES_TO_DOWNLOAD.getConstantValue(Integer.class); i++) {
            String videoFileName = "sample_krishna_images_" + i + ".mp4";
            File videoFilePath = new File(rootDirectory,videoFileName );
            StorageReference storageReference = firebaseStorage.getReference("flip_videos").child(videoFileName);
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