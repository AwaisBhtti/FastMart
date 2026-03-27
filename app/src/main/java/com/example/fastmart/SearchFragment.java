package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private ImageView btnBack;
    private TextView btnClearAll;
    private SharedPreferences sp;

    private RecyclerView rvSearchHistory;
    private SearchHistoryAdapter historyAdapter;
    private List<String> historyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        etSearch = view.findViewById(R.id.etSearch);
        btnBack = view.findViewById(R.id.btnSearchBack);
        btnClearAll = view.findViewById(R.id.btnClearAll);
        rvSearchHistory = view.findViewById(R.id.rvSearchHistory);

        sp = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        historyList = new ArrayList<>();
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        loadHistory();
        btnBack.setOnClickListener(v -> hideKeyboard());
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = etSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    saveToHistory(query);
                    runSearchAlgorithm(query);
                    hideKeyboard();
                }
                return true;
            }
            return false;
        });

        btnClearAll.setOnClickListener(v -> {
            sp.edit().remove("user1.searchHistory").apply();
            historyList.clear();
            if (historyAdapter != null) {
                historyAdapter.notifyDataSetChanged();
            }
            Toast.makeText(getContext(), "History Cleared", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadHistory() {
        Set<String> savedHistory = sp.getStringSet("user1.searchHistory", new HashSet<>());
        historyList.clear();
        historyList.addAll(savedHistory);

        historyAdapter = new SearchHistoryAdapter(historyList);
        rvSearchHistory.setAdapter(historyAdapter);
    }

    private void runSearchAlgorithm(String query) {
        List<Product> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String id = String.valueOf(i);
            int pattern = i % 4;
            if (pattern == 1) {
                list.add(new Product(id, "RØDE PodMic", 199.99, 199.99, "Model: WH-1000XM4, Black", "Microphone", R.drawable.mic_rode));
            } else if (pattern == 2) {
                list.add(new Product(id, "SONY Headphones", 399.99, 399.99, "Model: WH-1000XM5, Beige", "Headphone", R.drawable.headphones_beige));
            } else if (pattern == 3) {
                list.add(new Product(id, "Google Nest Mini", 99.99, 99.99, "Model: WH-1000XM6, White", "Nest-Mini", R.drawable.nest_mini));
            } else {
                list.add(new Product(id, "SONY Headphones", 399.99, 399.99, "Model: WH-1000XM5, Black", "Headphone", R.drawable.headphones_black));
            }
        }

        boolean isFound = false;
        for (Product p : list) {
            if (p.getTitle().equalsIgnoreCase(query)) {
                isFound = true;
                break;
            }
        }

        if (isFound) {
            new AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.product_found))
                    .setPositiveButton(getString(R.string.ok), null)
                    .show();
        }
    }

    private void saveToHistory(String query) {
        Set<String> history = sp.getStringSet("user1.searchHistory", new HashSet<>());
        Set<String> newHistory = new HashSet<>(history);
        newHistory.add(query);
        sp.edit().putStringSet("user1.searchHistory", newHistory).apply();

        loadHistory();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }
}