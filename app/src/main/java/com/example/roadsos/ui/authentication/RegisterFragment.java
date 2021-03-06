package com.example.roadsos.ui.authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.roadsos.MainActivity;
import com.example.roadsos.R;
import com.example.roadsos.utils.Listeners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    View view;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        TextInputLayout emailLayout = view.findViewById(R.id.register_email_layout);
        TextInputEditText emailEt = view.findViewById(R.id.edit_my_problem_name_et);
        Listeners.addListenerToRequiredEditText(emailLayout, emailEt);

        TextInputLayout passwordLayout = view.findViewById(R.id.register_password_layout);
        TextInputEditText passwordEt = view.findViewById(R.id.register_password_et);
        Listeners.addListenerToRequiredEditText(passwordLayout, passwordEt);

        TextInputLayout repasswordLayout = view.findViewById(R.id.register_repassword_layout);
        TextInputEditText repasswordEt = view.findViewById(R.id.register_repassword_et);
        Listeners.addListenerToRequiredEditText(repasswordLayout, repasswordEt);
        Button registerBtn = view.findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(b -> {
            createAccount(emailEt.getText().toString(), passwordEt.getText().toString(), passwordEt.getText().toString());
        });

        return view;
    }

    public void createAccount(String email, String password, String repassword) {
        if (email.length() == 0 || password.length() == 0 || repassword.length() == 0) {
            Toast.makeText(getActivity(), "אנא מלא את כל הפרטים",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(getActivity(), "הסיסמה והאימות שלה לא זהים",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();
                            if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                                // User can sign in with email/password
                                Toast.makeText(getActivity(), "משתמש זה כבר קיים במערכת",
                                        Toast.LENGTH_SHORT).show();
                            }  else {
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Log.d("TAG", "createUserWithEmail:success");
                                                    Toast.makeText(getActivity(), "נרשמת בהצלחה",
                                                            Toast.LENGTH_SHORT).show();
                                                    NavController navCtrl = Navigation.findNavController(view);
                                                    navCtrl.popBackStack();
                                                }
                                            }
                                        });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "מייל לא תקין",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}