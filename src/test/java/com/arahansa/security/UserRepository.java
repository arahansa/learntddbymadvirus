package com.arahansa.security;

/**
 * Created by jarvis on 2016. 10. 11..
 */
public interface UserRepository {
  <T> T findById(String id);
}
