package com.example.roadsos.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.roadsos.App;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
                        long lastUpdated = 0;
                        AppLocalDb.db.problemDao().delete();

                        for (Problem p : data) {
                            AppLocalDb.db.problemDao().insertAll(p);

                            if (p.getLastUpdated() > lastUpdated) {
                                lastUpdated = p.getLastUpdated();
                            }
                        }

                        SharedPreferences.Editor edit = App.context.getSharedPreferences("TAG", MODE_PRIVATE).edit();
                        edit.putLong("ProblemsLastUpdateDate",lastUpdated);
                        edit.commit();

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
                        long lastUpdated = 0;
                        AppLocalDb.db.problemDao().delete();

                        for (Problem p : data) {
                            AppLocalDb.db.problemDao().insertAll(p);

                            if (p.getLastUpdated() > lastUpdated) {
                                lastUpdated = p.getLastUpdated();
                            }
                        }

                        SharedPreferences.Editor edit = App.context.getSharedPreferences("TAG", MODE_PRIVATE).edit();
                        edit.putLong("UserProblemsLastUpdateDate",lastUpdated);
                        edit.commit();

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

    public void deleteProblem(Problem problem, Listener<Boolean> listener) {
        ProblemFirebase.deleteProblem(problem, listener);
        AsyncTask.execute(() -> AppLocalDb.db.problemDao().delete(problem));
    }
}
