package com.example.fastmart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fastmart.model.Message;
import com.example.fastmart.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private ChatRepository chatRepository;
    private LiveData<List<Message>> messagesLiveData;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatRepository = new ChatRepository();
        messagesLiveData = chatRepository.getMessagesLiveData();
    }

    public void sendMessage(String chatId, Message message) {
        chatRepository.sendMessage(chatId, message);
    }

    public void listenForMessages(String chatId) {
        chatRepository.listenForMessages(chatId);
    }

    public LiveData<List<Message>> getMessagesLiveData() {
        return messagesLiveData;
    }
}
