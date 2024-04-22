package com.eBook.Backend.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Flow.Subscription;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.eBook.Backend.Repository.NotificationRepository;
import com.eBook.Backend.models.NotficationSubscription;
import com.eBook.Backend.models.Notification;


@Service
public class NotificationServiceImplementation {
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	public Optional<List<Notification>> getNotificationByUsername(String username){
		return notificationRepository.findByUsername(username);
	}
	
	public Notification addNotifcation(String username, String message) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Notification notification = new Notification();
		notification.setDate(dateFormat.format(new Date()));
		notification.setUsername(username);
		notification.setMessage(message);
		return notificationRepository.save(notification);
	}
	
	public void deleteNotificationById(String id) {
		notificationRepository.deleteById(id);
	}
	
	public void dispatchNotification(Map<String, SseEmitter>notificationEmitters, String eventName, Notification refillNotification,NotficationSubscription subscription)
	{
		String eventFormatted = new JSONObject()
				.put("message", refillNotification.getMessage())
				.put("bookid",subscription.getBook().getId())
				.put("date", refillNotification.getDate()).toString();
		SseEmitter sseEmitter = notificationEmitters.get(refillNotification.getUsername());
		if(sseEmitter !=null) {
			try {

				sseEmitter.send(SseEmitter.event().name(eventName).data(eventFormatted));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteNotificationByUsername(String username) {
		// TODO Auto-generated method stub
		notificationRepository.deleteAll(notificationRepository.findByUsername(username).get());
	}
}
