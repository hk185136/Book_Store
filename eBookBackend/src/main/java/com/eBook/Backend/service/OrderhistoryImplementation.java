package com.eBook.Backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eBook.Backend.Repository.OrderhistoryRepository;
import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.Orderhistory;


@Service
public class OrderhistoryImplementation 
{
     @Autowired
	public OrderhistoryRepository orderrepo;
     
	public Orderhistory addtoHistory(Orderhistory order) {
		
		Orderhistory ord=orderrepo.save(order);
		return ord;
	}

	public List<Orderhistory> findOrderhistoryByUsername(String username)
	{
		Sort sort= Sort.by(Sort.Direction.DESC,"date");
		List<Orderhistory> history =orderrepo.findByUsername(username,sort);
		return history;
	}
	public void deleteById(String id) {
		orderrepo.deleteById(id);
	}
	public void deleteAll(String username) {
		List<String>ids = new ArrayList();
		List<Orderhistory> items = orderrepo.findByUsername(username, null);
		for(Orderhistory item : items) {
			ids.add(item.getId());
		}
		orderrepo.deleteAllById(ids);;
	}

}




