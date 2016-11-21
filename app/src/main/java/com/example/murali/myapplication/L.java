package com.example.murali.myapplication;

import android.content.Context;
import android.media.MediaCodecInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by murali on 11/18/2016.
 */
public class L {

    Context context;

    public void toast(String message){
        Toast.makeText(context,""+message,Toast.LENGTH_LONG).show();
    }
    public void logMessage(String message){
        Log.d("murali",message);

    }
}
