package com.eBook.Backend.Repository;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.OrderHistory;


@Repository
public interface OrderHistoryRepository extends MongoRepository<OrderHistory, String>
{
	@Query(value = "{'item.user.username': ?0}")
	List<OrderHistory> findByUsername(String username,Sort sort);
}