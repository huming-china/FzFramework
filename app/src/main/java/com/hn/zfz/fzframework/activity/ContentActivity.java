package com.hn.zfz.fzframework.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.avos.avoscloud.AVQuery;
import com.hn.zfz.bean.avosmodel.AVNews;
import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseActivity;

/**
 * Created by huming on 2016/1/4.
 */
public class ContentActivity extends BaseActivity{
    private AVNews avNews;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_content);
    }
    private void initIntent(){
        avNews=getIntent().getExtras().getParcelable("object");


    }
}
