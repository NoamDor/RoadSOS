package com.example.roadsos.model;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.LinkedList;
import java.util.List;

public class ProblemTypeFirebase {
    final static String PROBLEM_TYPES_COLLECTION = "problemTypes";

    public static void getAllProblems(final ProblemTypeModel.Listener<List<ProblemType>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PROBLEM_TYPES_COLLECTION).get().addOnCompleteListener((task -> {
            List<ProblemType> data = null;

            if (task.isSuccessful()) {
                data = new LinkedList<ProblemType>();

                for (QueryDocumentSnapshot doc : task.getResult()) {
                    ProblemType problem = doc.toObject(ProblemType.class);
                    data.add(problem);
                }
            }

            listener.onComplete(data);
        }));
    }
}
