package com.example.roadsos.ui.newProblem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.roadsos.model.ProblemType;
import com.example.roadsos.model.ProblemTypeModel;

import java.util.List;

public class NewProblemTypeViewModel extends ViewModel {
    private LiveData<List<ProblemType>> liveData;

    public LiveData<List<ProblemType>> getData() {
        if (liveData == null) {
            liveData = ProblemTypeModel.instance.getAllProblemTypes();
        }

        return liveData;
    }
}