package com.example.jwt.services.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.jwt.dto.request.CartItemRequest;
import com.example.jwt.services.CartRedisService;
import com.example.jwt.services.base.impl.BaseRedisServiceImpl;

@Service
public class CartRedisServiceImpl extends BaseRedisServiceImpl implements CartRedisService {

    public CartRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public void addProductToCart(CartItemRequest cartItemRequest) {
        String key = "cart:" + cartItemRequest.getUserId();

        int updateQuantity;

        // use StringBuilder for better performance than String concatenation
        StringBuilder fieldKeyBuilder = new StringBuilder("product:");

        fieldKeyBuilder.append(cartItemRequest.getProductId());

        // convert StringBuilder to String for easier to operate
        String fieldKey = fieldKeyBuilder.toString();

        // check if hash exist in redis
        if (this.hashExist(cartItemRequest.getUserId(), fieldKey)) {
            updateQuantity = (Integer) this.hashGet(cartItemRequest.getUserId(), fieldKey)
                    + cartItemRequest.getQuantity();
        } else {
            updateQuantity = cartItemRequest.getQuantity();
        }

        this.hashSet(key, fieldKey, updateQuantity);
    }

    @Override
    public void updateProductInCart(String userId, short quantity, int productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductInCart'");
    }

}
