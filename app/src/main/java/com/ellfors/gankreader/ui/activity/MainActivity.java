package com.ellfors.gankreader.ui.activity;

import android.Manifest;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.app.AppConfig;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.ui.fragment.AboutFragment;
import com.ellfors.gankreader.ui.fragment.FuliFragment;
import com.ellfors.gankreader.ui.fragment.LikeFragment;
import com.ellfors.gankreader.ui.fragment.ReadFragment;
import com.ellfors.gankreader.ui.fragment.SettingFragment;

import org.litepal.tablemanager.Connector;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends BaseActivity
{
    @BindView(R.id.main_drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_navigation)
    NavigationView mNavigationView;

    @Inject
    ReadFragment readFragment;
    @Inject
    FuliFragment fuliFragment;
    @Inject
    LikeFragment likeFragment;
    @Inject
    SettingFragment settingFragment;
    @Inject
    AboutFragment aboutFragment;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private BaseFragment now_fragment;

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
        SQLiteDatabase db = Connector.getDatabase();
        /* Permission */
        checkPermission();
        /* initFragment */
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.main_frame, readFragment);
        ft.add(R.id.main_frame, fuliFragment);
        ft.add(R.id.main_frame, likeFragment);
        ft.add(R.id.main_frame, settingFragment);
        ft.add(R.id.main_frame, aboutFragment);
        ft.hide(fuliFragment);
        ft.hide(likeFragment);
        ft.hide(settingFragment);
        ft.hide(aboutFragment);
        ft.commit();
        now_fragment = readFragment;
        /* NavigationView */
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.menu_read:
                    setMenuItemListener(readFragment);
                    break;
                case R.id.menu_fuli:
                    setMenuItemListener(fuliFragment);
                    break;
                case R.id.menu_like:
                    setMenuItemListener(likeFragment);
                    break;
                case R.id.menu_setting:
                    setMenuItemListener(settingFragment);
                    break;
                case R.id.menu_about:
                    setMenuItemListener(aboutFragment);
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    /**
     * 侧栏Click监听
     */
    private void setMenuItemListener(BaseFragment fragment)
    {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.hide(now_fragment);
        ft.show(fragment);
        ft.commit();
        now_fragment = fragment;
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
                .addRequestCode(AppConfig.PERMISSION_CODE)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults)
    {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = AppConfig.PERMISSION_CODE)
    public void doSuccessPermission()
    {
        /* 申请权限成功 */
    }

    @PermissionFail(requestCode = AppConfig.PERMISSION_CODE)
    public void doFailPermission()
    {
        /* 申请权限失败 */
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //调用双击退出函数
            exitBy2Click();
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click()
    {
        Timer tExit = null;
        if (isExit == false)
        {
            // 准备退出
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    // 取消退出
                    isExit = false;
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        }
        else
        {
            finish();
            System.exit(0);
        }
    }

}
