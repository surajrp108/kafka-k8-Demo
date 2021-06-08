package com.srp.inventory.repository;

import com.srp.inventory.entities.StockEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

public interface StockRepository extends CrudRepository<StockEntity, Long> {

    Stream<StockEntity> findByProductIdAndOrderIdIsNull(Long productId, Pageable page);

    Stream<StockEntity> findByOrderIdIn(List<Long> orderIds);

    @Query("select count(e.productId) from StockEntity e where e.productId=:id and status = 0")
    Long countOfProductAvailable(@Param("id") Long productId);
}
