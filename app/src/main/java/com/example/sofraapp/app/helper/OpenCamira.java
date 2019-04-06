package com.example.sofraapp.app.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.example.sofraapp.R;

public class OpenCamira {
    public static final int GALARY = 0;
    public static final int CAMIRA = 1;

    public static void takePhotoFromCamira(Activity activity) {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takeIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takeIntent, CAMIRA);

        }
    }
    public static void choosePhotoFromGalary(Activity activity) {
        Intent galaryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galaryIntent, GALARY);
    }
    public static void showPictureDialog(final Activity activity) {
        AlertDialog.Builder pitureDialog = new AlertDialog.Builder(activity);
        pitureDialog.setTitle(activity.getString(R.string.select_action));
        String[] pictureDialogItems = {activity.getString(R.string.select_galary), activity.getString(R.string.capture_camira)};
        pitureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGalary(activity);
                        break;
                    case 1:
                        takePhotoFromCamira(activity);
                        break;
                }
            }
        });
        pitureDialog.show();
    }

}
