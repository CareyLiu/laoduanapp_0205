package com.dexinkeji.cn.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dexinkeji.cn.fragment.HomeFragment;
import com.dexinkeji.cn.fragment.HomeFragment_New;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jaeger.library.StatusBarUtil;
import com.rairmmd.andmqtt.AndMqtt;
import com.rairmmd.andmqtt.MqttPublish;
import com.dexinkeji.cn.R;
import com.dexinkeji.cn.app.AppConfig;
import com.dexinkeji.cn.app.AppManager;
import com.dexinkeji.cn.app.BaseActivity;
import com.dexinkeji.cn.app.ConstanceValue;
import com.dexinkeji.cn.app.Notice;
import com.dexinkeji.cn.config.AudioFocusManager;
import com.dexinkeji.cn.config.PreferenceHelper;
import com.dexinkeji.cn.dialog.MyCarCaoZuoDialog_Notify;
import com.dexinkeji.cn.dialog.newdia.TishiDialog;
import com.dexinkeji.cn.fragment.MessagerFragment;
import com.dexinkeji.cn.fragment.MineFragment;
import com.dexinkeji.cn.fragment.OnlineFragment;
import com.dexinkeji.cn.model.AlarmClass;
import com.dexinkeji.cn.util.AppToast;
import com.dexinkeji.cn.util.SoundPoolUtils;
import com.dexinkeji.cn.util.YuYinChuLiTool;
import com.dexinkeji.cn.view.NoScrollViewPager;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.dexinkeji.cn.config.MyApplication.CAR_NOTIFY;
import static com.dexinkeji.cn.config.MyApplication.getAppContext;

;


public class HomeActivity extends BaseActivity {


    BottomNavigationViewEx mBnve;
    @BindView(R.id.vp)
    NoScrollViewPager mVp;
    @BindView(R.id.activity_with_view_pager)
    RelativeLayout activityWithViewPager;
    @BindView(R.id.bnve)
    BottomNavigationViewEx bnve;

