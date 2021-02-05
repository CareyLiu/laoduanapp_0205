package com.dexinkeji.cn.aakefudan.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dexinkeji.cn.R;
import com.dexinkeji.cn.aakefudan.model.ZixunModel;
import com.dexinkeji.cn.baseadapter.baserecyclerviewadapterhelper.BaseQuickAdapter;
import com.dexinkeji.cn.baseadapter.baserecyclerviewadapterhelper.BaseViewHolder;

import java.util.List;

public class XiuliAdapter extends BaseQuickAdapter<ZixunModel.DataBean.ListBean, BaseViewHolder> {

    public XiuliAdapter(int layoutResId, @Nullable List<ZixunModel.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZixunModel.DataBean.ListBean item) {


        helper.setText(R.id.tv_name, item.getInst_name());
        helper.setText(R.id.tv_juli, item.getMeter() + "KM");


        Glide.with(mContext).load(item.getUrl())
                .error(R.mipmap.logi_icon)
                .into((ImageView) helper.getView(R.id.iv_icon));

        boolean select = item.isSelect();
        ImageView iv_select = helper.getView(R.id.iv_select);
        if (select) {
            iv_select.setImageResource(R.mipmap.yixuanze);
        } else {
            iv_select.setImageResource(R.mipmap.weixuanze);
        }
    }
}
