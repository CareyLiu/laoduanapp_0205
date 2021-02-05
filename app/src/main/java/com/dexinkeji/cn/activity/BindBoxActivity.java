package com.dexinkeji.cn.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.dexinkeji.cn.R;
import com.dexinkeji.cn.activity.chelianwang.ScanAddCarActivity;
import com.dexinkeji.cn.app.BaseActivity;
import com.dexinkeji.cn.util.AppToast;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Sl on 2019/6/18.
 */

public class BindBoxActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private RelativeLayout mRlBack;
    private RelativeLayout mRlScanAdd;
    private RelativeLayout mRlHandAdd;

    @Override
    public void onCreate(Bundle savedInstanceStateundle) {
        super.onCreate(savedInstanceStateundle);
        initView();
    }


    @Override
    public int getContentViewResId() {
        return R.layout.activity_bind_box;
    }

    private void initView() {
        mRlBack = (RelativeLayout) findViewById(R.id.rl_back);
        mRlBack.setOnClickListener(this);
        mRlScanAdd = (RelativeLayout) findViewById(R.id.rl_scan_add);
        mRlScanAdd.setOnClickListener(this);
        mRlHandAdd = (RelativeLayout) findViewById(R.id.rl_hand_add);
        mRlHandAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_scan_add:

                if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                    ScanAddCarActivity.actionStart(BindBoxActivity.this);
                } else {
                    EasyPermissions.requestPermissions(this, getString(R.string.xjqx), 0, Manifest.permission.CAMERA);
                }
               // finish();
                break;
            case R.id.rl_hand_add:
                finish();
                startActivity(new Intent(this, HandAddActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //startActivity(new Intent(this, ScanActivity.class));
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        AppToast.makeShortToast(this, getString(R.string.get_error));
    }

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, BindBoxActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("绑定设备");
        tv_title.setTextSize(17);
        tv_title.setTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationIcon(R.mipmap.backbutton);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imm.hideSoftInputFromWindow(findViewById(R.id.cl_layout).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
            }
        });
    }

    @Override
    public boolean showToolBar() {
        return true;
    }
}
