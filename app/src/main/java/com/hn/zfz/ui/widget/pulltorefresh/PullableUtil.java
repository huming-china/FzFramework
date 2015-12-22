//package com.hn.zfz.ui.widget.pulltorefresh;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.view.animation.Animation;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.RotateAnimation;
//
//public class PullableUtil {
//    public static final String HEAD_BG_COLOR = "#ffffff";//顶部下拉的布局的默认背景色
//    public static final String HEAD_TEXT_COROR = "#666666";//顶部下拉的默认文字颜色
//    public static final String FOOTER_BG_COLOR = "#ffffff";//底部上拉的默认背景色
//    public static final String FOOTER_TEXT_COROR = "#666666";//底部上拉的默认文字颜色
//
//    public enum STATE {PULL, RELEASE, REFRESHING, LOADING, SUCCESS, FAILED} //刷新状态，状态分别对应：拉、松开、刷新中、加载中、成功、失败
//    public enum DIRECTION {HEAD_ONLY, BOTTOM_ONLY, BOTH, DISABLE} //刷新和加载模式，分别对应：仅头部可以刷新、仅底部可以上拉、上拉均可、上下均不可
//
//    public static final String REFRESH_SUCCESSED = "刷新成功";
//    public static final String REFRESHING = "正在刷新...";
//    public static final String REFRESH_FAILED = "刷新失败";
//    public static final String REFRESH_BEGAIN = "下拉刷新";
//    public static final String REFRESH_RELEASE = "松开立即刷新";
//
//    public static final String LOAD_SUCCESSED = "加载成功";
//    public static final String LOADING = "正在加载...";
//    public static final String LOAD_FAILED = "加载失败";
//    public static final String LOAD_BEGAIN = "上拉加载更多";
//    public static final String LOAD_RELEASE = "松开立即加载";
//
//    /**
//     * DP转换为像素值
//     */
//    public static int dip2px (Context context, float dipValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dipValue * scale + 0.5f);
//    }
//
//    /**
//     * 像素转换为DP值
//     */
//    public static int px2dip (Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//    /**
//     * 从文件目录去加载图片，避免对资源的过渡依赖
//     *
//     * @param targetClass
//     * @param src         文件的目录地址
//     * @return
//     */
//    public static Bitmap getBitmapFromSrc (Class<?> targetClass, String src) {
//        Bitmap bit = null;
//        try {
//            bit = BitmapFactory.decodeStream(targetClass.getResourceAsStream(src));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bit;
//    }
//
//    //获取翻转动画
//    public static RotateAnimation rotateReverse () {
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(100);
//        rotateAnimation.setFillAfter(true);
//        rotateAnimation.setRepeatCount(0);
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        return rotateAnimation;
//    }
//
//    //获取旋转动画
//    public static RotateAnimation rotateAlways () {
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(1500);
//        rotateAnimation.setFillAfter(true);
//        rotateAnimation.setRepeatCount(-1);
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        return rotateAnimation;
//    }
//
//}
