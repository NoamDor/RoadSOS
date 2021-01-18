package com.example.roadsos.ui.myProblems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.roadsos.model.Problem;
import com.example.roadsos.model.ProblemModel;

import java.util.List;

public class MyProblemsViewModel extends ViewModel {

    private LiveData<List<Problem>> liveData;

    public LiveData<List<Problem>> getData() {
        if (liveData == null) {
            liveData = ProblemModel.instance.getUserProblems();
        }

        return liveData;
    }

    public void refresh(ProblemModel.CompListener listener) {
        ProblemModel.instance.refreshUserProblemsList(listener);
    }
}