import com.arahansa.security.AuthService
import lombok.extern.slf4j.Slf4j
import spock.lang.Shared
import spock.lang.Specification

class AuthServiceSpec extends Specification {

    public static final String NO_USER_ID = "noUserId";
    public static final String WRONG_PASSWORD = "wrongPassword";
    public static final String USER_ID = "userId";
    public static final String USER_PASSWORD = "userPassword";

    @Shared
    AuthService authService;

    def setupSpec() {
        println("setupSpec")
        authService = new AuthService();
    }

    def "더하기 테스트"() {
        given:
        def a = 1
        def b = 2

        when:
        def result = a + b

        then:
        result == 3
    }

    def "주어진 아규먼트가 유효하지 않을때 InvalidArgumentException 발생"(){

        expect:
        assert assertIllegalArgExThrown(null, USER_PASSWORD)
        assert assertIllegalArgExThrown("", USER_PASSWORD)
        assert assertIllegalArgExThrown(USER_ID, "")
        assert assertIllegalArgExThrown(USER_ID, null)
    }


    def "주어진 아규먼트가 유효하지 않을때 InvalidArgumentException 발생 2"(){
        expect:
        authService.authenticate(a, b)
    }

    boolean assertIllegalArgExThrown(String id, String password){
        Exception ex;
        try{
            authService.authenticate(id, password);
        }catch(Exception e){
            ex = e;
        }
        ex instanceof IllegalArgumentException
    }




}