    private boolean isExit;
    private SparseIntArray items;
    private AlarmClass alarmClass;
    private int i = 0;
    private TishiDialog tishiDialog;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setLightMode(this);
        ButterKnife.bind(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mBnve = findViewById(R.id.bnve);
        initView();
        initData();
        initEvent();
        AppManager.getAppManager().addActivity(this);

        _subscriptions.add(toObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Notice>() {
            @Override
            public void call(Notice notice) {
                if (notice.type == ConstanceValue.MSG_GUZHANG_SHOUYE) {
                    tuiSongTanChuang(notice);
                } else if (notice.type == ConstanceValue.MSG_GOTOXIAOXI) {
                    mVp.setCurrentItem(3, false);
                } else if (notice.type == ConstanceValue.MSG_P) {
                    handler.removeCallbacks(runnable);
                } else if (notice.type == ConstanceValue.MSG_ZHINENGJIAJU) {
                    mVp.setCurrentItem(1, false);
                }
            }
        }));


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情
                AndMqtt.getInstance().publish(new MqttPublish()
                        .setMsg("O.")
                        .setQos(2).setRetained(false)
                        .setTopic(CAR_NOTIFY), new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("Rair", "订阅O.成功");

                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.i("Rair", "(MainActivity.java:84)-onFailure:-&gt;发布失败");
                    }
                });
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);

    }

    private void tuiSongTanChuang(Notice notice) {

        String message = (String) notice.content;
        Gson gson = new Gson();
        alarmClass = gson.fromJson(message.toString(), AlarmClass.class);
        Log.i("alarmClass", alarmClass.changjia_name + alarmClass.sell_phone);

        if (alarmClass.type.equals("1")) {
            switch (alarmClass.sound) {
                case "chSound1.mp3":
                    playMusic(R.raw.ch_sound1);
                    break;
                case "chSound2.mp3":
                    playMusic(R.raw.ch_sound2);
                    break;
                case "chSound3.mp3":
                    playMusic(R.raw.ch_sound3);
                    break;
                case "chSound4.mp3":
                    playMusic(R.raw.ch_sound4);
                    break;
                case "chSound5.mp3":
                    playMusic(R.raw.ch_sound5);
                    break;
                case "chSound6.mp3":
                    playMusic(R.raw.ch_sound6);
                    break;
                case "chSound8.mp3":
                    playMusic(R.raw.ch_sound8);
                    break;
                case "chSound9.mp3":
                    playMusic(R.raw.ch_sound9);
                    break;
                case "chSound10.mp3":
                    playMusic(R.raw.ch_sound10);
                    break;
                case "chSound11.mp3":
                    playMusic(R.raw.ch_sound11);
                    break;
                case "chSound18.mp3":
                    playMusic(R.raw.ch_sound18);
                    break;
            }
        } else if (alarmClass.type.equals("5")) {
            if (alarmClass.code.equals("jyj_0006")) {
                tishiDialog = new TishiDialog(mContext, 1, new TishiDialog.TishiDialogListener() {
                    @Override
                    public void onClickCancel(View v, TishiDialog dialog) {
                    }

                    @Override
                    public void onClickConfirm(View v, TishiDialog dialog) {

                    }

                    @Override
                    public void onDismiss(TishiDialog dialog) {

                    }
                });
                tishiDialog.setTextContent("您的家庭有新的状况，是否前去查看?");
                tishiDialog.show();

            }
        }
    }

    private Handler handler;
    private Runnable runnable;

    public void playMusic(int res) {
        boolean flag = false;
        Activity currentActivity = AppManager.getAppManager().currentActivity();
        if (currentActivity != null) {
            if (!currentActivity.getClass().getSimpleName().equals(DiagnosisActivity.class.getSimpleName())) {
                MyCarCaoZuoDialog_Notify myCarCaoZuoDialog_notify = new MyCarCaoZuoDialog_Notify(getAppContext(), new MyCarCaoZuoDialog_Notify.OnDialogItemClickListener() {
                    @Override
                    public void clickLeft() {
                        if (SoundPoolUtils.soundPool != null) {
                            SoundPoolUtils.soundPool.release();
                        }
                    }

                    @Override
                    public void clickRight() {
                        DiagnosisActivity.actionStart(HomeActivity.this, alarmClass);
                        if (SoundPoolUtils.soundPool != null) {
                            SoundPoolUtils.soundPool.release();
                        }
                    }
                }
                );
                myCarCaoZuoDialog_notify.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG);
                myCarCaoZuoDialog_notify.show();
                myCarCaoZuoDialog_notify.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (SoundPoolUtils.soundPool != null) {
                            SoundPoolUtils.soundPool.release();
                        }
                    }
                });
            } else {
                flag = true;
            }
        }
        if (flag) {
            return;
        }
        SoundPoolUtils.soundPool(mContext, res);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                AppToast.makeShortToast(this, "再按一次返回键退出");
                isExit = true;
                new Thread() {
                    public void run() {
                        SystemClock.sleep(3000);
                        isExit = false;
                    }

                }.start();
                return true;
            }
            AppManager.getAppManager().finishAllActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * change BottomNavigationViewEx style
     */
    private void initView() {
        mBnve.enableAnimation(false);
        mBnve.enableShiftingMode(false);
        mBnve.enableItemShiftingMode(false);
    }

    /**
     * create fragments
     */
    private void initData() {
        List<Fragment> fragments = new ArrayList<>(5);
        items = new SparseIntArray(5);
//        HomeFragment_New homeFragment = new HomeFragment_New();
        HomeFragment homeFragment = new HomeFragment();
        OnlineFragment onlineFragment = new OnlineFragment();
//        HomeFragment_New onlineFragment = new HomeFragment_New();
        MessagerFragment messagerFragment = new MessagerFragment();
        MineFragment mineFragment = new MineFragment();
        fragments.add(homeFragment);
        fragments.add(onlineFragment);
        fragments.add(messagerFragment);
        fragments.add(mineFragment);
        items.put(R.id.i_home, 0);
        items.put(R.id.i_car_online, 1);
        items.put(R.id.i_message, 2);
        items.put(R.id.i_mine, 3);
        // set adapter
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        //禁用懒加载，不然每次切换页面都会重新获取数据
        mVp.setOffscreenPageLimit(4);
        //viewPage禁止滑动
        mVp.setScroll(false);
        mVp.setAdapter(adapter);
    }

    /**
     * set listeners
     */
    private void initEvent() {
        mBnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = items.get(item.getItemId());
                if (previousPosition != position) {
                    previousPosition = position;
                    mVp.setCurrentItem(position, false);
                }
                return true;
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBnve.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

    public static HomeActivity getInstance() {
        return new HomeActivity();
    }

        @Override
    public void initImmersion() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }
}
