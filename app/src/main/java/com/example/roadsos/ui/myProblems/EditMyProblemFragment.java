package com.example.roadsos.ui.myProblems;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.roadsos.R;
import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;
import com.example.roadsos.ui.newProblem.NewProblemDetailsFragmentDirections;
import com.example.roadsos.utils.Listeners;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditMyProblemFragment extends Fragment {

    View view;
    Problem problem;
    TextInputEditText nameEt;
    TextInputEditText phoneEt;
    TextInputEditText carTypeEt;
    TextInputEditText licensePlateEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_my_problem, container, false);
        problem = EditMyProblemFragmentArgs.fromBundle(getArguments()).getProblem();

        TextInputLayout nameLayout = view.findViewById(R.id.edit_my_problem_name_layout);
        nameEt = view.findViewById(R.id.edit_my_problem_name_et);
        nameEt.setText(problem.getUserName());
        Listeners.addListenerToRequiredEditText(nameLayout, nameEt);

        TextInputLayout phoneLayout = view.findViewById(R.id.edit_my_problem_phone_layout);
        phoneEt = view.findViewById(R.id.edit_my_problem_phone_et);
        phoneEt.setText(problem.getPhoneNumber());
        Listeners.addListenerToRequiredEditText(phoneLayout, phoneEt);

        TextInputLayout carTypeLayout = view.findViewById(R.id.edit_my_problem_car_type_layout);
        carTypeEt = view.findViewById(R.id.edit_my_problem_car_type_et);
        carTypeEt.setText(problem.getCarType());
        Listeners.addListenerToRequiredEditText(carTypeLayout, carTypeEt);

        licensePlateEt = view.findViewById(R.id.edit_my_problem_license_plate_et);
        licensePlateEt.setText(problem.getLicensePlate());

        Button confirmBtn = view.findViewById(R.id.edit_my_problem_confirm_btn);
        confirmBtn.setOnClickListener(v -> editProblem());

        return view;
    }

    public void editProblem() {
        String carType = this.carTypeEt.getText().toString();
        String licensePlate = this.licensePlateEt.getText().toString();
        String userName = this.nameEt.getText().toString();
        String phoneNumber = this.phoneEt.getText().toString();

        if (carType.length() == 0 || licensePlate.length() == 0 || userName.length() == 0 || phoneNumber.length() == 0) {
            Toast.makeText(getActivity(), "אנא מלא את כל הפרטים", Toast.LENGTH_SHORT).show();
            return;
        }

        problem.setCarType(carType);
        problem.setLicensePlate(licensePlate);
        problem.setUserName(userName);
        problem.setPhoneNumber(phoneNumber);

        ProblemModel.instance.updateProblem(problem, new ProblemModel.Listener<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                NavController navCtrl = Navigation.findNavController(view);
                Toast.makeText(getActivity(), "מקרה עודכן בהצלחה", Toast.LENGTH_SHORT).show();
                navCtrl.popBackStack();
            }
        });
    }
}