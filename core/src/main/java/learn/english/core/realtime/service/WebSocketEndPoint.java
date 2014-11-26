package learn.english.core.realtime.service;

import learn.english.core.authentication.AuthenticationProvider;
import learn.english.core.authentication.HTTPHeaderNames;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by yaroslav on 11/14/14.
 */
//@ServerEndpoint(value = "/update", configurator = WebSocketConfigurator.class)
@ServerEndpoint("/update/{auth_token}")
public class WebSocketEndPoint {
    @Inject
    LiveContext liveContext;
    @Inject
    AuthenticationProvider authenticationProvider;


    @OnOpen
    public void openConnection(Session session, @PathParam("auth_token") String auth_token){
        //String auth_token = (String) conf.getUserProperties().get(HTTPHeaderNames.AUTH_TOKEN);
        String name = authenticationProvider.getUserName(auth_token);
        liveContext.getLiveProcessor(name).registerClient(session);
    }

    @OnClose
    public void closeConnection(Session session, @PathParam("auth_token") String auth_token){
       // String auth_token = (String) conf.getUserProperties().get(HTTPHeaderNames.AUTH_TOKEN);
        String name = authenticationProvider.getUserName(auth_token);
        liveContext.getLiveProcessor(name).removeClient(session);
    }
}
