package org.thoughtcrime.securesms.toast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.thoughtcrime.securesms.R;

public class ToastActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        textView = findViewById(R.id.textView);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        textView.setText(text);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.y = 150;

        getWindow().setAttributes(layoutParams);
    }
}
