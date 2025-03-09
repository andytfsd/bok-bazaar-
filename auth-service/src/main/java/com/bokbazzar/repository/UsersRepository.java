package com.bokbazzar.repository;

import com.bokbazzar.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);
}
