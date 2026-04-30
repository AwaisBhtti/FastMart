package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
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
        btnLogin = view.findViewById(R.id.btnLogin);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            authViewModel.login(email, password);
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
            } else {
                // This might be called if user data hasn't arrived yet or login failed
                // Handled in repository/viewmodel usually with a status
            }
        });
    }
}
