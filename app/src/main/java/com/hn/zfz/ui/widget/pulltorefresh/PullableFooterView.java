//package com.hn.zfz.ui.widget.pulltorefresh;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
///**
// * 上拉加载更多底部view
// *
// * @author goujiabo
// */
//public class PullableFooterView extends RelativeLayout {
//    private ImageView pullImage;// 上拉的箭头
//    private ImageView loadingImage;// 正在加载的图标
//    private TextView stateTextView;// 加载结果：成功或失败
//    private ImageView stateImage;// 加载结果图标
//
//    private RotateAnimation rotateAnimation;//180度旋转动画
//    private RotateAnimation refreshingAnimation;//旋转动画-（重复）
//
//    public PullableFooterView(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public PullableFooterView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public PullableFooterView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(context);
//    }
//
//    private void initView(Context context) {
//        setBackgroundColor(Color.parseColor(PullableUtil.FOOTER_BG_COLOR));
//        rotateAnimation = PullableUtil.rotateReverse();
//        refreshingAnimation = PullableUtil.rotateAlways();
//
//        RelativeLayout headLayout = new RelativeLayout(context);
//        headLayout.setPadding(0, PullableUtil.dip2px(context, 20), 0, PullableUtil.dip2px(context, 20));
//        LayoutParams headLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        headLP.addRule(RelativeLayout.ALIGN_PARENT_TOP, -1);
//        addView(headLayout, headLP);
//
//        RelativeLayout headInforLayout = new RelativeLayout(context);
//        LayoutParams headInforLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        headInforLP.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
//        headLayout.addView(headInforLayout, headInforLP);
//
//        pullImage = new ImageView(context);
//        pullImage.setImageResource(R.drawable.pulltorefresh_pullup_icon_big);
//        LayoutParams imagePullLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        imagePullLP.addRule(RelativeLayout.CENTER_VERTICAL, -1);
//        imagePullLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
//        imagePullLP.setMargins(PullableUtil.dip2px(context, 60), 0, 0, 0);
//        headInforLayout.addView(pullImage, imagePullLP);
//
//        loadingImage = new ImageView(context);
//        loadingImage.setImageResource(R.drawable.pulltorefresh_loading);
//        loadingImage.setVisibility(View.GONE);
//        LayoutParams refreshLP = new LayoutParams(PullableUtil.dip2px(context, 25), PullableUtil.dip2px(context, 25));
//        refreshLP.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
//        refreshLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
//        refreshLP.setMargins(PullableUtil.dip2px(context, 60), 0, 0, 0);
//        headInforLayout.addView(loadingImage, refreshLP);
//
//        stateTextView = new TextView(context);
//        stateTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        stateTextView.setTextColor(Color.parseColor(PullableUtil.FOOTER_TEXT_COROR));
//        stateTextView.setText("上拉加载");
//        LayoutParams stateTextViewLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        stateTextViewLP.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
//        headInforLayout.addView(stateTextView, stateTextViewLP);
//
//        stateImage = new ImageView(context);
//        stateImage.setVisibility(View.GONE);
//        LayoutParams stateLP = new LayoutParams(PullableUtil.dip2px(context, 25), PullableUtil.dip2px(context, 25));
//        stateLP.addRule(RelativeLayout.CENTER_VERTICAL, -1);
//        stateLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
//        stateLP.setMargins(PullableUtil.dip2px(context, 60), 0, 0, 0);
//        headInforLayout.addView(stateImage, stateLP);
//    }
//
//    //根据不同状态，改变底部加载的布局和显示内容
//    public void setFooterState(PullableUtil.STATE state) {
//        switch (state) {
//            case PULL:
//                stateImage.setVisibility(View.GONE);
//                stateTextView.setText(PullableUtil.LOAD_BEGAIN);
//                pullImage.clearAnimation();
//                pullImage.setVisibility(View.VISIBLE);
//                break;
//            case RELEASE:
//                stateImage.setVisibility(View.GONE);
//                stateTextView.setText(PullableUtil.LOAD_RELEASE);
//                pullImage.startAnimation(rotateAnimation);
//                break;
//            case LOADING:
//                pullImage.clearAnimation();
//                pullImage.setVisibility(View.INVISIBLE);
//                loadingImage.setVisibility(View.VISIBLE);
//                loadingImage.startAnimation(refreshingAnimation);
//                stateImage.setVisibility(View.GONE);
//                stateTextView.setText(PullableUtil.LOADING);
//                break;
//            case SUCCESS:
//                loadingImage.clearAnimation();
//                loadingImage.setVisibility(View.GONE);
//                stateImage.setVisibility(View.VISIBLE);
//                stateTextView.setText(PullableUtil.LOAD_SUCCESSED);
//                stateImage.setImageResource(R.drawable.pulltorefresh_success);
//                break;
//            case FAILED:
//                loadingImage.clearAnimation();
//                loadingImage.setVisibility(View.GONE);
//                stateImage.setVisibility(View.VISIBLE);
//                stateTextView.setText(PullableUtil.LOAD_FAILED);
//                stateImage.setImageResource(R.drawable.pulltorefresh_faild);
//                break;
//            default:
//                break;
//        }
//    }
//
//    //移除动画
//    public void removeAnimation() {
//        pullImage.clearAnimation();
//    }
//
//    //设置Footer的背景颜色
//    public void setFooterBackgroundColor(int color) {
//        setBackgroundColor(color);
//    }
//
//    //设置Footer的文字颜色
//    public void setFooterTextColor(int color) {
//        stateTextView.setTextColor(color);
//    }
//
//    public void setFooterPullProgress(float progress) {
////		Log.e("PullableFooterView load progress", progress*100+"%");
//    }
//}
