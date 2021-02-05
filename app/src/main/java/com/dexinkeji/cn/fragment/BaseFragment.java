package com.dexinkeji.cn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jaeger.library.StatusBarUtil;
import com.dexinkeji.cn.R;


/**
 * Created by Administrator on 2016/1/13.
 *
 */
public class BaseFragment extends Fragment implements View.OnClickListener {



    @Override
    public void onClick(View v) {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(getActivity());
        initdata();
    }

    protected void initdata() {

    }
}