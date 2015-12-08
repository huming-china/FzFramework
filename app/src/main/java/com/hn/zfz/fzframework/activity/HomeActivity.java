package com.hn.zfz.fzframework.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseActivity;
import com.hn.zfz.fzframework.fragment.ContentFragment;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.MessageCount;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.activities.NotificationActivity;
import com.umeng.comm.ui.fragments.CommunityMainFragment;
import com.umeng.comm.ui.fragments.FindFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener ,FindFragment.OnMessageListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private View LeftMenuFrame;
    private FragmentManager mFragmentManager;
    private ContentFragment mContentFragment;
    private CommunityMainFragment mFeedsFragment;
    private FindFragment mFindFragment;
    private TextView tvTitle;
    private TextView tvRight;
    private View mNotifyBadgeView;
    private MessageCount mUnReadMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.layout_toolbar);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvRight= (TextView) findViewById(R.id.tv_right);
        tvRight.setOnClickListener(tab3listener);
        // 未读系统通知的红点
        mNotifyBadgeView = findViewById(R.id.umeng_comm_badge_view);

        LeftMenuFrame=findViewById(R.id.drawer_left);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.app_name,
                R.string.action_settings) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        CommunitySDK mCommSDK = CommunityFactory.getCommSDK(getApplicationContext());
        //初始化sdk，请传递ApplicationContext
        mCommSDK.initSDK(getApplicationContext());

        //Fragment
        mContentFragment=new ContentFragment();
        mFeedsFragment = new CommunityMainFragment();
        //设置Feed流页面的返回按钮不可见
        mFeedsFragment.setBackButtonVisibility(View.INVISIBLE);


        mFindFragment=new FindFragment();
        Bundle bundle=new Bundle();
        CommUser user=CommConfig.getConfig().loginedUser;
        if (user == null) {// 来自开发者外部调用的情况
            bundle.putParcelable(Constants.TAG_USER, CommConfig.getConfig().loginedUser);
        } else {
            bundle.putParcelable(Constants.TAG_USER, user);
        }
        String mContainerClass = getClass().getName();
        bundle.putString(Constants.TYPE_CLASS, mContainerClass);
        mFindFragment.setArguments(bundle);
        mFragmentManager=getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.frameLayout, mContentFragment)
                .add(R.id.frameLayout, mFeedsFragment)
                .add(R.id.frameLayout, mFindFragment)
                .commit();
        showFragment(1);


        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        findViewById(R.id.tab3).setOnClickListener(this);
    }
   private void showFragment(int position){
       showActionBar(position);
       FragmentTransaction mTransaction= mFragmentManager.beginTransaction();
       switch (position){
           case 1:
                mTransaction.show(mContentFragment).hide(mFeedsFragment).hide(mFindFragment).commit();
               break;
           case 2:
              // mTransaction .replace(R.id.frameLayout, mFeedsFragment).commit();
               mTransaction.hide(mContentFragment).show(mFeedsFragment).hide(mFindFragment).commit();
               break;
           case 3:
              // mTransaction.replace(R.id.frameLayout, mFindFragment).commit();
               mTransaction.hide(mContentFragment).hide(mFeedsFragment).show(mFindFragment).commit();
               break;
       }
   }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(LeftMenuFrame)) {
                mDrawerLayout.closeDrawer(LeftMenuFrame);
            } else {
                mDrawerLayout.openDrawer(LeftMenuFrame);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab1:
                showFragment(1);
                break;
            case R.id.tab2:
                showFragment(2);
                break;
            case R.id.tab3:
                showFragment(3);
                break;
        }
    }
    //Tab3
    private void showActionBar(int position){
        switch (position){
            case 1:
                tvRight.setVisibility(View.INVISIBLE);
                mNotifyBadgeView.setVisibility(View.INVISIBLE);
                tvTitle.setText("资讯");
                break;
            case 2:
                tvRight.setVisibility(View.INVISIBLE);
                mNotifyBadgeView.setVisibility(View.INVISIBLE);
                tvTitle.setText("社区");
                break;
            case 3:
                tvRight.setVisibility(View.VISIBLE);
                tvTitle.setText("发现");
                // 右上角的通知

                setupUnReadNotifyBadge(mUnReadMsg);
                break;
        }

    }

    View.OnClickListener tab3listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tvRightId=ResFinder.getId("tv_right");
            if(v.getId()==tvRightId){
                Intent intent = new Intent(v.getContext(), NotificationActivity.class);
                intent.putExtra(Constants.USER, CommConfig.getConfig().loginedUser);
                startActivity(intent);
                return;
            }
        }
    };

    /**
     * 设置通知红点</br>
     */
    private void setupUnReadNotifyBadge(MessageCount mUnReadMsg) {
        if (mUnReadMsg!=null&&mUnReadMsg.unReadNotice > 0) {
            mNotifyBadgeView.setVisibility(View.VISIBLE);
        } else {
            mNotifyBadgeView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onMessage(MessageCount mUnReadMsg) {
        this.mUnReadMsg=mUnReadMsg;
        setupUnReadNotifyBadge(mUnReadMsg);
    }
}
