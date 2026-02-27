package com.ascentracoresolutions.equiskill.SignIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

public class SignInActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);

        String user_id = sharedPreferences.getString(MainActivity.USER_ID, null);

        if (user_id != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        ImageView eye, eye_slash;

        eye = findViewById(R.id.eye);
        eye_slash = findViewById(R.id.eye_slash);


        TextInputEditText email = findViewById(R.id.email_in);
        TextInputEditText password = findViewById(R.id.password_in);

        eye.setOnClickListener(v -> {
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eye.setVisibility(View.GONE);
            eye_slash.setVisibility(View.VISIBLE);
        });

        eye_slash.setOnClickListener(v -> {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eye.setVisibility(View.VISIBLE);
            eye_slash.setVisibility(View.GONE);
        });


        Button sign_in_btn = findViewById(R.id.sign_in_btn);
        TextView error_info = findViewById(R.id.error_info);

        sign_in_btn.setOnClickListener(v -> {

            String email_val = Objects.requireNonNull(email.getText()).toString().trim();
            String password_val = Objects.requireNonNull(password.getText()).toString().trim();


            if (email_val.isEmpty()) {
                email.setError("Email is required");

            } else if (password_val.isEmpty()) {
                password.setError("Password is required");
            } else {

                ConstraintLayout layout = findViewById(R.id.main);
                layout.setAlpha(0.5f);
                sign_in_btn.setEnabled(false);
                InsertFetch insertFetch = new InsertFetch(this, error_info, MainActivity.class, sign_in_btn, layout);
                insertFetch.fetchData(MainActivity.url + "apps/aaaab/signin?email=" + email_val + "&password=" + password_val);

            }

        });


        TextView forgot = findViewById(R.id.forgot_password);
        TextView create_account = findViewById(R.id.create_account);

        create_account.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });


        forgot.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
        });


        ConstraintLayout back = findViewById(R.id.back_layout);

        back.setOnClickListener(v -> {

            getOnBackPressedDispatcher();
        });



    }
}