package com.dexinkeji.cn.aakefudan.base;

import android.view.View;

import com.dexinkeji.cn.R;
import com.dexinkeji.cn.app.BaseActivity;

public class ServiceBaseActivity extends BaseActivity {


    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setTextSize(17);
        tv_title.setTextColor(getResources().getColor(R.color.black));
        tv_rightTitle.setTextSize(17);
        tv_rightTitle.setTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationIcon(R.mipmap.backbutton);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
