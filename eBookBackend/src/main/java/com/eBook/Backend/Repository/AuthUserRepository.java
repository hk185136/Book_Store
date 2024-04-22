package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eBook.Backend.models.AuthUser;

@Repository
// Used to perform CRUD operations related to user data.
public interface AuthUserRepository extends MongoRepository<AuthUser, String>{
	// Fetches a user.
	Optional<AuthUser> findByusername(String username);
	// Fetches users with a particular role(customer/admin).
	List<AuthUser> findByRole(String role);
}
