package com.eBook.Backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.MessageRepository;
import com.eBook.Backend.models.Message;

@Service
public class MessageServiceImplementation {
	@Autowired
	private MessageRepository messageRepository;
	public Optional<List<Message>> getMessagesByUsername(String username){
		return messageRepository.findByUsername(username);
	}
	public void addMessage(Message msg) {
		messageRepository.save(msg);
	}
	public void deleteById(String id) {
		messageRepository.deleteById(id);
	}
}
