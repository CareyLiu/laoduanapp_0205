package com.dexinkeji.cn.util;

import android.content.Context;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.dexinkeji.cn.app.UIHelper;
import com.dexinkeji.cn.callback.JsonCallback;
import com.dexinkeji.cn.config.AppResponse;
import com.dexinkeji.cn.tools.NetworkUtils;

import java.util.Map;


public class NetUtils<T> {


    public void requestData(Map<String, String> map, String url, Context context, JsonCallback<AppResponse<T>> jsonCallback) {

        if (NetworkUtils.isNetAvailable(context)) {
            Gson gson = new Gson();
            OkGo.<AppResponse<T>>post(url)
                    .tag(context)//
                    .upJson(gson.toJson(map))
                    .execute(jsonCallback);
        } else {
            UIHelper.ToastMessage(context, "请联网后重新尝试");
        }


    }


}
