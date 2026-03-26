package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment {

    private TextInputEditText etEmail, etPassword, etVerifyPassword;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etEmail = view.findViewById(R.id.etEmail2);
        etPassword = view.findViewById(R.id.etPassword2);
        etVerifyPassword = view.findViewById(R.id.etVerifyPassword);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String verifyPassword = etVerifyPassword.getText().toString().trim();
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Please enter a valid email");
                return;
            }
            if (password.length() < 8) {
                etPassword.setError("Password must be at least 8 characters");
                return;
            }
            if (!password.equals(verifyPassword)) {
                etVerifyPassword.setError("Passwords do not match");
                return;
            }

            SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("savedEmail", email);
            ed.putString("savedPassword", password);
            ed.commit();

            Toast.makeText(getContext(), "Registration Successful! Please Log In.", Toast.LENGTH_SHORT).show();
            etEmail.setText("");
            etPassword.setText("");
            etVerifyPassword.setText("");
            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
            if (viewPager != null) {
                viewPager.setCurrentItem(0, true);
            }
        });
    }
}