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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import kotlinx.coroutines.flow.SharedFlow;

public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;

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

        btnLogin.setOnClickListener(v -> {
            String inputEmail = etEmail.getText().toString().trim();
            String inputPassword = etPassword.getText().toString().trim();
            if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            String savedEmail = sp.getString("savedEmail", "");
            String savedPassword = sp.getString("savedPassword", "");

            if (inputEmail.equals(savedEmail) && inputPassword.equals(savedPassword)) {
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("loggedIn", true);
                ed.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}