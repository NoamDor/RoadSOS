package com.example.roadsos.ui.problems;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roadsos.R;
import com.example.roadsos.model.Problem;
import com.example.roadsos.ui.problems.ProblemDetailsFragmentArgs;
import com.squareup.picasso.Picasso;

public class ProblemDetailsFragment extends Fragment {

    public ProblemDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_problem_details, container, false);
        Problem problem = ProblemDetailsFragmentArgs.fromBundle(getArguments()).getProblem();

        TextView licensePlate = view.findViewById(R.id.problem_details_license_plate_tv);
        TextView carType = view.findViewById(R.id.problem_details_car_type_tv);
        TextView phoneNumber = view.findViewById(R.id.problem_details_phone_number_tv);
//        TextView problemType = view.findViewById(R.id.problem_row_type_tv);
        TextView userName = view.findViewById(R.id.problem_details_user_name_tv);
//        ImageView problemTypeImage = view.findViewById(R.id.problem_row_type_img);
        ImageView carImage = view.findViewById(R.id.problem_details_car_img);

        carType.setText(problem.getCarType());
        licensePlate.setText(problem.getLicensePlate());
        phoneNumber.setText(problem.getPhoneNumber());
//        problemType.setText(problem.getProblemType().getName());
        userName.setText(problem.getUserName());
//        Picasso.get().load(problem.getProblemType().getImageUrl()).into(problemTypeImage);
        Picasso.get().load(problem.getCarImageUrl()).into(carImage);

        return view;
    }
}