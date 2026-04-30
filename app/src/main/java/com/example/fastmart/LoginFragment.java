package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fastmart.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private TextInputLayout lyEmail, lyPassword;
    private Button btnLogin;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        lyEmail = view.findViewById(R.id.lyEmail);
        lyPassword = view.findViewById(R.id.lyPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Clear errors when typing
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lyEmail.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lyPassword.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            boolean isValid = true;

            if (email.isEmpty()) {
                lyEmail.setError("Email is required");
                isValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                lyEmail.setError("Invalid email format");
                isValid = false;
            }

            if (password.isEmpty()) {
                lyPassword.setError("Password is required");
                isValid = false;
            } else if (password.length() < 6) {
                lyPassword.setError("Password must be at least 6 characters");
                isValid = false;
            }

            if (isValid) {
                authViewModel.login(email, password);
            }
        });

        authViewModel.getUserDataLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("userId", user.getUserId());
                ed.putString("name", user.getName());
                ed.putString("accountType", user.getAccountType());
                ed.putBoolean("loggedIn", true);
                ed.apply();

                Intent intent;
                if ("Seller".equalsIgnoreCase(user.getAccountType())) {
                    intent = new Intent(getActivity(), SellerMainActivity.class);
                } else {
                    intent = new Intent(getActivity(), MainActivity.class);
                }
                startActivity(intent);
                requireActivity().finish();
            }
        });

        authViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
