package com.example.roadsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.roadsos.ui.authentication.LoginFragment;
import com.example.roadsos.ui.authentication.RegisterFragment;

import java.util.ArrayList;

public class AuthenticationActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        NavController navController = Navigation.findNavController(this, R.id.authentication_nav_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);

        sharedpreferences=getApplicationContext().getSharedPreferences("Preferences", 0);
        String login = sharedpreferences.getString("LOGIN", null);

        if (login != null) {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}