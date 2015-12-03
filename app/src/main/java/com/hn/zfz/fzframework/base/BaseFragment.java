package com.hn.zfz.fzframework.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hn.zfz.fzframework.R;


public class BaseFragment extends Fragment {
    public boolean isActive=false;
    //public ActivityState activityState = ActivityState.DESTROY;


    @Override
    public void onDetach() {
        super.onDetach();
        isActive=false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isActive=true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showToast(String msg){
        if(isActive)
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    protected void showToast(int resId){
        if(isActive)
            Toast.makeText(getActivity(),resId,Toast.LENGTH_SHORT).show();
    }
    protected void startActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }
    protected void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void initFristData(){};

}
