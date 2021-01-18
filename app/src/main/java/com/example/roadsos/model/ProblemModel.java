package com.example.roadsos.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProblemModel {
    public static final ProblemModel instance = new ProblemModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    public void refreshProblemsList(CompListener listener) {
        ProblemFirebase.getAllProblems(new Listener<List<Problem>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Problem> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        AppLocalDb.db.problemDao().delete();
                        for (Problem p : data) {
                            AppLocalDb.db.problemDao().insertAll(p);
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        if (listener != null) {
                            listener.onComplete();
                        }
                    }
                }.execute("");
            }
        });
    }

    public void refreshUserProblemsList(CompListener listener) {
        ProblemFirebase.getUserProblems(new Listener<List<Problem>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Problem> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        AppLocalDb.db.problemDao().delete();
                        for (Problem p : data) {
                            AppLocalDb.db.problemDao().insertAll(p);
                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        if (listener != null) {
                            listener.onComplete();
                        }
                    }
                }.execute("");
            }
        });
    }

    public LiveData<List<Problem>> getAllProblems() {
        LiveData<List<Problem>> liveData = AppLocalDb.db.problemDao().getAll();
        refreshProblemsList(null);
        return liveData;
    }

    public LiveData<List<Problem>> getUserProblems() {
        LiveData<List<Problem>> liveData = AppLocalDb.db.problemDao().getAll();
        refreshUserProblemsList(null);
        return liveData;
    }

    public void addProblem(Problem problem, Listener<Boolean> listener) {
        ProblemFirebase.upsertProblem(problem, listener);
        AsyncTask.execute(() -> AppLocalDb.db.problemDao().insertAll(problem));
    }

    public void updateProblem(Problem problem, Listener<Boolean> listener) {
        ProblemFirebase.upsertProblem(problem, listener);
        AsyncTask.execute(() -> AppLocalDb.db.problemDao().update(problem));
    }
}
