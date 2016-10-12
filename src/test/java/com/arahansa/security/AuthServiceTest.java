package com.arahansa.security;

import com.arahansa.domain.Authentication;
import com.arahansa.domain.User;
import com.arahansa.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jarvis on 2016. 10. 11..
 */
public class AuthServiceTest {

  public static final String NO_USER_ID = "noUserId";
  public static final String WRONG_PASSWORD = "wrongPassword";
  public static final String USER_ID = "userId";
  public static final String USER_PASSWORD = "userPassword";
  private AuthService authService;
  private UserRepository mockUserRepository;
  /*
  - [ ] 테스트 클래스 만들기
  - [ ] 객체 생성하기 (쉬운)
  - [ ] ID값이 비정상인 경우(쉬운, 정상에서 벗어난)
  - [ ] PW값이 비정상인 경우 (쉬운, 정상에서 벗어난)
  - [ ] User가 존재하지 않는 경우 (정상에서 벗어난)
  - [ ] Id에 해당하는 User가 존재하는데, PW가 일치하지 않는 경우(정상에서 벗어난)
  - [ ] ID와 PW가 일치하는 경우(정상) => 인증정보를 리턴
  */
 
  @Before
  public void setup() throws Exception{
    mockUserRepository = mock(UserRepository.class);
    authService = new AuthService();
    authService.setUserRepository(mockUserRepository);
  }

  @Test
  public void whenUserFoundAndRightPw_returnAuth(){
    givenUserExists(USER_ID, USER_PASSWORD);
    Authentication auth = authService.authenticate(USER_ID, USER_PASSWORD);
    assertThat(auth.getId(), equalTo(USER_ID));
  }

  @Test
  public void givenInvalid_throwIllegalArgEx(){
    assertIllegalArgExThrown(null, USER_PASSWORD);
    assertIllegalArgExThrown("", USER_PASSWORD);
    assertIllegalArgExThrown(USER_ID, "");
    assertIllegalArgExThrown(USER_ID, null);
  }

  private void assertIllegalArgExThrown(String id, String userPassword) {
    assertExceptionThrown(id, userPassword, IllegalArgumentException.class);
  }

  private void assertExceptionThrown(String id, String userPassword, Class<? extends Exception> type) {
    Exception thrownEx = null;
    try {
      authService.authenticate(id, userPassword);
    } catch (Exception e) {
      thrownEx = e;
    }
    assertThat(thrownEx, instanceOf(type));
  }

  @Test
  public void whenUserNotFound_throwNonExistingUserEx(){
    assertExceptionThrown(NO_USER_ID, USER_PASSWORD, NonExistingUserException.class);
    for(int i=0;i<100;i++)
    assertExceptionThrown(NO_USER_ID + i , USER_PASSWORD, NonExistingUserException.class);
  }
  
  @Test
  public void whenUserFoundButWrongPw_throwWrongPasswordEx(){
    givenUserExists(USER_ID, USER_PASSWORD);
    assertExceptionThrown(USER_ID, WRONG_PASSWORD, WrongPasswordException.class);
    verifyUserFound(USER_ID);
  }

  private void verifyUserFound(String id) {
    verify(mockUserRepository).findById(id);
  }

  private void givenUserExists(String id, String password) {
    when(mockUserRepository.findById(id)).thenReturn(new User(id, password));
  }




}
