package com.hn.zfz.fzframework.activity;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hn.zfz.fzframework.HomeFramentAdapter;
import com.hn.zfz.fzframework.R;
import com.hn.zfz.fzframework.base.BaseActivity;
import com.hn.zfz.fzframework.fragment.ContentFragment;
import com.hn.zfz.fzframework.fragment.MineFragment;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.ui.fragments.CommunityMainFragment;

public class HomeActivity extends BaseActivity{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private View LeftMenuFrame;
    private HomeFramentAdapter mFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.layout_toolbar);

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
        ContentFragment mContentFragment=new ContentFragment();
        CommunityMainFragment mFeedsFragment = new CommunityMainFragment();
        //设置Feed流页面的返回按钮不可见
        mFeedsFragment.setBackButtonVisibility(View.INVISIBLE);

        MineFragment mMineFragment=new MineFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mFeedsFragment)
                .commitAllowingStateLoss();

//        mFragmentAdapter=new HomeFramentAdapter(getSupportFragmentManager());
//        getSupportFragmentManager().getFragments()
//        viewPager.setAdapter(mFragmentAdapter);
       // CommunityMainFragment mFeedsFragment = new CommunityMainFragment();
        //设置Feed流页面的返回按钮不可见
       // mFeedsFragment.setBackButtonVisibility(View.INVISIBLE);
        //添加并显示Fragment
        //getSupportFragmentManager().beginTransaction().add(R.id.container, mFeedsFragment).commit();

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
}
