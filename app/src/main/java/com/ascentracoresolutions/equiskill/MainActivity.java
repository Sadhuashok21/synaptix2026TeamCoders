package com.ascentracoresolutions.equiskill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ascentracoresolutions.equiskill.SignIn.SignInActivity;
import com.ascentracoresolutions.equiskill.Users.Fragments.HomeFragment;
import com.ascentracoresolutions.equiskill.Users.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final HomeFragment homeFragment = new HomeFragment();

    private Menu menu;
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF = "shared_pref";
    public static final String USER_ID = "user_id";
    public static final String USER_TYPE = "user_type";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        String user_id = sharedPreferences.getString(USER_ID, null);



        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);


        bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, profileFragment).addToBackStack(null).commit();
                changeIcons(R.id.profile);

            } else if (menuItem.getItemId() == R.id.home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, homeFragment).addToBackStack(null).commit();
                changeIcons(R.id.home);
            }

            return true;
        });

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.main));
        /* window.setNavigationBarColor(ContextCompat.getColor(this, R.color.button_color));*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.toolbar, menu);

        String user_type = sharedPreferences.getString(USER_TYPE, null);

//        if (Objects.equals(user_type, "admin")) {
//            menu.findItem(R.id.admin).setVisible(true);
//        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.profile) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, cartFragment).addToBackStack(null).commit();
//        } else if (item.getItemId() == R.id.signIn) {
//            Intent i = new Intent(this, SignInActivity.class);
//            startActivity(i);
//        }
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
//
//
//        MenuItem grid = bottomNavigationView.getMenu().findItem(R.id.grid);
//        if (grid != null) {
//            grid.setIcon(R.drawable.grid);
//        }
//
//        MenuItem offer = bottomNavigationView.getMenu().findItem(R.id.orders);
//        if (offer != null) {
//            offer.setIcon(R.drawable.truck);
//        }
//
//        MenuItem cart = bottomNavigationView.getMenu().findItem(R.id.cart);
//        if (cart != null) {
//            cart.setIcon(R.drawable.cart4);
//        }


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