package com.eBook.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eBook.Backend.models.Message;
import com.eBook.Backend.service.MessageServiceImplementation;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user/messages")
@CrossOrigin(origins = "*")
public class MessageController {
	@Autowired
	private MessageServiceImplementation messageServiceImplementation;
	@GetMapping(value = "/getMessages/{username}")
	public ResponseEntity<List<Message>> getMessagesByUsername(@PathVariable String username){
		return ResponseEntity.ok(messageServiceImplementation.getMessagesByUsername(username).get());
	}
	@DeleteMapping(value = "/deleteMessage/{id}")
	public ResponseEntity<String> deleteNotificationByid(@PathVariable String id){
		messageServiceImplementation.deleteById(id);
		return ResponseEntity.status(200).body("Deleted successfully");
	}
}
