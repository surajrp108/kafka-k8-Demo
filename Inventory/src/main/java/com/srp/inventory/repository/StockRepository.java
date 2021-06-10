package com.srp.inventory.repository;

import com.srp.inventory.entities.StockEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class StockRepository implements PanacheRepository<StockEntity> {

    public Uni<List<StockEntity>> findByProductIdAndOrderIdIsNull(Long productId, int quantity) {
        return find("productId=$1 and orderId is null")
                .page(Page.of(0, quantity))
                .list();
    }

    public Uni<List<StockEntity>> findByOrderIdIn(List<Long> orderIds) {
        return find("orderId in ($1)", orderIds).list();
    }

    public Uni<Long> countOfProductAvailable(Long productId) {
        return count("productId= $1 and status=0", productId);
    }
}
