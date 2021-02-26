package com.example.roadsos.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;

public class ProblemFirebase {
    final static String PROBLEM_COLLECTION = "problems";

    public static void getAllProblems(final ProblemModel.Listener<List<Problem>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PROBLEM_COLLECTION).get().addOnCompleteListener((task -> {
            List<Problem> data = null;

            if (task.isSuccessful()) {
                data = new LinkedList<Problem>();

                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Problem problem = doc.toObject(Problem.class);
                    data.add(problem);
                }
            }

            listener.onComplete(data);
        }));
    }

    public static void getUserProblems(final ProblemModel.Listener<List<Problem>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db.collection(PROBLEM_COLLECTION).whereEqualTo("uid",mAuth.getCurrentUser().getUid()).get().addOnCompleteListener((task -> {
            List<Problem> data = null;

            if (task.isSuccessful()) {
                data = new LinkedList<Problem>();

                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Problem problem = doc.toObject(Problem.class);
                    data.add(problem);
                }
            }

            listener.onComplete(data);
        }));
    }

    public static void upsertProblem(Problem problem, final ProblemModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PROBLEM_COLLECTION).document(problem.getId()).set(problem.toMap()).addOnCompleteListener(task -> {
            if (listener != null) {
                listener.onComplete(task.isSuccessful());
            }
        });
    }

    public static void deleteProblem(Problem problem, final ProblemModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PROBLEM_COLLECTION).document(problem.getId()).delete().addOnCompleteListener(task -> {
            listener.onComplete(task.isSuccessful());
        });
    }
}
