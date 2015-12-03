package com.hn.zfz.fzframework.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseFragment;

/**
 * Created by huming on 2015/12/2.
 */
public class ContentFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content,container);
    }
}
