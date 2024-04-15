package com.eBook.Backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.eBook.Backend.Repository.OrderHistoryRepository;
import com.eBook.Backend.models.OrderHistory;


@Service
public class OrderHistoryImplementation 
{
     @Autowired
	public OrderHistoryRepository orderrepo;
     
	public OrderHistory addtoHistory(OrderHistory order) {
		
		OrderHistory ord=orderrepo.save(order);
		return ord;
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




