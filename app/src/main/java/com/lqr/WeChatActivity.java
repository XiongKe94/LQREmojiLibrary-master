package com.lqr;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lqr.emoji.EmoticonPickerView;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.IEmoticonSelectedListener;

/**
 * Created by Xiong Ke on 2017/2/21.
 */

public class WeChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);
        initView();
        initEmotionKeyboard();
    }

    private TextView ll_center;
    private EditText et_input;
    private Button bt_emjon;
    private Button bt_menu;
    EmotionKeyboard mEmotionKeyboard;
    EmoticonPickerView epv;
    TextView tv_menu;
    private FrameLayout  frm;

    private void initView() {
        ll_center = (TextView) findViewById(R.id.ll_center);
        et_input = (EditText) findViewById(R.id.et_input);
        bt_emjon = (Button) findViewById(R.id.bt_emjon);
        bt_menu = (Button) findViewById(R.id.bt_menu);
        frm = (FrameLayout) findViewById(R.id.frm);
        epv= (EmoticonPickerView) findViewById(R.id.epv);
        tv_menu= (TextView) findViewById(R.id.tv_menu);

        epv.attachEditText(et_input);//把EditText交给EmoticonPickerView控制
        epv.setWithSticker(true);//开启贴图功能



        epv.setVisibility(View.VISIBLE);//开关表情贴图控件
        epv.show(new IEmoticonSelectedListener() {
            @Override
            public void onEmojiSelected(String key) {
                Log.i("LQR", "onEmojiSelected," + key);
            }

            @Override
            public void onStickerSelected(String categoryName, String stickerName) {
                Log.i("LQR", "onStickerSelected, categoryName =" + categoryName
                        + ", stickerName =" + stickerName);
            }
        });
    }

    private void initEmotionKeyboard() {
        //1、创建EmotionKeyboard对象
        mEmotionKeyboard = EmotionKeyboard.with(this);

        //2、绑定输入框控件
        mEmotionKeyboard.bindToEditText(et_input);
        //3、绑定输入框上面的消息列表控件（这里用的是RecyclerView，其他控件也可以，注意该控件是会影响输入框位置的控件）
        mEmotionKeyboard.bindToContent(ll_center);
        //4、绑定输入框下面的底部区域（这里是把表情区和功能区共放在FrameLayout下，所以绑定的控件是FrameLayout）
        mEmotionKeyboard.setEmotionView(frm);
        //5、绑定表情按钮（可以绑定多个，如微信就有2个，一个是表情按钮，一个是功能按钮）
        mEmotionKeyboard.bindToEmotionButton(bt_emjon, bt_menu);
        //6、当在第5步中绑定了多个EmotionButton时，这里的回调监听的view就有用了，注意是为了判断是否要自己来控制底部的显隐，还是交给EmotionKeyboard控制
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
//                if (mBtnVoice.getVisibility() == View.VISIBLE) {
//                    hideBtnVoice();
//                }
                //输入框底部显示时
                if (frm.getVisibility() == View.VISIBLE) {
                    //表情控件显示而点击的按钮是ivAdd时，拦截事件，隐藏表情控件，显示功能区
                    if (epv.getVisibility() == View.VISIBLE && view.getId() == R.id.bt_menu) {
                        epv.setVisibility(View.GONE);
                        tv_menu.setVisibility(View.VISIBLE);
                        return true;
                        //功能区显示而点击的按钮是ivEmo时，拦截事件，隐藏功能区，显示表情控件
                    } else if (tv_menu.getVisibility() == View.VISIBLE && view.getId() == R.id.bt_emjon) {
                        epv.setVisibility(View.VISIBLE);
                        tv_menu.setVisibility(View.GONE);
                        return true;
                    }
                } else {
                    //点击ivEmo，显示表情控件
                    if (view.getId() == R.id.bt_emjon) {
                        epv.setVisibility(View.VISIBLE);
                        tv_menu.setVisibility(View.GONE);
                        //点击ivAdd，显示功能区
                    } else {
                        epv.setVisibility(View.GONE);
                        tv_menu.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
    }
}
