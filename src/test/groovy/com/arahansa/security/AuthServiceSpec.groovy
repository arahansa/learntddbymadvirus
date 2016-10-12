import com.arahansa.domain.User
import com.arahansa.domain.UserRepository
import com.arahansa.security.AuthService
import com.arahansa.security.NonExistingUserException
import com.arahansa.security.WrongPasswordException
import org.mockito.InjectMocks
import spock.lang.Shared
import spock.lang.Specification

import static org.mockito.Mockito.atLeastOnce
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class AuthServiceSpec extends Specification {

    public static final String NO_USER_ID = "noUserId";
    public static final String WRONG_PASSWORD = "wrongPassword";
    public static final String USER_ID = "userId";
    public static final String USER_PASSWORD = "userPassword";

    @Shared
    @InjectMocks
    AuthService authService;
    @Shared
    UserRepository mockUserRepository;

    def setupSpec() {
        println("setupSpec")
    }



    def setup(){
        mockUserRepository = Mock();
        authService = new AuthService();
        authService.setUserRepository(mockUserRepository);
    }

    def "00 기초. 스폭을 알아보는 더하기 테스트"() {
        given:
        def a = 1
        def b = 2

        when:
        def result = a + b

        then:
        result == 3
    }

    def "01 검증. 주어진 아규먼트가 유효하지 않을때 InvalidArgumentException 발생"(){
        expect:
        assert assertIllegalArgExThrown(null, USER_PASSWORD)
        assert assertIllegalArgExThrown("", USER_PASSWORD)
        assert assertIllegalArgExThrown(USER_ID, "")
        assert assertIllegalArgExThrown(USER_ID, null)
    }

    boolean assertExceptionThrown(String id, String password, Class<? extends Exception> exType){
        Exception ex;
        try{
            authService.authenticate(id, password);
        }catch(Exception e){
            ex = e;
        }
        println("exType : "+exType)
        ex.class.isAssignableFrom(exType)
    }

    boolean assertIllegalArgExThrown(String id, String password){
        assertExceptionThrown(id, password, IllegalArgumentException)
    }

    def "02 검증. 주어진 아규먼트가 유효하지 않을때 InvalidArgumentException 발생. where 문 이용해봄. "(){
        when:
        authService.authenticate(a, b)

        then:
        thrown(expectedException)

        where:
        a|b|expectedException
        null || USER_PASSWORD || IllegalArgumentException
        "" || USER_PASSWORD || IllegalArgumentException
        USER_ID || "" || IllegalArgumentException
        USER_ID || null || IllegalArgumentException
    }

    def "03. 낫파운드. 유저가 발견되지 않으면 NonExistingUserEx를 리턴한다. whenUserNotFound_throwNonExistingUserEx"(){
        when:
        authService.authenticate(NO_USER_ID, USER_PASSWORD)
        then:
        thrown NonExistingUserException

        for(int i=0;i<100;i++)
            assert assertExceptionThrown(NO_USER_ID + i , USER_PASSWORD, NonExistingUserException)
    }

    def "04. 비밀번호 체크. 유저가 발견되고 비밀번호가 다르면 whenUserFoundButWrongPw_throwWrongPasswordEx"(){
        given:
        mockUserRepository.findById(USER_ID) >> new User(USER_ID, USER_PASSWORD)
        when:
        authService.authenticate(USER_ID, WRONG_PASSWORD);
        then:
        thrown WrongPasswordException
        verify(mockUserRepository, atLeastOnce()).findById(USER_ID)

    }

    def "04 다시"(){
        given:
        mockUserRepository.findById(USER_ID) >> new User(USER_ID, USER_PASSWORD)
        when:
        authService.authenticate(USER_ID, WRONG_PASSWORD);
        then:
        thrown WrongPasswordException

    }


    void givenUserExists(String id, String password) {
        when(mockUserRepository.findById(id)).thenReturn(new User(id, password))
    }

    void verifyUserFound(String id) {
        verify(mockUserRepository).findById(id)
    }






}