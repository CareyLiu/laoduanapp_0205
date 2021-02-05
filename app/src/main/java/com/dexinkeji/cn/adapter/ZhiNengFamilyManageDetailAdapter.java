package com.dexinkeji.cn.adapter;

import androidx.annotation.Nullable;

import com.dexinkeji.cn.R;
import com.dexinkeji.cn.baseadapter.baserecyclerviewadapterhelper.BaseQuickAdapter;
import com.dexinkeji.cn.baseadapter.baserecyclerviewadapterhelper.BaseViewHolder;
import com.dexinkeji.cn.model.ZhiNengFamilyMAnageDetailBean;

import java.util.List;

public class ZhiNengFamilyManageDetailAdapter extends BaseQuickAdapter<ZhiNengFamilyMAnageDetailBean.DataBean.MemberBean, BaseViewHolder> {
    public ZhiNengFamilyManageDetailAdapter(int layoutResId, @Nullable List<ZhiNengFamilyMAnageDetailBean.DataBean.MemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiNengFamilyMAnageDetailBean.DataBean.MemberBean item) {
        helper.setText(R.id.tv_name, item.getUser_name());
        helper.setText(R.id.tv_type, item.getMember_type_name());
        helper.addOnClickListener(R.id.ll_content);
    }
}
