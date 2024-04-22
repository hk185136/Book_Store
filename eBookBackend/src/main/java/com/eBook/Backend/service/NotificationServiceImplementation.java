package com.eBook.Backend.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.eBook.Backend.Repository.NotificationRepository;
import com.eBook.Backend.models.NotificationSubscription;
import com.eBook.Backend.models.Notification;


@Service
// Provides services related to notifications management.
public class NotificationServiceImplementation {
	@Autowired
	private NotificationRepository notificationRepository;
	
	
	// Get notifications of a particular user.
	public Optional<List<Notification>> getNotificationByUsername(String username){
		return notificationRepository.findByUsername(username);
	}
	
	
	// Add a new notification.
	public Notification addNotifcation(String username, String message) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Notification notification = new Notification();
		notification.setDate(dateFormat.format(new Date()));
		notification.setUsername(username);
		notification.setMessage(message);
		return notificationRepository.save(notification);
	}
	
	// Delete a notification of a particular id.
	public void deleteNotificationById(String id) {
		notificationRepository.deleteById(id);
	}
	
	// dispatch a notification related to a particular book and user.
	public void dispatchNotification(Map<String, SseEmitter>notificationEmitters, String eventName, Notification notification,NotificationSubscription subscription)
	{
		String eventFormatted = new JSONObject()
				.put("message", notification.getMessage())
				.put("bookid",subscription.getBook().getId())
				.put("date", notification.getDate()).toString();
		SseEmitter sseEmitter = notificationEmitters.get(notification.getUsername());
		if(sseEmitter !=null) {
			try {
				sseEmitter.send(SseEmitter.event().name(eventName).data(eventFormatted));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// delete a notification of a user.
	public void deleteNotificationByUsername(String username) {
		// TODO Auto-generated method stub
		notificationRepository.deleteAll(notificationRepository.findByUsername(username).get());
	}
}
