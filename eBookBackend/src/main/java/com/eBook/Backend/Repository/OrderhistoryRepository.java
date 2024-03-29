package com.eBook.Backend.Repository;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eBook.Backend.models.AuthUser;
import com.eBook.Backend.models.Item;
import com.eBook.Backend.models.Orderhistory;




public interface OrderhistoryRepository extends MongoRepository<Orderhistory, String>
{
	@Query(value = "{'item.user.username': ?0}")
	List<Orderhistory> findByUsername(String username,Sort sort);

	
	

	


}
