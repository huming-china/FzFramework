package com.hn.zfz.fzframework.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hn.zfz.bean.avosmodel.AVNews;
import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseFragment;
import com.hn.zfz.ui.widget.convenientbanner.CBPageAdapter;
import com.hn.zfz.ui.widget.convenientbanner.CBViewHolderCreator;
import com.hn.zfz.ui.widget.convenientbanner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huming on 2015/12/2.
 */
public class ContentFragment extends BaseFragment{
    private ConvenientBanner convenientBanner;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_content,null);
        init(view);
        return view;
    }
    private void init(View view){
        convenientBanner= (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        ArrayList<Integer> localImages= new ArrayList<Integer>();
        localImages.add(R.drawable.default_photo);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                },localImages )
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.default_photo, R.drawable.ic_launcher})
                        //设置指示器的方向
                //.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                        //设置翻页的效果，不需要翻页效果可用不设
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
//        convenientBanner.setManualPageable(false);//设置不能手动影响
        RecyclerView mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        get();
    }

    public class LocalImageHolderView implements CBPageAdapter.Holder<Integer>{
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
    private void get(){
        AVQuery<AVNews> query = new AVQuery<AVNews>("News");
        //query.whereEqualTo("pubUser", "LeanCloud官方客服");
        query.findInBackground(new FindCallback<AVNews>() {
            public void done(List<AVNews> avObjects, AVException e) {
                if (e == null) {
                    Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                   // avObjects；；
                } else {
                    Log.d("失败", "查询错误: " + e.getMessage());
                }
            }
        });
    }

}
