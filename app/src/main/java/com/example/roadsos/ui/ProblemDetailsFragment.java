package com.example.roadsos.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.roadsos.R;
import com.example.roadsos.model.ProblemType;
import com.example.roadsos.ui.problemCreation.ProblemCreationFragmentDirections;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProblemDetailsFragment extends Fragment {

    public ProblemDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_problem_details, container, false);
        TextInputLayout userNameLayout = view.findViewById(R.id.problem_details_user_name_layout);
        TextInputEditText userNameEditText = view.findViewById(R.id.problem_details_user_name_et);
        addListenerToRequiredEditText(userNameLayout, userNameEditText);

        TextInputLayout phoneNumberLayout = view.findViewById(R.id.problem_details_phone_number_layout);
        TextInputEditText phoneNumberEditText = view.findViewById(R.id.problem_details_phone_number_et);
        addListenerToRequiredEditText(phoneNumberLayout, phoneNumberEditText);

        ProblemType problemType = ProblemDetailsFragmentArgs.fromBundle(getArguments()).getProblemType();
        return view;
    }

    private void addListenerToRequiredEditText(TextInputLayout layout, TextInputEditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEditText(layout, s);
            }
        });

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(layout, ((EditText)v).getText());
                }
            }
        });
    }

    private void validateEditText(TextInputLayout layout, Editable s) {
        if (TextUtils.isEmpty(s)) {
            Log.d("TAG", "field is empty");
            layout.setError("שדה חובה");
        } else {
            layout.setError(null);
        }
    }
}