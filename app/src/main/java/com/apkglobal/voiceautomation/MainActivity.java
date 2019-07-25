package com.apkglobal.voiceautomation;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
Button btn_share, btn_voice, btn_speak;
TextView tv_data;
EditText et_msg;
TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_msg=findViewById(R.id.et_msg);
        tv_data=findViewById(R.id.tv_data);
        btn_share=findViewById(R.id.btn_share);
        btn_speak=findViewById(R.id.btn_speak);
        btn_voice=findViewById(R.id.btn_voice);
        textToSpeech=new TextToSpeech(this, this);

        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
                startActivityForResult(intent, 1);
            }
        });

        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= et_msg.getText().toString();
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share=new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, tv_data.getText().toString());
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, "ShareApp via"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 &&  resultCode== RESULT_OK && data != null)
        {
            ArrayList<String> arrayList=data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            tv_data.setText(arrayList.get(0).toString());
        }
    }

    @Override
    public void onInit(int status) {
        String message= et_msg.getText().toString();
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);

    }
}
