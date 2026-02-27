package com.ascentracoresolutions.equiskill.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ascentracoresolutions.equiskill.Admin.Upload.SkillUploadFragment;
import com.ascentracoresolutions.equiskill.Admin.Upload.UploadFragment;
import com.ascentracoresolutions.equiskill.R;
import com.ascentracoresolutions.equiskill.SignIn.SignInActivity;
import com.ascentracoresolutions.equiskill.Users.Fragments.HomeFragment;
import com.ascentracoresolutions.equiskill.Users.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    private Menu menu;
    SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;
    private final AProfileFragment profileFragment = new AProfileFragment();
    private final AHomeFragment homeFragment = new AHomeFragment();
    private final AStudentFragment aStudentFragment = new AStudentFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);




        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);

        getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).addToBackStack(null).commit();

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, profileFragment).addToBackStack(null).commit();
                changeIcons(R.id.profile);

            } else if (menuItem.getItemId() == R.id.home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).addToBackStack(null).commit();
                changeIcons(R.id.home);
            }
            else if (menuItem.getItemId() == R.id.students) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, aStudentFragment).addToBackStack(null).commit();
                changeIcons(R.id.home);
            }

            return true;
        });

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_blue));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.admin_toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.upload) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new UploadFragment()).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetIcons() {

        MenuItem item = bottomNavigationView.getMenu().findItem(R.id.home);
        if (item != null) {
            item.setIcon(R.drawable.house_door);
        }

        MenuItem profile = bottomNavigationView.getMenu().findItem(R.id.profile);
        if (profile != null) {
            profile.setIcon(R.drawable.person_circle);
        }


    }

    private void changeIcons(int i) {
        resetIcons();

        MenuItem menuItem = bottomNavigationView.getMenu().findItem(i);

        if(menuItem != null) {
            if(i == R.id.home) {
                menuItem.setIcon(R.drawable.house_door_fill);
            } else if (i == R.id.profile) {
                menuItem.setIcon(R.drawable.person_fill);
            }
        }

    }



}