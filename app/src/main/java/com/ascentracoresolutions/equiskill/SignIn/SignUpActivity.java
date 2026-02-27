package com.ascentracoresolutions.equiskill.SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.button_color));

        TextView back_to_sign = findViewById(R.id.back_to_sign);


        if (savedInstanceState != null) {
            finish();
        }
        back_to_sign.setOnClickListener(v -> {

            startActivity(new Intent(this, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

        });

        TextInputEditText firstName = findViewById(R.id.first_input);
        TextInputEditText lastName = findViewById(R.id.last_input);
        TextInputEditText email = findViewById(R.id.email_in);
        TextInputEditText phone = findViewById(R.id.phone_in);
        TextView error_info = findViewById(R.id.error_info);

        Button btn = findViewById(R.id.create_btn);

        TextView back = findViewById(R.id.back_layout);

        back.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());


        btn.setOnClickListener(v -> {

            String first_name = Objects.requireNonNull(firstName.getText()).toString().trim();
            String last_name = Objects.requireNonNull(lastName.getText()).toString().trim();
            String email_address = Objects.requireNonNull(email.getText()).toString().trim();
            String phone_in = Objects.requireNonNull(phone.getText()).toString().trim();

            if (first_name.isEmpty()) {
                firstName.setError("First name is required");
            } else if (last_name.isEmpty()) {
                lastName.setError("Last name is required");
            }else if (email_address.isEmpty() || !isValidEmail(email_address)) {

                email.setError("Email is required & must be in email format");
            } else if (phone_in.isEmpty()) {
                phone.setError("Phone number is required");
            } else {

                ConstraintLayout layout = findViewById(R.id.main);
                layout.setAlpha(0.5f);
                btn.setEnabled(false);

                InsertFetch insertFetch = new InsertFetch(this, error_info, OTPActivity.class, btn, layout);
                insertFetch.fetchData(MainActivity.url + "apps/aaaab/create_account?fullname=" + first_name + "&lastname=" + last_name + "&email=" + email_address + "&phone=" + phone_in);

            }

        });



    }

    public boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}