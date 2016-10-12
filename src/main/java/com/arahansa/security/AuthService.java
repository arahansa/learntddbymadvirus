package com.arahansa.security;

import com.arahansa.domain.Authentication;
import com.arahansa.domain.User;
import com.arahansa.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by jarvis on 2016. 10. 12..
 */
@Slf4j
public class AuthService {

  private UserRepository userRepository;

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Authentication authenticate(String id, String password) {
    log.debug("authenticating...");
    assertIdAndPw(id, password);
    User user = findUserOrThrowEx(id);
    throwExIfPasswordWrong(password, user);
    return createAuthentication(user);
  }

  private void assertIdAndPw(String id, String password) {
    if (StringUtils.isBlank(id)) throw new IllegalArgumentException();
    if (StringUtils.isBlank(password)) throw new IllegalArgumentException();
  }

  private User findUserOrThrowEx(String id) {
    User user = findUserById(id);
    if (user == null)
      throw new NonExistingUserException();
    return user;
  }

  private void throwExIfPasswordWrong(String password, User user) {
    if (!user.matchPassword(password))
      throw new WrongPasswordException();
  }

  private Authentication createAuthentication(User user) {
    return new Authentication(user.getId());
  }

  private User findUserById(String id) {
    return userRepository.findById(id);
  }

}
