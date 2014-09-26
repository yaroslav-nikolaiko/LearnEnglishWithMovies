package learn.english.core.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.login.LoginException;
import java.io.Serializable;

/**
 * Created by yaroslav on 9/26/14.
 */
public interface AuthenticationProvider extends Serializable {
    /**
     *
     * @param username
     * @param password
     * @return Authentication Token
     * @throws LoginException
     */
    String login(String username, String password)throws LoginException;
    boolean isAuthTokenValid( String authToken );
    String getUserName(String authToken);
    void logout( String authToken );
}
