package com.dexinkeji.cn.activity.chelianwang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.dexinkeji.cn.R;
import com.dexinkeji.cn.activity.shuinuan.Y;
import com.dexinkeji.cn.app.BaseActivity;
import com.dexinkeji.cn.app.UIHelper;
import com.dexinkeji.cn.callback.JsonCallback;
import com.dexinkeji.cn.config.AppResponse;
import com.dexinkeji.cn.config.UserManager;
import com.dexinkeji.cn.dialog.BangdingFailDialog;
import com.dexinkeji.cn.get_net.Urls;
import com.dexinkeji.cn.model.CarBrand;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanAddCarActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final String tag = ScanAddCarActivity.class.getSimpleName();
    @BindView(R.id.zxingview)
    ZBarView mQRCodeView;
    @BindView(R.id.capture_flash)
    ImageView captureFlash;


    private String companyid;
    Long personId;
    private String roleId;
    boolean flag = true;
    boolean input_flag = false;

    private String myCode = null;
    private String Sn = null;

    private Camera camera;
    private Camera.Parameters parameter;
    ProgressDialog waitdialog;

    /**
     * 用于其他Activty跳转到该Activity
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ScanAddCarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        mQRCodeView.startSpot();
        mQRCodeView.setDelegate(this);
        // mQRCodeView.setResultHandler(this);

    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_scan1;
    }


    private void light() {
        if (flag) {
            flag = false;
            // 开闪光灯
            mQRCodeView.openFlashlight();
            captureFlash.setTag(null);
            captureFlash.setBackgroundResource(R.drawable.flash_open);
        } else {
            flag = true;
            // 关闪光灯
            mQRCodeView.closeFlashlight();
            captureFlash.setTag("1");
            captureFlash.setBackgroundResource(R.drawable.flash_default);
        }
    }


    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.e(tag, result);
        myCode = result;
        waitdialog = ProgressDialog.show(ScanAddCarActivity.this, null, "已扫描，正在处理···", true, true);
        waitdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                mQRCodeView.stopSpot();
            }
        });

        //  UIHelper.ToastMessage(ScanActivity.this, "您已经收到了二维码 code:" + result);
        vibrate();
        // mQRCodeView.startSpot();
        waitdialog.dismiss();
        if (result.length() == 24) {
            addSheBei(result);
        } else {
            BangdingFailDialog dialog = new BangdingFailDialog(mContext);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });
            dialog.setTextContent("您的设备码不正确");
            dialog.show();
        }
    }

    public void addSheBei(String ccid) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "03509");
        map.put("key", Urls.key);
        map.put("token", UserManager.getManager(mContext).getAppToken());
        map.put("ccid", ccid);

        Gson gson = new Gson();
        OkGo.<AppResponse<CarBrand.DataBean>>post(Urls.SERVER_URL + "wit/app/user")
                .tag(this)//
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<AppResponse<CarBrand.DataBean>>() {
                    @Override
                    public void onSuccess(final Response<AppResponse<CarBrand.DataBean>> response) {
                        UIHelper.ToastMessage(mContext, "添加成功");
                        finish();
//                        Notice notice = new Notice();
//                        notice.type = ConstanceValue.MSG_ADD_CHELIANG_SUCCESS;
//                        sendRx(notice);
                    }

                    @Override
                    public void onError(Response<AppResponse<CarBrand.DataBean>> response) {
                        String msg = response.getException().getMessage();
                        Y.tError(response);

                        BangdingFailDialog dialog = new BangdingFailDialog(mContext);
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                        dialog.setTextContent(msg);
                        dialog.show();
                    }
                });
    }


    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(tag, "打开相机出错");
        mQRCodeView.startCamera();
    }

    /**
     * 扫描结果对话框
     *
     * @param msg
     */
    public void showDialog(final String msg) {
        new AlertDialog.Builder(ScanAddCarActivity.this).setTitle("扫描结果").setMessage(msg)
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        waitdialog.dismiss();
                        dialog.dismiss();
                        mQRCodeView.startSpotAndShowRect();
                    }
                }).show();
    }


    @OnClick({R.id.capture_flash})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.capture_flash:
                light();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (waitdialog != null) {
            waitdialog.dismiss();
        }
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        tv_title.setText("扫一扫添加设备");
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
