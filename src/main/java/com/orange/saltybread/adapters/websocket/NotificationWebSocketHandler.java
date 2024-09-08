package com.orange.saltybread.adapters.websocket;

import com.orange.saltybread.adapters.service.JWTService;
import java.io.IOException;
import java.net.URI;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class NotificationWebSocketHandler extends TextWebSocketHandler {

  private static final ConcurrentHashMap<UUID, WebSocketSession> CLIENTS = new ConcurrentHashMap<UUID, WebSocketSession>();

  private final JWTService jwtService;

  @Autowired
  public NotificationWebSocketHandler(JWTService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    URI uri = session.getUri();

    if (uri != null) {
      // URI에서 쿼리 파라미터를 추출합니다.
      String query = uri.getQuery();
      System.out.println("Query: " + query);

      // 쿼리 파라미터를 Map 형태로 변환합니다.
      Map<String, List<String>> parameters = splitQuery(uri);
      String token = parameters.get("token").get(0);
      UUID userId = jwtService.getUserId(token);

      CLIENTS.put(userId, session);
    }

  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    CLIENTS.remove(session.getId());
  }

  // 쿼리 파라미터를 파싱하는 헬퍼 메소드
  private Map<String, List<String>> splitQuery(URI uri) throws Exception {
    return Arrays.stream(uri.getQuery().split("&"))
        .map(this::splitQueryParameter)
        .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey,
            LinkedHashMap::new,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
  }

  private AbstractMap.SimpleImmutableEntry<String, String> splitQueryParameter(String it) {
    final int idx = it.indexOf("=");
    final String key = idx > 0 ? it.substring(0, idx) : it;
    final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;
    return new AbstractMap.SimpleImmutableEntry<>(key, value);
  }

  public void sendMessage(UUID userId, String message) throws IOException {
    if (CLIENTS.containsKey(userId)) {
      WebSocketSession session = CLIENTS.get(userId);
      session.sendMessage(new TextMessage(message));
    }
  }
}
