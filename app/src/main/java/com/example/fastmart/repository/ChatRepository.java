package com.example.fastmart.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fastmart.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRepository {
    private DatabaseReference messagesRef;
    private MutableLiveData<List<Message>> messagesLiveData;

    public ChatRepository() {
        messagesRef = FirebaseDatabase.getInstance().getReference("messages");
        messagesLiveData = new MutableLiveData<>();
    }

    public void sendMessage(String chatId, Message message) {
        messagesRef.child(chatId).push().setValue(message);
    }

    public void listenForMessages(String chatId) {
        messagesRef.child(chatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    if (message != null) {
                        messages.add(message);
                    }
                }
                messagesLiveData.postValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public LiveData<List<Message>> getMessagesLiveData() {
        return messagesLiveData;
    }
}
