package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fastmart.model.User;
import com.example.fastmart.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment {

    private TextInputEditText etName, etEmail, etPassword, etAddress, etDob, etPhone, etCountry;
    private RadioGroup rgGender, rgAccountType;
    private Button btnSignUp;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmailSignup);
        etPassword = view.findViewById(R.id.etPasswordSignup);
        etAddress = view.findViewById(R.id.etAddress);
        etDob = view.findViewById(R.id.etDob);
        etPhone = view.findViewById(R.id.etPhone);
        etCountry = view.findViewById(R.id.etCountry);
        rgGender = view.findViewById(R.id.rgGender);
        rgAccountType = view.findViewById(R.id.rgAccountType);
        btnSignUp = view.findViewById(R.id.btnSignUp);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String country = etCountry.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || dob.isEmpty() || phone.isEmpty() || country.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Invalid email");
                return;
            }

            int selectedGenderId = rgGender.getCheckedRadioButtonId();
            String gender = selectedGenderId == R.id.rbMale ? "Male" : "Female";

            int selectedTypeId = rgAccountType.getCheckedRadioButtonId();
            String accountType = selectedTypeId == R.id.rbSeller ? "Seller" : "Buyer";

            User user = new User("", name, address, gender, dob, phone, country, accountType);
            authViewModel.signUp(user, email, password);
        });

        authViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
                if (viewPager != null) {
                    viewPager.setCurrentItem(0, true);
                }
            }
        });

        authViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
