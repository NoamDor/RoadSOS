package com.example.roadsos.ui.newProblem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
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
import android.widget.Toast;

import com.example.roadsos.R;
import com.example.roadsos.model.MyLocation;
import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;
import com.example.roadsos.model.ProblemType;
import com.example.roadsos.model.StoreModel;
import com.example.roadsos.ui.newProblem.NewProblemDetailsFragmentArgs;
import com.example.roadsos.ui.newProblem.NewProblemDetailsFragmentDirections;
import com.example.roadsos.utils.Listeners;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class NewProblemDetailsFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    View view;
    Bitmap imageBitmap;
    ProblemType problemType;
    MyLocation problemLocation;
    TextInputEditText phoneNumberEditText;
    TextInputEditText carTypeEditText;
    TextInputEditText licensePlateEditText;
    TextInputEditText userNameEditText;

    public NewProblemDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_problem_details, container, false);

        TextInputLayout phoneNumberLayout = view.findViewById(R.id.new_problem_details_phone_number_layout);
        this.phoneNumberEditText = view.findViewById(R.id.new_problem_details_phone_number_et);
        Listeners.addListenerToRequiredEditText(phoneNumberLayout, this.phoneNumberEditText);

        TextInputLayout carTypeLayout = view.findViewById(R.id.new_problem_details_car_type_layout);
        this.carTypeEditText = view.findViewById(R.id.new_problem_details_car_type_et);
        Listeners.addListenerToRequiredEditText(carTypeLayout, carTypeEditText);

        TextInputLayout licensePlateLayout = view.findViewById(R.id.new_problem_details_license_plate_layout);
        this.licensePlateEditText = view.findViewById(R.id.new_problem_details_license_plate_et);
        Listeners.addListenerToRequiredEditText(licensePlateLayout, licensePlateEditText);

        TextInputLayout userNameLayout = view.findViewById(R.id.new_problem_details_user_name_layout);
        this.userNameEditText = view.findViewById(R.id.new_problem_details_user_name_et);
        Listeners.addListenerToRequiredEditText(userNameLayout, userNameEditText);

        ImageButton takePhotobtn = view.findViewById(R.id.new_problem_details_take_photo_btn);
        takePhotobtn.setOnClickListener(b -> {
            takePhoto();
        });
        
        Button createProblem = view.findViewById(R.id.new_problem_details_create_problem_btn);
        createProblem.setOnClickListener(b -> {
            saveProblem();
        });

        problemType = NewProblemDetailsFragmentArgs.fromBundle(getArguments()).getProblemType();
        problemLocation = NewProblemDetailsFragmentArgs.fromBundle(getArguments()).getProblemLocation();
        return view;
    }

    private void saveProblem() {
        String carType = this.carTypeEditText.getText().toString();
        String licensePlate = this.licensePlateEditText.getText().toString();
        String userName = this.userNameEditText.getText().toString();
        String phoneNumber = this.phoneNumberEditText.getText().toString();
        Date date = new Date();

        if (carType.length() == 0 || licensePlate.length() == 0 || userName.length() == 0 || phoneNumber.length() == 0) {
            Toast.makeText(getActivity(), "אנא מלא את כל הפרטים", Toast.LENGTH_SHORT).show();
            return;
        }

        StoreModel.uploadImage(imageBitmap, userName + date.getTime(), new StoreModel.Listener() {
            @Override
            public void onSuccess(String url) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Problem problem = new Problem(uid, problemLocation, problemType, carType, licensePlate, userName, phoneNumber, url);
                ProblemModel.instance.addProblem(problem, new ProblemModel.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        NavController navCtrl = Navigation.findNavController(view);
                        NavDirections direction = NewProblemDetailsFragmentDirections.actionNewProblemDetailsFragmentToNavigationHome();
                        navCtrl.navigate(direction);
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
            ImageView checkboxImage = view.findViewById(R.id.new_problem_details_checkbox_image);
            checkboxImage.setVisibility(View.VISIBLE);
        }
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}