package com.example.roadsos.model;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProblemFirebase {
    final static String PROBLEM_COLLECTION = "problems";

    public static void addProblem(Problem problem, final ProblemModel.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PROBLEM_COLLECTION).document(problem.getId()).set(problem.toMap()).addOnCompleteListener(task -> {
            if (listener != null) {
                listener.onComplete(task.isSuccessful());
            }
        });
    }
}
