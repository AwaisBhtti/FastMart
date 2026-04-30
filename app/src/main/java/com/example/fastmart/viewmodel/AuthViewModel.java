package com.example.fastmart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fastmart.model.User;
import com.example.fastmart.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private LiveData<FirebaseUser> userLiveData;
    private LiveData<User> userDataLiveData;
    private LiveData<String> errorLiveData;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository();
        userLiveData = authRepository.getUserLiveData();
        userDataLiveData = authRepository.getUserDataLiveData();
        errorLiveData = authRepository.getErrorLiveData();
    }

    public void login(String email, String password) {
        authRepository.login(email, password);
    }

    public void signUp(User user, String email, String password) {
        authRepository.signUp(user, email, password);
    }

    public void fetchUserData(String userId) {
        authRepository.fetchUserData(userId);
    }

    public void logOut() {
        authRepository.logOut();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<User> getUserDataLiveData() {
        return userDataLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
