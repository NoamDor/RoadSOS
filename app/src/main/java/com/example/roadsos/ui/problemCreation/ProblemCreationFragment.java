package com.example.roadsos.ui.problemCreation;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadsos.R;
import com.example.roadsos.model.ProblemType;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class ProblemCreationFragment extends Fragment {

    RecyclerView list;
    List<ProblemType> data = new LinkedList<ProblemType>();
    ProblemTypesListAdpater adapter;
    Integer selectedViewPosition = -1;
    ProblemCreationViewModel viewModel;
    LiveData<List<ProblemType>> liveData;
    View selectedView;

    public ProblemCreationFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_creation, container, false);
        list = view.findViewById(R.id.problem_type_list);
        list.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext() );
        list.setLayoutManager(layoutManager);

        adapter = new ProblemTypesListAdpater();
        list.setAdapter(adapter);

        list.addItemDecoration(new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL));

        liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner() , (problems) -> {
            data = problems;
            adapter.notifyDataSetChanged();
        });

        Button continueBtn = view.findViewById(R.id.problem_creation_continue_btn);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (selectedViewPosition != -1) {
                    View prevSelectedView = list.findViewHolderForAdapterPosition(selectedViewPosition).itemView;
                    prevSelectedView.setBackgroundColor(Color.WHITE);
                    TextView prevSelectedText = prevSelectedView.findViewById(R.id.problem_type_name_tv);
                    prevSelectedText.setTextColor(Color.BLACK);
                }

                View currSelectedView = list.findViewHolderForAdapterPosition(position).itemView;
                selectedView = currSelectedView;
                currSelectedView.setBackgroundColor(Color.parseColor("#33D7FF"));
                TextView currSelectedText = currSelectedView.findViewById(R.id.problem_type_name_tv);
                currSelectedText.setTextColor(Color.WHITE);

                selectedViewPosition = position;
                continueBtn.setEnabled(true);

            }
        });

        continueBtn.setOnClickListener(v -> {
//            NavDirections direction =
//                    ProblemCreationFragmentDirections.actionProblemCreationFragmentToProblemDetailsFragment(data.get(selectedViewPosition));
            NavDirections direction2 = ProblemCreationFragmentDirections.actionProblemCreationFragmentToProblemLocationFragment();
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(direction2);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(ProblemCreationViewModel.class);
    }

    public static class ProblemTypeViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ProblemTypeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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

        public void bind(ProblemType pt) {
            TextView problemName = view.findViewById(R.id.problem_type_name_tv);
            ImageView problemImage = view.findViewById(R.id.problem_type_image_tv);
            problemName.setText(pt.getName());
            Picasso.get().load(pt.getImageUrl()).into(problemImage);
        }
    }

    public class ProblemTypesListAdpater extends RecyclerView.Adapter<ProblemTypeViewHolder> {
        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public ProblemTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.problem_type_row, parent, false);
            return new ProblemTypeViewHolder(v, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ProblemTypeViewHolder holder, int position) {
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