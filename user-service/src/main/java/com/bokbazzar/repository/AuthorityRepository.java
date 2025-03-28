package com.bokbazzar.repository;

import com.bokbazzar.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

    AuthorityEntity findByName(String name);
}
