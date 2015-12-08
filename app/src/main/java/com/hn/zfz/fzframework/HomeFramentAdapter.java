//package com.hn.zfz.fzframework;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.util.ArrayMap;
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.hn.zfz.fzframework.base.BaseFragment;
//import com.hn.zfz.fzframework.fragment.ContentFragment;
//import com.umeng.comm.ui.fragments.CommunityMainFragment;
//
//import java.util.ArrayList;
//
///**
// * Created by huming on 2015/12/2.
// */
//public class HomeFramentAdapter extends PagerAdapter{
//        private FragmentManager fm = null;
//        private ArrayList<Fragment> fragmentList;
//        //记录是否是首次显示
//        ArrayMap<Integer, Boolean> isFirstVisbles = new ArrayMap<Integer, Boolean>(3);
//
//        public HomeFramentAdapter(FragmentManager fm) {
//            this.fm = fm;
//            this.fragmentList = new ArrayList<>(3);
//            fragmentList.add(new ContentFragment());
//            CommunityMainFragment mFeedsFragment = new CommunityMainFragment();
//            //设置Feed流页面的返回按钮不可见
//            mFeedsFragment.setBackButtonVisibility(View.INVISIBLE);
//            fragmentList.add(mFeedsFragment);
//            fragmentList.add(new MineFragment());
//            for (int i = 0; i < 3; i++) {
//                isFirstVisbles.put(i, false);
//            }
//        }
//
//        @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
//            // TODO Auto-generated method stub
//            if (isFirstVisbles.get(position) == false) {
//        /* 这里是首次调用 */
//                Fragment frag = fragmentList.get(position);
//                isFirstVisbles.put(position, true);// 设置为不是首次
//                if(frag instanceof BaseFragment)
//                ((BaseFragment)frag).initFristData();// 加载数据
//            }
//        }
//
//        @Override public int getCount() {
//            // TODO Auto-generated method stub
//            return 3;
//        }
//
//        @Override public boolean isViewFromObject(View arg0, Object arg1) {
//            // TODO Auto-generated method stub
//            return arg0 == arg1;
//        }
//
//        @Override public Object instantiateItem(ViewGroup container, int position) {
//            Fragment fragment = fragmentList.get(position);
//            if (!fragment.isAdded()) { // 如果fragment还没有added
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.add(fragment, fragment.getClass().getSimpleName());
//                ft.commit();
//                /**
//                 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
//                 * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
//                 * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
//                 */
//                fm.executePendingTransactions();
//            }
//            if (fragment.getView().getParent() == null) {
//                container.addView(fragment.getView()); // 为viewpager增加布局
//            }
//
//            return fragment.getView();
//        }
//
//        @Override public void destroyItem(ViewGroup container, int position, Object object) {
//            // TODO Auto-generated method stub
//            // super.destroyItem(container, position, object);
//            container.removeView(fragmentList.get(position).getView());
//            object = null;
//        }
//}
