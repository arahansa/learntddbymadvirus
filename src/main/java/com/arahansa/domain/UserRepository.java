package com.arahansa.domain;

/**
 * Created by jarvis on 2016. 10. 11..
 */
public interface UserRepository {
  public <T> T findById(String id);
}
