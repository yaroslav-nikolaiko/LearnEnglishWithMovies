package learn.english.core.authentication.impl;

import com.google.common.cache.*;
import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.SessionExpired;
import learn.english.core.realtime.service.LiveContext;
import learn.english.core.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 9/26/14.
 */
@ApplicationScoped
public class AuthenticationProviderImpl implements AuthenticationProvider{
    private static final int CONCURRENCY_LEVEL = 4;
    private static final long MAX_SIZE = 10000000;
    private static final long EXPIRE_TIME = 5; //in hours
    @EJB
    UserService userService;
    // An authentication token storage which stores <auth_token, username>. Use guava Cache, to perform  expireAfterAccess
    Cache<String, String> authorizationTokensStorage;
    @Inject @SessionExpired
    Event<String> sessionExpiredEvent;

    @PostConstruct
    void init(){
        authorizationTokensStorage = CacheBuilder.newBuilder()
                .concurrencyLevel(CONCURRENCY_LEVEL)
                .maximumSize(MAX_SIZE)
                .expireAfterAccess(EXPIRE_TIME, TimeUnit.HOURS).removalListener(notification -> {
                    Object username = notification.getValue();
                    if(username!=null)
                        sessionExpiredEvent.fire(username.toString());
                })
                .build();
    }

    @Override
    public String login(String username, String password) throws LoginException {
        if(userService.nameExist(username)) {
            String passwordMatch = userService.getPassword(username);
            if (passwordMatch.equals(password)) {
                if (authorizationTokensStorage.asMap().containsValue(username)) {
                    for (Map.Entry<String, String> entry : authorizationTokensStorage.asMap().entrySet())
                        if(entry.getValue().equals(username))
                            return entry.getKey();
                } else {
                    String authToken = UUID.randomUUID().toString();
                    authorizationTokensStorage.put(authToken, username);
                    return authToken;
                }

            }
        }
        throw new LoginException( "Don't Come Here Again!" );
    }

    @Override
    public boolean isAuthTokenValid(String authToken) {
        return authorizationTokensStorage.asMap().containsKey(authToken);
    }

    @Override
    public String getUserName(String authToken) {
        return authorizationTokensStorage.asMap().get(authToken);
    }

    @Override
    public void logout(String authToken) {
        authorizationTokensStorage.asMap().remove(authToken);
    }
}
