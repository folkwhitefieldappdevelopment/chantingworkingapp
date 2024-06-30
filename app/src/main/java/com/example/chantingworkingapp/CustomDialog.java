package com.example.chantingworkingapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);

        // Set dialog position
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM| Gravity.START;
        layoutParams.x = 30; // Adjust the X position as needed
        layoutParams.y = 30; // Adjust the Y position as needed
        getWindow().setAttributes(layoutParams);

        // Customize your dialog layout
        TextView dialogText = findViewById(R.id.dialogText);
        dialogText.setText("Do you want to change your Level?");

        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 'Yes' button click

                dismiss(); // Close the dialog
                // Add your logic for 'Yes' action
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle 'No' button click
                dismiss(); // Close the dialog
                // Add your logic for 'No' action
            }
        });
    }
}
