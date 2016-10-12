package com.arahansa.domain;

import lombok.Data;

/**
 * Created by jarvis on 2016. 10. 12..
 */
@Data
public class User extends Authentication{


  public User(String id, String password) {
    this.id = id;
    this.password = password;
  }

}
