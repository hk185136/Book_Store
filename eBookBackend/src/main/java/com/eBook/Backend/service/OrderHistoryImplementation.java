package com.eBook.Backend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.CartAndOrderRepository;
import com.eBook.Backend.Repository.OrderHistoryRepository;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;


@Service
public class OrderHistoryImplementation 
{
     @Autowired
	public OrderHistoryRepository orderrepo;
     
     // Accepts item data and sets item to a history entry and returns that entry.
     public OrderHistory setItemToHistory(Item item)
     {
    	 OrderHistory orderHistory = new OrderHistory();
    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 orderHistory.setItem(item);
    	 orderHistory.setDate(dateFormat.format(new Date()));
    	 return orderHistory;
     }
     
     
	public OrderHistory addtoHistory(OrderHistory orderHistory) {
		OrderHistory his=orderrepo.save(orderHistory);
		return his;
	}

	public List<OrderHistory> findOrderHistoryByUsername(String username)
	{
		Sort sort= Sort.by(Sort.Direction.DESC,"date");
		List<OrderHistory> history =orderrepo.findByUsername(username,sort);
		return history;
	}
	
	public void deleteById(String id) {
		orderrepo.deleteById(id);
	}
	
	
	
	public void deleteAll(String username) {
		List<String>ids = new ArrayList<>();
		List<OrderHistory> items = orderrepo.findByUsername(username, null);
		for(OrderHistory item : items) {
			ids.add(item.getId());
		}
		orderrepo.deleteAllById(ids);;
	}

}




