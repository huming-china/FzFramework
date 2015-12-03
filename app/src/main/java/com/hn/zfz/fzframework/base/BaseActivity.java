package com.hn.zfz.fzframework.base;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity{

    /**
     * 当前Activity状态
     */
//    public static enum ActivityState {
//        RESUME, PAUSE, STOP, DESTROY
//    }
    public boolean isActive=false;
    //public ActivityState activityState = ActivityState.DESTROY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isActive=true;
    }

    protected void showToast(String msg){
        if(isActive)
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    protected void showToast(int resId){
        if(isActive)
            Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
        }
    protected void startActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }
    protected void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive=false;
    }
}
