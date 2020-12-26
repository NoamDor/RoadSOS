package com.example.roadsos.ui.problems;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roadsos.R;
import com.example.roadsos.enums.StatusCode;
import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;
import com.example.roadsos.model.ProblemStatus;
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
        TextView userName = view.findViewById(R.id.problem_details_user_name_tv);
        ImageView carImage = view.findViewById(R.id.problem_details_car_img);
        Button saveBtn = view.findViewById(R.id.problem_details_help_btn);

        carType.setText(problem.getCarType());
        licensePlate.setText(problem.getLicensePlate());
        phoneNumber.setText(problem.getPhoneNumber());
        userName.setText(problem.getUserName());
        Picasso.get().load(problem.getCarImageUrl()).into(carImage);
        saveBtn.setEnabled(problem.getStatus().code == StatusCode.NEW.getValue());

        saveBtn.setOnClickListener(b -> {
            Toast.makeText(getActivity(), "תודה שבחרת לסייע!",
                    Toast.LENGTH_SHORT).show();
            b.setEnabled(false);
            problem.setStatus(new ProblemStatus(StatusCode.OCCUPIED, "בטיפול"));
            ProblemModel.instance.updateProblem(problem, new ProblemModel.Listener<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    Log.d("TAG", "problem updated");
                }
            });
        });

        return view;
    }
}