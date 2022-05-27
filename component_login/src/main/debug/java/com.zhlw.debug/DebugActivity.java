package com.zhlw.debug;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.zhlw.component.login.R;


public class DebugActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        TextView version = (TextView) findViewById(R.id.idVersionTv);

    }
}