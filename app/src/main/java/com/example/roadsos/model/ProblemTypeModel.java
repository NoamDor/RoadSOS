package com.example.roadsos.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProblemTypeModel {
    public static final ProblemTypeModel instance = new ProblemTypeModel();

    public interface CompListener {
        void onComplete();
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public void refreshProblemTypesList(CompListener listener) {
        ProblemTypeFirebase.getAllProblemTypes(new Listener<List<ProblemType>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<ProblemType> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (ProblemType pt : data) {
                            AppLocalDb.db.problemTypeDao().insertAll(pt);
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


    public LiveData<List<ProblemType>> getAllProblemTypes() {
        LiveData<List<ProblemType>> liveData = AppLocalDb.db.problemTypeDao().getAll();
        refreshProblemTypesList(null);
        return liveData;
    }
}
