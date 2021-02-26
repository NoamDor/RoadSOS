package com.example.roadsos.ui.myProblems;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roadsos.R;
import com.example.roadsos.model.Problem;
import com.google.android.material.textfield.TextInputEditText;

public class EditMyProblemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_my_problem, container, false);
        Problem problem = EditMyProblemFragmentArgs.fromBundle(getArguments()).getProblem();

        TextInputEditText nameEt = view.findViewById(R.id.edit_my_problem_name_et);
        nameEt.setText(problem.getUserName());

        TextInputEditText phoneEt = view.findViewById(R.id.edit_my_problem_phone_et);
        phoneEt.setText(problem.getPhoneNumber());

        TextInputEditText carTypeEt = view.findViewById(R.id.edit_my_problem_car_type_et);
        carTypeEt.setText(problem.getCarType());

        TextInputEditText licensePlateEt = view.findViewById(R.id.edit_my_problem_license_plate_et);
        licensePlateEt.setText(problem.getLicensePlate());

        return view;
    }
}