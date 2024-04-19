package com.eBook.Backend.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.eBook.Backend.models.Message;
import com.eBook.Backend.models.Notification;

@Service
public class NotificationServiceImpl {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private MessageServiceImplementation messageServiceImplementation;
	public Map<String, SseEmitter> emitters = new HashMap<>();
	
	public Notification addtoNotifications(Notification notification) {
		Notification nn = notificationRepository.save(notification);
		return nn;
	}	
	
	public void deleteById(String id) {
		notificationRepository.deleteById(id);
	}
	public Optional<List<Notification>> getNotificationsByUser(String username)
	{
		return notificationRepository.findByUsername(username);
	}
	public Optional<List<Notification>> getNotificationsByTitle(String title)
	{
		return notificationRepository.findByTitle(title);
	}
	
	public Optional<List<Notification>> getNotificationsByStatusAndTitle(String status, String title)
	{
		return notificationRepository.findByStatusAndTitle(status, title);
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Message msg = new Message();
		msg.setDate(dateFormat.format(new Date()));
		msg.setMessage(notification.getDescription());
		msg.setUsername(username);
		messageServiceImplementation.addMessage(msg);
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
