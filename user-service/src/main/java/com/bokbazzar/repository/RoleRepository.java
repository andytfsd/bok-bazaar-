package com.bokbazzar.repository;

import com.bokbazzar.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
