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


import com.eBook.Backend.Repository.CartAndOrderRepository;
import com.eBook.Backend.config.OrderStatusConfig;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.NotificationSubscription;
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
	
	// Gets an item based on an id.
	public Item getItemById(String id)
	{
		return CartRepository.findById(id).get();
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
	        	Item storedItem  =  CartRepository.findById(item.getId()).get();
	    		if((storedItem.getStatus()==null) || !storedItem.getStatus().equals(OrderStatusConfig.cancelled)) {
		        	updateItemStatus(item, status);
		        	startOrderStep(item,status,deliveryTime);
	    		}
	        }
	    };
	    
	    Timer Timer = new Timer("Delivery Timer");
	    
	    Timer.schedule(startTimer, deliveryTime);
	}
	
	
	// Accepts item, order status, delivery time, calls the scheduler function.
	public void performOrderStep(Item item, String orderStatus, int time)
	{
			itemDeliveryTimer(item, orderStatus, time);	
	}
	
	
	// Accepts item, order status, delivery time , updates the item's status, adds that item to the order history, sends a notification displaying the current status of that item.
	public void startOrderStep(Item item, String orderStatus, long time)
	{
		Item storedItem = updateItemStatus(item, orderStatus);
		updateItemOrderedDate(storedItem);
		OrderHistory history = orderHistoryImplementation.setItemToHistory(storedItem);
		orderHistoryImplementation.addtoHistory(history);
		Notification statusNotification = notificationServiceImplementation.addNotifcation(storedItem.getUser().getUsername(), storedItem.getBook().getTitle()+" is "+orderStatus);
		NotificationSubscription subscription = new NotificationSubscription();
		subscription.setItem(storedItem);
		subscription.setBook(storedItem.getBook());

		notificationServiceImplementation.dispatchNotification(notificationSubscriptionServiceImplementation.emitters,"Order status", statusNotification,subscription);
	}
	
	
}
