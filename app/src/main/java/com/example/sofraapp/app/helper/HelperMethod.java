package com.example.sofraapp.app.helper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sofraapp.R;
import com.example.sofraapp.app.ui.fragment.splashAndUserCycle.LoginFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.sofraapp.app.ui.activity.MainActivity.toolbar;

public class HelperMethod {
   private static CountDownTimer countDownTimers;
    public static final String API_KEY = "API_KEY";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String GET_DATA = "get_data";

    //This method for handle Fragments
    public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, TextView toolbar, String title, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
      //  transaction.commit();
        // for change from commit() because don't happen Error
        //   java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        transaction.commitAllowingStateLoss();

        if (toolbar != null) {
            toolbar.setText(title);
        }


    }


        public static void replece(Fragment fragment, FragmentManager fragmentManager, int id, Toolbar toolbar, String title, SaveData saveData) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putParcelable(GET_DATA, saveData);
            fragment.setArguments(bundle);
            transaction.replace(id, fragment);
            transaction.addToBackStack(null);
            // for change from commit() because don't happen Error
            //   java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
            transaction.commitAllowingStateLoss();

            if (toolbar != null) {
                toolbar.setTitle(title);
            }


        }

    // This method for check Do the internet is available or not ?
    public static boolean isNetworkConnected(Context context, View view) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        } else {
            Snackbar.make(view, context.getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
            return false;

        }
    }

    // This method for handle Activity
    public static void startActivity(Context context, Class<?> toActivity, String getAPI) {
        Intent startActivity = new Intent(context, toActivity);
        startActivity.putExtra(API_KEY, getAPI);
        startActivity.putExtra(API_KEY, getAPI);

        context.startActivity(startActivity);
    }

    // This method for handle Activity
    public static void startActivity(Context context, Class<?> toActivity, SaveData saveData) {
        Intent startActivity = new Intent(context, toActivity);
        startActivity.putExtra(GET_DATA, saveData);
        context.startActivity(startActivity);
    }

    //This method for set time for Reset the password
    public static void startCountdownTimer(final Context context, final View view, final FragmentManager fragmentManager, final TextView textView, final SaveData saveData) {
        countDownTimers = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(context.getString(R.string.remind_time) + " " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Snackbar.make(view, context.getString(R.string.time_out), Snackbar.LENGTH_LONG).show();
                LoginFragment loginFragment = new LoginFragment();
                replece(loginFragment, fragmentManager, R.id.Cycle_Home_contener, toolbar, context.getString(R.string.login), saveData);
            }
        };
        countDownTimers.start();
    }
    public static void stopCountdownTimer(){
        if (countDownTimers !=null){
            countDownTimers.cancel();
            countDownTimers= null;
        }
    }

    //Calender
    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        DecimalFormat mFormat = new DecimalFormat("00");
                        if (!data1.getMonth().equals(mFormat.format(Double.valueOf(selectedMonth)))) {
                            String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
                            data1.setDate_txt(data);
                            data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                            data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                            data1.setYear(String.valueOf(selectedYear));
                            text_view_data.setText(data);
                        }
                    }
                }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()), Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }

    //This method for show data in Spinner

    public static void showGovernorates(ArrayList<String> date, Context context, Spinner spinner) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, date);

        spinner.setAdapter(arrayAdapter);

    }

    //This method for Exit App
    public static void closeApp(Context context) {

        Intent exitAppIntent = new Intent(Intent.ACTION_MAIN);
        exitAppIntent.addCategory(Intent.CATEGORY_HOME);
        exitAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(exitAppIntent);
    }

    //This method for make Call
    public static void makePhoneCall(Context context, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phone));
        context.startActivity(callIntent);

    }

    /*
        public static void removeFragment(AppCompatActivity activity) {
            for (int j = 0; j < activity.getSupportFragmentManager().getFragments().size(); j++) {
                try {
                    SupportRequestManagerFragment fragment = (SupportRequestManagerFragment) activity.getSupportFragmentManager().getFragments().get(j);

                    activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                    break;
                } catch (Exception e) {

                }
            }
        }*/
    public static void openWebSite(Context context, String url) {
        Uri UrlLink = Uri.parse(url);
        Intent webSite = new Intent(Intent.ACTION_VIEW, UrlLink);
        context.startActivity(webSite);
    }

    public static void getReating(int rate, RatingBar ratingBar){
        switch (rate){
            case 0:
                ratingBar.setRating(0);
                break;
            case 1:
                ratingBar.setRating(1);
                break;
            case 2:
                ratingBar.setRating(2);
                break;
            case 3:
                ratingBar.setRating(3);
                break;

            case 4:
                ratingBar.setRating(4);
                break;
            case 5:
                ratingBar.setRating(5);
                break;
            default:
        }
    }

    //method for convert picture
    public static byte[] imageViewToByte(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }


    public static void openAlbum(int Counter, Context context, final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {
        Album album = new Album();
        Album.initialize(AlbumConfig.newBuilder(context)
             //   .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());
        album.image(context)// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .columnCount(3) // The number of columns in the page list.
                .selectCount(Counter)  // Choose up to a few images.
                .camera(true) // Whether the camera appears in the Item.
                .checkedList(ImagesFiles) // To reverse the list.
                .widget(
                        Widget.newLightBuilder(context)
                                .title("")
                                .statusBarColor(Color.WHITE) // StatusBar color.
                                .toolBarColor(Color.WHITE) // Toolbar color.
                                .navigationBarColor(Color.WHITE) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector(Color.BLUE, Color.GREEN) // Image or video selection box.
                                .bucketItemCheckSelector(Color.RED, Color.YELLOW) // Select the folder selection box.
                                .build()
                )
                .onResult(action)
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
// The user canceled the operation.
                    }
                })
                .start();
    }

}
