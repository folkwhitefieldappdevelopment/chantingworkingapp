package com.iskcon.folk.app.chantandhear.util;

import android.content.DialogInterface;

public class OpenAlertDialogRqModel {
    private String title;
    private String message;
    private String positiveButtonName;
    private DialogInterface.OnClickListener positiveClickHandler;
    private String negativeButtonName;
    private DialogInterface.OnClickListener negativeClickHandler;

    public OpenAlertDialogRqModel() {

    }

    public OpenAlertDialogRqModel(String message, String positiveButtonName,
                                  DialogInterface.OnClickListener positiveClickHandler) {
        this.message = message;
        this.positiveButtonName = positiveButtonName;
        this.positiveClickHandler = positiveClickHandler;
    }

    public OpenAlertDialogRqModel(String message, String positiveButtonName,
                                  DialogInterface.OnClickListener positiveClickHandler,
                                  String negativeButtonName,
                                  DialogInterface.OnClickListener negativeClickHandler) {
        this.message = message;
        this.positiveButtonName = positiveButtonName;
        this.positiveClickHandler = positiveClickHandler;
        this.negativeButtonName = negativeButtonName;
        this.negativeClickHandler = negativeClickHandler;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPositiveButtonName() {
        return positiveButtonName;
    }

    public void setPositiveButtonName(String positiveButtonName) {
        this.positiveButtonName = positiveButtonName;
    }

    public DialogInterface.OnClickListener getPositiveClickHandler() {
        return positiveClickHandler;
    }

    public void setPositiveClickHandler(DialogInterface.OnClickListener positiveClickHandler) {
        this.positiveClickHandler = positiveClickHandler;
    }

    public String getNegativeButtonName() {
        return negativeButtonName;
    }

    public void setNegativeButtonName(String negativeButtonName) {
        this.negativeButtonName = negativeButtonName;
    }

    public DialogInterface.OnClickListener getNegativeClickHandler() {
        return negativeClickHandler;
    }

    public void setNegativeClickHandler(DialogInterface.OnClickListener negativeClickHandler) {
        this.negativeClickHandler = negativeClickHandler;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OpenAlertDialogRqModel{");
        sb.append("title='").append(title).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", positiveButtonName='").append(positiveButtonName).append('\'');
        sb.append(", positiveClickHandler=").append(positiveClickHandler);
        sb.append(", negativeButtonName='").append(negativeButtonName).append('\'');
        sb.append(", negativeClickHandler=").append(negativeClickHandler);
        sb.append('}');
        return sb.toString();
    }
}