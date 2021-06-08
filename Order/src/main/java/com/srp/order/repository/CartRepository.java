package com.srp.order.repository;


import com.srp.order.entity.CartEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends CrudRepository<CartEntity, Long> {

    @Query("from CartEntity where userId=:userId and status = 0")
    Optional<CartEntity> findByUserIdAndActive(@Param("userId") Long userId);
}
