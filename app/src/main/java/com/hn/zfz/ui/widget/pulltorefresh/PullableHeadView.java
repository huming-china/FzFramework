//package com.hn.zfz.ui.widget.pulltorefresh;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.net.Uri;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.request.ImageRequest;
//import com.facebook.imagepipeline.request.ImageRequestBuilder;
//import com.hn.zfz.fzframework.R;
//
///**
// * 下拉刷新的头部View
// *
// * @author goujiabo
// */
//public class PullableHeadView extends RelativeLayout {
//    private ImageView pullImage;//下拉的箭头
//    //    private ImageView refreshImage;//正在刷新的图标
//    private SimpleDraweeView refreshImage;
//    private TextView stateTextView;// 刷新结果：成功或失败
//    private ImageView stateImage;// 下拉刷新结果图标
//
//
//    private RotateAnimation rotateAnimation;//180度旋转动画
//    private RotateAnimation refreshingAnimation;//旋转动画-（重复）
//
//    public PullableHeadView(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public PullableHeadView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public PullableHeadView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(context);
//    }
//
//    private void initView(Context context) {
//        setBackgroundColor(Color.parseColor(PullableUtil.HEAD_BG_COLOR));
//        rotateAnimation = PullableUtil.rotateReverse();
//        refreshingAnimation = PullableUtil.rotateAlways();
//
//        RelativeLayout headLayout = new RelativeLayout(context);
//        headLayout.setPadding(0, PullableUtil.dip2px(context, 20), 0, PullableUtil.dip2px(context, 20));
//        LayoutParams headLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        headLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
//        addView(headLayout, headLP);
//
//        RelativeLayout headInforLayout = new RelativeLayout(context);
//        LayoutParams headInforLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        headInforLP.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
//        headLayout.addView(headInforLayout, headInforLP);
//        pullImage = new ImageView(context);
//        pullImage.setImageResource(R.drawable.pulltorefresh_pull_icon_big);
//        LayoutParams imagePullLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        imagePullLP.addRule(RelativeLayout.CENTER_VERTICAL, -1);
//        imagePullLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
//        imagePullLP.setMargins(PullableUtil.dip2px(context, 60), 0, 0, 0);
//        headInforLayout.addView(pullImage, imagePullLP);
//
//        //将加载转圈换位gif
//        refreshImage = new SimpleDraweeView(context);
//        Uri uri = Uri.parse("res:// /" + R.drawable.pulltorefresh_load_gif);
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                .build();
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setAutoPlayAnimations(true)
//                .build();
//        refreshImage.setController(controller);
//
////        refreshImage = new ImageView(context);
////        refreshImage.setImageResource(R.drawable.pulltorefresh_loading);
//        refreshImage.setVisibility(View.GONE);
//        LayoutParams refreshLP = new LayoutParams(PullableUtil.dip2px(context, 25), PullableUtil.dip2px(context, 25));
//        refreshLP.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
//        refreshLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT, -1);
//        refreshLP.setMargins(PullableUtil.dip2px(context, 60), 0, 0, 0);
//        headInforLayout.addView(refreshImage, refreshLP);
//
//        stateTextView = new TextView(context);
//        stateTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        stateTextView.setTextColor(Color.parseColor(PullableUtil.HEAD_TEXT_COROR));
//        stateTextView.setText("下拉刷新");
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
//    //根据不同状态，改变头部的布局和显示内容
//    public void setHeadState(PullableUtil.STATE state) {
//        switch (state) {
//            case PULL:
//                stateImage.setVisibility(View.GONE);
//                stateTextView.setText(PullableUtil.REFRESH_BEGAIN);
//                pullImage.clearAnimation();
//                pullImage.setVisibility(View.VISIBLE);
//                break;
//            case RELEASE:
//                stateImage.setVisibility(View.GONE);
//                stateTextView.setText(PullableUtil.REFRESH_RELEASE);
//                pullImage.startAnimation(rotateAnimation);
//                break;
//            case REFRESHING:
//                pullImage.clearAnimation();
//                pullImage.setVisibility(View.INVISIBLE);
//                refreshImage.setVisibility(View.VISIBLE);
////                refreshImage.startAnimation(refreshingAnimation);
//                stateImage.setVisibility(View.GONE);
//                stateTextView.setText(PullableUtil.REFRESHING);
//                break;
//            case SUCCESS:
//                refreshImage.clearAnimation();
//                refreshImage.setVisibility(View.GONE);
//                stateImage.setVisibility(View.VISIBLE);
//                stateTextView.setText(PullableUtil.REFRESH_SUCCESSED);
//                stateImage.setImageResource(R.drawable.pulltorefresh_success);
//                break;
//            case FAILED:
////                refreshImage.clearAnimation();
//                refreshImage.setVisibility(View.GONE);
//                stateImage.setVisibility(View.VISIBLE);
//                stateTextView.setText(PullableUtil.REFRESH_FAILED);
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
//    //设置head的背景颜色
//    public void setHeadBackgroundColor(int color) {
//        setBackgroundColor(color);
//    }
//
//    //设置head的文字颜色
//    public void setHeadTextColor(int color) {
//        stateTextView.setTextColor(color);
//    }
//
//    public void setHeadPullProgress(float progress) {
////        Log.e("PullableHeadView pull progress", progress * 100 + "%");
//    }
//}
