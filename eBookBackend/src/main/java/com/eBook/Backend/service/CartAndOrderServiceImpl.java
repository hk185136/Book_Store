package com.eBook.Backend.service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.eBook.Backend.Repository.CartAndOrderRepository;

import com.eBook.Backend.Repository.OrderHistoryRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Book;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.NotficationSubscription;
import com.eBook.Backend.models.Notification;
import com.eBook.Backend.models.OrderHistory;

@Service
//Class which provides user defined CRUD operations for cart and orders.
public class CartAndOrderServiceImpl {
	
	@Autowired
	private CartAndOrderRepository CartRepository;
	
	@Autowired
	private NotificationSubscriptionServiceImplementation notificationSubscriptionServiceImplementation;
	
	@Autowired
	private NotificationServiceImplementation notificationServiceImplementation;
	
	@Autowired
	private OrderHistoryImplementation orderHistoryImplementation;
	
	// Saves an input item to database
	public Item addItem(Item Item)
	{
		Item itemAddedTocart = CartRepository.save(Item);
		return itemAddedTocart;
	}
	
	//Increases item quantity count by one.
	public Item increaseItem(Item item) {
		Item storedItem = CartRepository.findById(item.getId()).get();
		storedItem.setQuantity(storedItem.getQuantity()+1);
		return CartRepository.save(storedItem);
	}
	
	//Decreases item quantity count by one.
	public Item decreaseItem(Item item) {	
		Item storedItem = CartRepository.findById(item.getId()).get();
		storedItem.setQuantity(storedItem.getQuantity()-1);
		return CartRepository.save(storedItem);
	}
	
	//Deletes an item.
	public void deleteItem(String itemId) {
		CartRepository.deleteById(itemId);
	}
	
	// Returns a set of items related to input user name and status.
	public Set<Item> findItemsByStatusAndUsername(String status, String username)
	{
		List<Item>userItems =CartRepository.findByStatusAndUsername(status,username);
		Set<Item> Items = new HashSet<>();
		Items.addAll(userItems);
		return Items;
	}
	
	
	
	// Accepts item data, updates the ordered date field and returns item data.
	public Item updateItemOrderedDate(Item item)
	{
		Item storedItem  =  CartRepository.findById(item.getId()).get();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		storedItem.setDate(dateFormat.format(new Date()));
		CartRepository.save(storedItem);
		return storedItem;
	}
	
	// Updates an existing item with new status.
	public Item updateItemStatus(Item item, String status)
	{
		Item storedItem  =  CartRepository.findById(item.getId()).get();
		storedItem.setStatus(status);
		CartRepository.save(storedItem);
		return storedItem;
	}
	
	// Accepts item, status and delivery time and schedules item status updation task after delivery time.
	public void itemDeliveryTimer(Item item, String status, long deliveryTime)
	{
		TimerTask startTimer = new TimerTask() {
	        public void run() {
	        	updateItemStatus(item, status);
	        	performOrderStep1(item,status,deliveryTime);
	        }
	    };
	    
	    Timer Timer = new Timer("Delivery Timer");
	    
	    Timer.schedule(startTimer, deliveryTime);
	}
	
	
	public void performOrderStep(Item item, String orderStatus, int time)
	{
		if((item.getStatus()==null) || !item.getStatus().equals("Cancelled")) {
			itemDeliveryTimer(item, orderStatus, time);	
		}
			
	}
	public void performOrderStep1(Item item, String orderStatus, long time)
	{
		item.setStatus(orderStatus);
		updateItemOrderedDate(item);
		OrderHistory history = orderHistoryImplementation.setItemToHistory(item);
		orderHistoryImplementation.addtoHistory(history);
		List<NotficationSubscription> statusSubscriptions = notificationSubscriptionServiceImplementation.getSubscriptionsByStatusAndTitle(orderStatus, item.getBook().getTitle()).get();
		Notification statusNotification = notificationServiceImplementation.addNotifcation(item.getUser().getUsername(), item.getBook().getTitle()+" is "+orderStatus);
		NotficationSubscription subscription = new NotficationSubscription();
		subscription.setItem(item);
		subscription.setBook(item.getBook());
		notificationServiceImplementation.dispatchNotification(notificationSubscriptionServiceImplementation.emitters,"Order status", statusNotification,subscription);
	}
	
	
}
