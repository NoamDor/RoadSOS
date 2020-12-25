package com.example.roadsos.ui.problems;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadsos.R;
import com.example.roadsos.model.Problem;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ProblemsFragment extends Fragment {

    RecyclerView list;
    ProblemsAdapter adapter;
    ProblemsViewModel viewModel;
    LiveData<List<Problem>> liveData;
    List<Problem> data = new LinkedList<Problem>();

    private ProblemsViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(ProblemsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_problems, container, false);

        list = view.findViewById(R.id.home_list);
        list.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext() );
        list.setLayoutManager(layoutManager);

        adapter = new ProblemsAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((position) -> {
            Problem problem = data.get(position);
            NavDirections direction = ProblemsFragmentDirections.actionProblemsFragmentToProblemDetailsFragment(problem);
            Navigation.findNavController(view).navigate(direction);
        });

        liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner() , (problems) -> {
            data = problems;
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(ProblemsViewModel.class);
    }

    public static class ProblemViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ProblemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            view = itemView;

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onClick(position);
                    }
                }
            });
        }

        public void bind(Problem problem) {
            TextView problemType = view.findViewById(R.id.problem_row_type_tv);
            ImageView problemTypeImage = view.findViewById(R.id.problem_row_type_img);
            ImageView carImage = view.findViewById(R.id.problem_row_car_img);

            problemType.setText(problem.getProblemType().getName());
            Picasso.get().load(problem.getProblemType().getImageUrl()).into(problemTypeImage);
            Picasso.get().load(problem.getCarImageUrl()).into(carImage);
        }
    }

    public class ProblemsAdapter extends RecyclerView.Adapter<ProblemViewHolder> {
        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.problem_row, parent, false);
            return new ProblemViewHolder(v, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
            holder.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}