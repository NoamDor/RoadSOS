package com.example.roadsos.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.roadsos.R;
import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;
import com.example.roadsos.model.ProblemType;
import com.example.roadsos.model.StoreModel;
import com.example.roadsos.ui.problemCreation.ProblemCreationFragmentDirections;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ProblemDetailsFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    View view;
    Bitmap imageBitmap;
    ProblemType problemType;

    public ProblemDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_problem_details, container, false);
        TextInputLayout userNameLayout = view.findViewById(R.id.problem_details_user_name_layout);
        TextInputEditText userNameEditText = view.findViewById(R.id.problem_details_user_name_et);
        addListenerToRequiredEditText(userNameLayout, userNameEditText);

        TextInputLayout phoneNumberLayout = view.findViewById(R.id.problem_details_phone_number_layout);
        TextInputEditText phoneNumberEditText = view.findViewById(R.id.problem_details_phone_number_et);
        addListenerToRequiredEditText(phoneNumberLayout, phoneNumberEditText);

        ImageButton takePhotobtn = view.findViewById(R.id.problem_details_take_photo_btn);
        takePhotobtn.setOnClickListener(b -> {
            takePhoto();
        });
        
        Button createProblem = view.findViewById(R.id.problem_details_create_problem_btn);
        createProblem.setOnClickListener(b -> {
            saveProblem();
        });

        problemType = ProblemDetailsFragmentArgs.fromBundle(getArguments()).getProblemType();
        return view;
    }

    private void saveProblem() {
        TextInputEditText carTypeEditText = view.findViewById(R.id.problem_details_car_type_et);
        String carType = carTypeEditText.getText().toString();

        TextInputEditText licensePlateEditText = view.findViewById(R.id.problem_details_license_plate_et);
        String licensePlate = licensePlateEditText.getText().toString();

        TextInputEditText userNameEditText = view.findViewById(R.id.problem_details_user_name_et);
        String userName = userNameEditText.getText().toString();

        TextInputEditText phoneNumberEditText = view.findViewById(R.id.problem_details_phone_number_et);
        String phoneNumber = phoneNumberEditText.getText().toString();

        Date date = new Date();

        StoreModel.uploadImage(imageBitmap, "my_photo" + date.getTime(), new StoreModel.Listener() {
            @Override
            public void onSuccess(String url) {
                Log.d("TAG", "url: " + url);
                Problem problem = new Problem(problemType.getId(), carType, licensePlate, userName, phoneNumber, url);
                ProblemModel.instance.addProblem(problem, new ProblemModel.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
//                        NavController navCtrl = Navigation.findNavController(view);
//                        navCtrl.navigateUp();
                        Log.d("TAG", "problem has been added");
                    }
                });
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageView checkboxImage = view.findViewById(R.id.problem_details_checkbox_image);
            checkboxImage.setVisibility(View.VISIBLE);
        }
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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