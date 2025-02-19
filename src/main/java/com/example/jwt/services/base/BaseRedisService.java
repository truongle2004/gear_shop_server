package com.example.jwt.services.base;

public interface BaseRedisService {
    // /**
    // * set value to redis by key and value
    // *
    // * @param key
    // * @param value
    // */
    // void set(String key, String value);

    /**
     * get value from redis by key
     * 
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * delete value from redis by key
     * 
     * @param key
     */
    void delete(String key);

    /**
     * Get value from redis by key
     * 
     * @param key
     * @param field
     * @return {@link Object}
     */
    Object hashGet(String key, String field);

    /**
     * Set value to redis by a hash
     * 
     * @param key
     * @param field
     * @param value
     */
    void hashSet(String key, String field, Object value);

    /**
     * check if hash exist in redis return true else return false
     * 
     * @param key
     * @param field
     * @return
     */
    boolean hashExist(String key, String field);
}
