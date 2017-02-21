package com.lqr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lqr.emoji.EmoticonPickerView;
import com.lqr.emoji.IEmoticonSelectedListener;

public class MainActivity extends AppCompatActivity  {

    private EmoticonPickerView mEpv;
    private EditText messageEditText;
  //  private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
      //  button= (Button) findViewById(R.id.button);
        mEpv = (EmoticonPickerView) findViewById(R.id.epv);



        mEpv.setWithSticker(true);//开启贴图功能

        mEpv.attachEditText(messageEditText);//把EditText交给EmoticonPickerView控制

        mEpv.setVisibility(View.VISIBLE);//开关表情贴图控件
        mEpv.show(new IEmoticonSelectedListener() {
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
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //EmotionKeyboard.with(this);
    }
}
