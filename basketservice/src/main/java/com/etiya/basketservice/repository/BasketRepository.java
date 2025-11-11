package com.etiya.basketservice.repository;

import com.etiya.basketservice.domain.Basket;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BasketRepository {

    public static final String KEY = "BASKET";

    private final HashOperations<String, String, Basket> basketHashOperations;

    public BasketRepository(RedisTemplate<String, Object> redisTemplate) {
        this.basketHashOperations = redisTemplate.opsForHash();
    }

    public void saveBasket(int billingAccId, Basket basket) {
        if (basket == null) return;
        // Field = billingAccId → tek kayıt
        basketHashOperations.put(KEY, String.valueOf(billingAccId), basket);
    }

    public Basket getBasketByBillingAccountId(int billingAccId) {
        return basketHashOperations.get(KEY, String.valueOf(billingAccId));
    }

    public void deleteBasket(int billingAccId) {
        basketHashOperations.delete(KEY, String.valueOf(billingAccId));
    }


    public Map<String, Basket> getAll() {
        return basketHashOperations.entries(KEY);
    }

    public Map<String, Basket> getBasketMap() {
        return this.basketHashOperations.entries(KEY);
    }



}
