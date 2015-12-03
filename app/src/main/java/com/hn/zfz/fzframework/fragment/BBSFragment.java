package com.hn.zfz.fzframework.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseFragment;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;

/**
 * Created by huming on 2015/12/2.
 */
public class BBSFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 获取CommunitySDK实例, 参数1为Context类型
                CommunitySDK mCommSDK = CommunityFactory.getCommSDK(getActivity());
// 打开微社区的接口, 参数1为Context类型
                mCommSDK.openCommunity(getActivity());
            }
        },3000);
        return inflater.inflate(R.layout.fragment_content, container);
    }
    Handler mHandler=new Handler();

}
