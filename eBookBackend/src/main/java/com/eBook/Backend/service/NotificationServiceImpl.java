package com.eBook.Backend.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.eBook.Backend.Repository.NotificationRepository;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Notification;

@Service
public class NotificationServiceImpl {
	@Autowired
	private NotificationRepository notificationRepository;
		
	public Map<String, SseEmitter> emitters = new HashMap<>();
	
	public Notification addtoNotifications(Notification notification) {
		Notification nn = notificationRepository.save(notification);
		return nn;
	}	
	
	public void deleteById(String id) {
		notificationRepository.deleteById(id);
	}
	
	public Optional<Notification> getNotificationsByTitleAndUsername(String title, String username)
	{
		return notificationRepository.findByTitleAndUsername(title, username);
	}
	
	public Optional<Notification> getNotificationsByStatusAndUsernameAndTitle(String status, String username, String title)
	{
		return notificationRepository.findByStatusAndUsernameAndTitle(status, username, title);
	}
	
	
	public SseEmitter initUserSubscription(String username)
	{
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("Initialize subscription"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitters.put(username, sseEmitter);
		return sseEmitter;
	}
	
	public void dispatchNotification(String username, String eventName, Notification notification)
	{
		String eventFormatted = new JSONObject()
				.put("title", notification.getNotifcationTitle())
				.put("description", notification.getDescription()).toString();
		SseEmitter sseEmitter = emitters.get(username);
		if(sseEmitter !=null) {
			try {
				sseEmitter.send(SseEmitter.event().name(eventName).data(eventFormatted));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
