package com.srp.users.repository;

import com.srp.users.entities.RegisterEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRegisterRepository extends CrudRepository<RegisterEntity, String> {

    @Query("from RegisterEntity where userEmail=:email")
    Optional<RegisterEntity> findByUserEmail(@Param("email") String email);
}
