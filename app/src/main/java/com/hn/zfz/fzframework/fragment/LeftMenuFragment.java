package com.hn.zfz.fzframework.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseFragment;
import com.hn.zfz.ui.widget.convenientbanner.CBPageAdapter;
import com.hn.zfz.ui.widget.convenientbanner.CBViewHolderCreator;
import com.hn.zfz.ui.widget.convenientbanner.ConvenientBanner;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by huming on 2015/12/2.
 */
public class LeftMenuFragment extends BaseFragment implements View.OnClickListener{
    private TextView tvUsername;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_left_menu,null);
        init(view);
        return view;
    }
    private void init(View view){
        //登录成功后可以用该方法判断用户时候已经登录
        tvUsername= (TextView) view.findViewById(R.id.tv_username);
        button= (Button) view.findViewById(R.id.btn_login_unlogin);
        view.findViewById(R.id.layout_exam).setOnClickListener(this);
        if(CommonUtils.isLogin(getActivity())){
            tvUsername.setText(CommConfig.getConfig().loginedUser.name);
            button.setText("注销");
        }else{
            tvUsername.setVisibility(View.GONE);
            button.setText("登录");
        }

    }

    private void startApp(String appPackageName) {
        try{
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(appPackageName);
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(getActivity(), "没有安装", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_exam:
                startApp("com.bcedu.exam");
                break;
        }
    }
}
