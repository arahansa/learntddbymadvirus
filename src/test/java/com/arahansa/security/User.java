package com.arahansa.security;

/**
 * Created by jarvis on 2016. 10. 12..
 */
public class User extends Authentication{


  public User(String id, String password) {
    this.id = id;
    this.password = password;
  }

}
