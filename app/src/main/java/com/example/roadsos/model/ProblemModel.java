package com.example.roadsos.model;

import android.os.AsyncTask;

public class ProblemModel {
    public static final ProblemModel instance = new ProblemModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    public void addProblem(Problem problem, Listener<Boolean> listener) {
        ProblemFirebase.addProblem(problem, listener);
        AsyncTask.execute(() -> AppLocalDb.db.problemDao().insertAll(problem));
    }
}
