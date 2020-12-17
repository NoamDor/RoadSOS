package com.example.roadsos.ui.problems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;

import java.util.List;

public class ProblemsViewModel extends ViewModel {
    private LiveData<List<Problem>> liveData;

    public LiveData<List<Problem>> getData() {
        if (liveData == null) {
            liveData = ProblemModel.instance.getAllProblems();
        }

        return liveData;
    }
}