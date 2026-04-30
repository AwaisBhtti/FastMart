package com.example.fastmart.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fastmart.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthRepository {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;
    private MutableLiveData<User> userDataLiveData;
    private MutableLiveData<String> errorLiveData;

    public AuthRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseReference = FirebaseDatabase.getInstance().getReference("users");
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.userDataLiveData = new MutableLiveData<>();
        this.errorLiveData = new MutableLiveData<>();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            userLiveData.postValue(currentUser);
            fetchUserData(currentUser.getUid());
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.postValue(user);
                        if (user != null) {
                            fetchUserData(user.getUid());
                        }
                    } else {
                        errorLiveData.postValue(task.getException() != null ? task.getException().getMessage() : "Login Failed");
                        userLiveData.postValue(null);
                    }
                });
    }

    public void signUp(User user, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        user.setUserId(userId);
                        // Save user details to database
                        databaseReference.child(userId).setValue(user)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                                        userDataLiveData.postValue(user);
                                    } else {
                                        // If DB fails, we still have the Auth user, but we should report the error
                                        errorLiveData.postValue("User created but profile failed: " + 
                                            (dbTask.getException() != null ? dbTask.getException().getMessage() : "Database Error"));
                                    }
                                });
                    } else {
                        errorLiveData.postValue(task.getException() != null ? task.getException().getMessage() : "Registration Failed");
                        userLiveData.postValue(null);
                    }
                });
    }

    public void fetchUserData(String userId) {
        databaseReference.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                User user = task.getResult().getValue(User.class);
                userDataLiveData.postValue(user);
            } else if (!task.isSuccessful()) {
                errorLiveData.postValue("Failed to fetch user data");
            }
        });
    }

    public void logOut() {
        firebaseAuth.signOut();
        userLiveData.postValue(null);
        userDataLiveData.postValue(null);
        loggedOutLiveData.postValue(true);
    }

    public LiveData<FirebaseUser> getUserLiveData() { return userLiveData; }
    public LiveData<Boolean> getLoggedOutLiveData() { return loggedOutLiveData; }
    public LiveData<User> getUserDataLiveData() { return userDataLiveData; }
    public LiveData<String> getErrorLiveData() { return errorLiveData; }
}
