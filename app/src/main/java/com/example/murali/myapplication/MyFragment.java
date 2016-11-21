package com.example.murali.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by murali on 11/18/2016.
 */
public  class MyFragment extends android.support.v4.app.Fragment {


    MainActivity.MyTask myTask;
    ResultsCallBack callBack;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack= (ResultsCallBack) context;
        if(myTask!=null){

            myTask.onAttach(callBack);

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack=null;
        if(myTask!=null){
            myTask.onDetach();
        }
    }

    public void startTask(){
        if(myTask!=null){

            myTask.cancel(true);

        }else{
            myTask = new MainActivity.MyTask(callBack);
            myTask.execute();


        }
    }
}

