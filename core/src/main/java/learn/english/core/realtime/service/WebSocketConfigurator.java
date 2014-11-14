package learn.english.core.realtime.service;

import learn.english.core.authentication.HTTPHeaderNames;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by yaroslav on 11/14/14.
 */
public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        sec.getUserProperties().put(HTTPHeaderNames.AUTH_TOKEN,request.getHeaders().get(HTTPHeaderNames.AUTH_TOKEN));
    }
}
