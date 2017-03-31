package com.thepinesoft.smsforward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.thepinesoft.smsforward.fw.DespatchServiceImpl;
import com.thepinesoft.smsforward.global.Autowired;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View onOffListen){
        ToggleButton onOffButton = (ToggleButton) onOffListen;
        Autowired.setContext(getApplicationContext());

        if(onOffButton.isChecked()){
            getApplicationContext().startService(new Intent(getApplicationContext(),DespatchServiceImpl.class));

        }else{
            getApplicationContext().stopService(new Intent(getApplicationContext(),DespatchServiceImpl.class));
        }
    }
}
