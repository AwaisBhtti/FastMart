package com.example.fastmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fastmart.viewmodel.AuthViewModel;
import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail, tvDob, tvGender, tvPhone;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        tvName = view.findViewById(R.id.tvAccName);
        tvEmail = view.findViewById(R.id.tvAccEmail);
        tvDob = view.findViewById(R.id.tvAccDob);
        tvGender = view.findViewById(R.id.tvAccGender);
        tvPhone = view.findViewById(R.id.tvAccPhone);
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String userId = sp.getString("userId", "");
        
        if (!userId.isEmpty()) {
            authViewModel.fetchUserData(userId);
        }

        authViewModel.getUserDataLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                tvName.setText(user.getName());
                tvEmail.setText(sp.getString("savedEmail", "")); // Note: In a real app, email should be in User model or from FirebaseAuth
                tvDob.setText(user.getDateOfBirth());
                tvGender.setText(user.getGender());
                tvPhone.setText(user.getPhone());
            }
        });

        btnLogout.setOnClickListener(v -> {
            authViewModel.logOut();
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}
