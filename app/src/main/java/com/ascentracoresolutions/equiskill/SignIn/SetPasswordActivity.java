package com.ascentracoresolutions.equiskill.SignIn;

import android.content.SharedPreferences;
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

public class SetPasswordActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_blue));

        TextInputEditText conf_password = findViewById(R.id.conf_password);
        TextInputEditText password = findViewById(R.id.password);
        Button btn = findViewById(R.id.set_password);

        TextView error_info = findViewById(R.id.error_info);

        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE);

        btn.setOnClickListener(v -> {
            String pass = Objects.requireNonNull(password.getText()).toString().trim();
            String conf_pass_s = Objects.requireNonNull(conf_password.getText()).toString().trim();


            if (pass.length() >= 8) {
                if (pass.equals(conf_pass_s)) {

                    ConstraintLayout layout = findViewById(R.id.main);
                    layout.setAlpha(0.5f);
                    btn.setEnabled(false);
                    InsertFetch insertFetch = new InsertFetch(this, error_info, SignInActivity.class, btn, layout);
                    insertFetch.fetchData(MainActivity.url + "apps/aaaab/create_account_pass?user_id=" + sharedPreferences.getString(InsertFetch.USER_ID_SIGN, null) + "&password=" + pass);

                } else {
                    conf_password.setError("Password does not match");
                }

            } else {

                password.setError("Password must be at least 8 characters");
            }



        });




    }
}