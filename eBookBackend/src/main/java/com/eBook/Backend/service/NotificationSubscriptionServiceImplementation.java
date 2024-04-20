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

import com.eBook.Backend.Repository.NotificationSubscriptionRepository;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Notification;
import com.eBook.Backend.models.NotficationSubscription;

@Service
public class NotificationSubscriptionServiceImplementation {
	@Autowired
	private NotificationSubscriptionRepository notificationSubscriptionRepository;

	public Map<String, SseEmitter> emitters = new HashMap<>();
	
	public NotficationSubscription addtoSubscriptions(NotficationSubscription notificationSubscription) {
		return notificationSubscriptionRepository.save(notificationSubscription);
	}	
	
	public void deleteSubscriptionById(String id) {
		notificationSubscriptionRepository.deleteById(id);
	}
	
	
	public Optional<List<NotficationSubscription>> getSubscriptionsByTitle(String title)
	{
		return notificationSubscriptionRepository.findByTitle(title);
	}
	
	public Optional<List<NotficationSubscription>> getSubscriptionsByStatusAndTitle(String status, String title)
	{
		return notificationSubscriptionRepository.findByStatusAndTitle(status, title);
	}
	
	public Optional<List<NotficationSubscription>> getSubscriptionsByUsename(String username)
	{
		return notificationSubscriptionRepository.findByUsername(username);
	}
	
	public SseEmitter initiateUserSubscription(String username)
	{
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		try {
			sseEmitter.send(SseEmitter.event().name("Initiate subscription"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitters.put(username, sseEmitter);
		return sseEmitter;
	}
}
