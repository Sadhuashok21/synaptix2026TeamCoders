package com.ascentracoresolutions.equiskill.SignIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ascentracoresolutions.equiskill.MainActivity;
import com.ascentracoresolutions.equiskill.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class OTPActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.button_color));


        TextInputEditText otp = findViewById(R.id.otp_input);
        Button btn = findViewById(R.id.verify_btn);
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);


        TextView change_mail = findViewById(R.id.change_mail);

        change_mail.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        TextView back = findViewById(R.id.back_layout);

        back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());



        btn.setOnClickListener(v -> {

            String otp_s = Objects.requireNonNull(otp.getText()).toString().trim();

            if (otp_s.isEmpty()) {
                otp.setError("OTP is required");
            } else if (otp_s.equals(sharedPreferences.getString("otp", null))) {

                startActivity(new Intent(this, SetPasswordActivity.class));
            } else {
                otp.setError("OTP is incorrect");
            }

        });




    }
}