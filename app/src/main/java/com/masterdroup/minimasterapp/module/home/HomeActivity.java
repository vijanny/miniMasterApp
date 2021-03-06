package com.masterdroup.minimasterapp.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.masterdroup.minimasterapp.App;
import com.masterdroup.minimasterapp.R;
import com.masterdroup.minimasterapp.module.device.DeviceListActivity;
import com.masterdroup.minimasterapp.module.welcomeModule.WelcomeActivity;
import com.masterdroup.minimasterapp.util.DebugUtils;
import com.masterdroup.minimasterapp.util.Utils;
import com.masterdroup.minimasterapp.view.TipsDialog;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.bottomBar)
    BottomBar bottomBar;
    @Bind(R.id.fl_content)
    FrameLayout flContent;

    MenuFragment menuFragment;
    DeviceFragment deviceFragment;
    UserFragment userFragment;

    String menuFragment_tag = "tag1", deviceFragment_tag = "tag2", userFragment_tag = "tag3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        showMenuFragment();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {

        menuFragment = new MenuFragment();

        getFragmentManager().beginTransaction().add(R.id.fl_content, menuFragment, menuFragment_tag).commit();


        if (Utils.isLogin()) {
            deviceFragment = new DeviceFragment();
            userFragment = new UserFragment();
            getFragmentManager().beginTransaction().add(R.id.fl_content, deviceFragment, deviceFragment_tag).commit();
            getFragmentManager().beginTransaction().add(R.id.fl_content, userFragment, userFragment_tag).commit();

        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                hideAllFragment();
                if (tabId == R.id.tab_food) {
                    DebugUtils.d("TAB", "tab_food");
                    showMenuFragment();
                } else if (tabId == R.id.tab_device) {
                    DebugUtils.d("TAB", "tab_device");
                    if (!Utils.isLogin()) {
                        finish();
                        startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));
                    } else
                        showDeviceFragment();
                } else if (tabId == R.id.tab_user) {

                    DebugUtils.d("TAB", "tab_user");
                    if (!Utils.isLogin()) {
                        finish();
                        startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));
                    } else
                        showUserFragment();
                }
            }
        });


    }

    void showMenuFragment() {

        if (menuFragment == null)
            menuFragment = (MenuFragment) getFragmentManager().findFragmentByTag(menuFragment_tag);
        getFragmentManager().beginTransaction().show(menuFragment).commit();
    }

    void showDeviceFragment() {

        if (deviceFragment == null)
            deviceFragment = (DeviceFragment) getFragmentManager().findFragmentByTag(deviceFragment_tag);
        getFragmentManager().beginTransaction().show(deviceFragment).commit();
    }

    void showUserFragment() {

        if (App.spUtils.contains(App.mContext.getString(R.string.key_token))) {

            if (userFragment == null)
                userFragment = (UserFragment) getFragmentManager().findFragmentByTag(userFragment_tag);
            getFragmentManager().beginTransaction().show(userFragment).commit();
        } else {
            finish();
        }
    }

    void hideAllFragment() {
        getFragmentManager().beginTransaction().hide(menuFragment).commit();

        if (null != deviceFragment)
            getFragmentManager().beginTransaction().hide(deviceFragment).commit();
        if (null != userFragment)
            getFragmentManager().beginTransaction().hide(userFragment).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 666) {
            finish();
        } else if (resultCode == 98765) {
            TipsDialog dialog = new TipsDialog(HomeActivity.this,
                    getResources().getString(R.string.devicedisconnected));

            dialog.show();
        }
    }

}
