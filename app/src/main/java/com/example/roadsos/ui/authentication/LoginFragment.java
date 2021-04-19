package com.example.roadsos.ui.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roadsos.MainActivity;
import com.example.roadsos.R;
import com.example.roadsos.utils.Listeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private SharedPreferences sharedpreferences;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sharedpreferences=getActivity().getSharedPreferences("Preferences", 0);

        TextInputEditText emailEt = view.findViewById(R.id.login_email_et);
        TextInputEditText passwordEt = view.findViewById(R.id.login_password_et);
        Button loginBtn = view.findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(b -> {
            login(emailEt.getText().toString(), passwordEt.getText().toString());
        });

        Button registerBtn = view.findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(b -> {
            NavDirections direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(getActivity(), R.id.authentication_nav_fragment).navigate(direction);
        });

        return view;
    }

    public void login(String email, String password) {
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getActivity(), "שם המשתמש/סיסמה לא נכונים",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("LOGIN", user.getUid());
                            editor.commit();

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "שם המשתמש/סיסמה לא נכונים",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}