package com.ascentracoresolutions.equiskill.SignIn;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ascentracoresolutions.equiskill.Fetchers.InsertFetch;
import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.button_color));


        TextInputEditText otp = findViewById(R.id.email_in);
        Button btn = findViewById(R.id.send_otp);
        TextView error_info = findViewById(R.id.error_info);

        btn.setOnClickListener(v -> {
            if (Objects.requireNonNull(otp.getText()).toString().isEmpty()) {
                otp.setError("OTP is required");
            } else {

                ConstraintLayout layout = findViewById(R.id.main);
                layout.setAlpha(0.5f);
                btn.setEnabled(false);
                InsertFetch insertFetch = new InsertFetch(this, error_info, OTPActivity.class, btn, layout);
                insertFetch.fetchData(MainActivity.url + "apps/aaaab/send_otp?email=" + otp.getText().toString().trim());
            }


        });

        ConstraintLayout back = findViewById(R.id.back_layout);
        back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());




    }
}