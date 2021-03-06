package com.example.roadsos.ui.myProblems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.roadsos.R;
import com.example.roadsos.enums.StatusCode;
import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;
import com.example.roadsos.model.ProblemStatus;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import static com.example.roadsos.enums.StatusCode.DONE;
import static com.example.roadsos.enums.StatusCode.NEW;
import static com.example.roadsos.enums.StatusCode.OCCUPIED;

public class MyProblemsFragment extends Fragment {

    RecyclerView list;
    MyProblemsAdapter adapter;
    MyProblemsViewModel viewModel;
    LiveData<List<Problem>> liveData;
    List<Problem> data = new LinkedList<Problem>();
    SwipeRefreshLayout swipeRefresh;

    private MyProblemsViewModel myProblemsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myProblemsViewModel =
                new ViewModelProvider(this).get(MyProblemsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_my_problems, container, false);

        list = view.findViewById(R.id.my_problems_list);
        list.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext() );
        list.setLayoutManager(layoutManager);

        adapter = new MyProblemsAdapter();
        list.setAdapter(adapter);

        swipeRefresh = view.findViewById(R.id.my_problems_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refresh(() -> {
                swipeRefresh.setRefreshing(false);
            });
        });

        swipeRefresh.setRefreshing(true);
        liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner() , (problems) -> {
            data = problems;
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(MyProblemsViewModel.class);
    }

    public static class MyProblemViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView confirmBtn;
        ImageView closeBtn;
        ImageView statusImage;

        public MyProblemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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
            TextView status = view.findViewById(R.id.problem_row_status_tv);
            statusImage = view.findViewById(R.id.problem_row_status_img);
            TextView address = view.findViewById(R.id.problem_row_address_tv);
            ImageView editBtn = view.findViewById(R.id.problem_row_edit_btn);
            ImageView deleteBtn = view.findViewById(R.id.problem_row_delete_btn);
            confirmBtn = view.findViewById(R.id.problem_row_confirm_btn);
            closeBtn = view.findViewById(R.id.problem_row_close_btn);

            problemType.setText(problem.getProblemType().getName());
            Picasso.get().load(problem.getProblemType().getImageUrl()).into(problemTypeImage);
            Picasso.get().load(problem.getCarImageUrl()).into(carImage);
            status.setText(problem.getStatus().desc);
            address.setText(problem.getLocation().address);
            updateButtons(problem.getStatus().code);

            deleteBtn.setOnClickListener(v -> ProblemModel.instance.deleteProblem(problem, succeeded -> {
                if (succeeded)
                    Toast.makeText(view.getContext(), "בעיה נמחקה בהצלחה", Toast.LENGTH_SHORT).show();
            }));

            editBtn.setOnClickListener(v -> {
                NavController navCtrl = Navigation.findNavController(view);
                NavDirections direction = MyProblemsFragmentDirections.actionMyProblemsFragmentToEditMyProblemFragment(problem);
                navCtrl.navigate(direction);
            });

            confirmBtn.setOnClickListener(v -> {
                problem.setStatus(new ProblemStatus(StatusCode.DONE, "הסתיים"));
                ProblemModel.instance.updateProblem(problem, succeeded -> {
                    if (succeeded) {
                        confirmBtn.setVisibility(View.INVISIBLE);
                        closeBtn.setVisibility(View.INVISIBLE);
                    }
                });
            });

            closeBtn.setOnClickListener(v -> {
                problem.setStatus(new ProblemStatus(StatusCode.NEW, "חדש"));
                ProblemModel.instance.updateProblem(problem, succeeded -> {
                    if (succeeded) {
                        closeBtn.setEnabled(false);
                        closeBtn.setAlpha((float) 0.3);
                    }
                });
            });
        }

        private void updateButtons(int code) {
            if (code == NEW.getValue()) {
                confirmBtn.setEnabled(true);
                confirmBtn.setAlpha((float) 1);
                closeBtn.setEnabled(false);
                closeBtn.setAlpha((float) 0.3);
                statusImage.setImageResource(R.drawable.ic_resource_new);
            } else if (code == OCCUPIED.getValue()) {
                confirmBtn.setEnabled(true);
                confirmBtn.setAlpha((float) 1);
                closeBtn.setEnabled(true);
                closeBtn.setAlpha((float) 1);
                statusImage.setImageResource(R.drawable.ic_in_progress);
            } else {
                confirmBtn.setVisibility(View.INVISIBLE);
                closeBtn.setVisibility(View.INVISIBLE);
                statusImage.setImageResource(R.drawable.ic_finished);
            }
        }
    }

    public class MyProblemsAdapter extends RecyclerView.Adapter<MyProblemViewHolder> {
        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public MyProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.problem_row, parent, false);
            return new MyProblemViewHolder(v, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull MyProblemViewHolder holder, int position) {
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