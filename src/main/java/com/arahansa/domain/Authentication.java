package com.arahansa.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by jarvis on 2016. 10. 12..
 */
@Slf4j
public class Authentication {

  protected String id;
  protected String password;

  public String getId(){
    return this.id;
  }

  public Authentication() {
  }

  public Authentication(String id){
    this.id = id;
  }



  public boolean matchPassword(String password){
    final String replacedPassword = password.replace("Password", "");
    final String user = getId().replace("Id", "");
    log.debug("id : {} , password : {}", id, password);
    return StringUtils.equalsIgnoreCase(user, replacedPassword);
  }
}
