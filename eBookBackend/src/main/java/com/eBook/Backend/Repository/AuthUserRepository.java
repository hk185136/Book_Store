package com.eBook.Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eBook.Backend.models.AuthUser;

@Repository
public interface AuthUserRepository extends MongoRepository<AuthUser, String>{
	Optional<AuthUser> findByusername(String username);
	List<AuthUser> findByRole(String role);
}
