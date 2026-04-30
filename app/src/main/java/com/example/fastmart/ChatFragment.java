package com.example.fastmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastmart.model.Message;
import com.example.fastmart.viewmodel.ChatViewModel;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private RecyclerView rvChat;
    private EditText etMessage;
    private ImageButton btnSend;
    private ChatAdapter adapter;
    private ChatViewModel chatViewModel;
    private String currentUserId;
    private String receiverId;
    private String chatId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        rvChat = view.findViewById(R.id.rvChat);
        etMessage = view.findViewById(R.id.etChatMessage);
        btnSend = view.findViewById(R.id.btnSendChat);

        SharedPreferences sp = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        currentUserId = sp.getString("userId", "");
        
        // In a real app, receiverId would be passed via arguments (e.g., from Product Description)
        receiverId = getArguments() != null ? getArguments().getString("receiverId") : "default_seller";
        
        // Unique chatId for the pair
        if (currentUserId.compareTo(receiverId) < 0) {
            chatId = currentUserId + "_" + receiverId;
        } else {
            chatId = receiverId + "_" + currentUserId;
        }

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatAdapter(new ArrayList<>(), currentUserId);
        rvChat.setAdapter(adapter);

        chatViewModel.listenForMessages(chatId);
        chatViewModel.getMessagesLiveData().observe(getViewLifecycleOwner(), messages -> {
            adapter = new ChatAdapter(messages, currentUserId);
            rvChat.setAdapter(adapter);
            if (!messages.isEmpty()) {
                rvChat.scrollToPosition(messages.size() - 1);
            }
        });

        btnSend.setOnClickListener(v -> {
            String msgText = etMessage.getText().toString().trim();
            if (!msgText.isEmpty()) {
                Message message = new Message(currentUserId, receiverId, msgText, System.currentTimeMillis());
                chatViewModel.sendMessage(chatId, message);
                etMessage.setText("");
            }
        });

        return view;
    }
}
