package com.ellfors.gankreader.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.ui.fragment.FuliFragment;
import com.ellfors.gankreader.ui.fragment.ReadFragment;
import com.ellfors.gankreader.ui.fragment.SettingFragment;
import com.ellfors.gankreader.ui.fragment.VideoFragment;

import javax.inject.Inject;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends BaseActivity
{
    private static final int PERMISSION_CODE = 100;

    @BindView(R.id.main_drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigation)
    NavigationView mNavigationView;

    @Inject
    ReadFragment readFragment;
    @Inject
    FuliFragment fuliFragment;
    @Inject
    VideoFragment videoFragment;
    @Inject
    SettingFragment settingFragment;

    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    public void initInject()
    {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData()
    {
        /* Permission */
        checkPermission();
        /* initFragment */
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.main_frame,readFragment);
        ft.commit();
        /* NavigationView */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.menu_read:
                        setMenuItemListener(readFragment);
                        break;
                    case R.id.menu_fuli:
                        setMenuItemListener(fuliFragment);
                        break;
                    case R.id.menu_video:
                        setMenuItemListener(videoFragment);
                        break;
                    case R.id.menu_setting:
                        setMenuItemListener(settingFragment);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 侧栏Click监听
     */
    private void setMenuItemListener(BaseFragment fragment)
    {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.main_frame,fragment);
        ft.commit();
        closeDrawer();
    }

    /**
     * 打开抽屉
     */
    public void openDrawer()
    {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    /**
     * 关闭抽屉
     */
    public void closeDrawer()
    {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    /**
     * 申请权限
     */
    private void checkPermission()
    {
        PermissionGen
                .with(this)
                .addRequestCode(PERMISSION_CODE)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults)
    {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PERMISSION_CODE)
    public void doSuccessPermission()
    {
        /* 申请权限成功 */
    }

    @PermissionFail(requestCode = PERMISSION_CODE)
    public void doFailPermission()
    {
        /* 申请权限失败 */
    }

}
