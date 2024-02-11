package com.example.gateway.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> implements javax.servlet.Filter {
    @Autowired
    private JwtUtil jwtUtil;
    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(String routeId, Consumer<Config> consumer) {
        return super.apply(routeId, consumer);
    }
    @Override
    public GatewayFilter apply(Consumer<Config> consumer) {
        return super.apply(consumer);
    }
    @Override
    public GatewayFilter apply(Config config) {
        System.out.println("точка 1");
        return (exchange, chain) -> {
            System.out.println("точка 2");
            ServerHttpRequest request = exchange.getRequest();
            if (request.getHeaders().containsKey("username")) {
                System.out.println("точка 3");
                return this.onError(exchange, "Invalid header username", HttpStatus.BAD_REQUEST);
            }
            if (isAuthMissing(request)) {
                final String token = getAuthHeader(request);
                if (jwtUtil.isInvalid(token)) {
                    System.out.println("точка 4");
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                System.out.println("точка 5");
                System.out.println("место модификации заголовка" + exchange + token);
                populateRequestWithHeaders(exchange, token);
            }
            System.out.println("точка 6");
            return chain.filter(exchange);
        };
    }
    @Override
    public GatewayFilter apply(String routeId, Config config) {
        System.out.println("точка 7");
        return super.apply(routeId, config);
    }
    @Override
    public String name() {
        return super.name();
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
    @Override
    public ShortcutType shortcutType() {
        return super.shortcutType();
    }
    @Override
    public List<String> shortcutFieldOrder() {
        return super.shortcutFieldOrder();
    }
    @Override
    public String shortcutFieldPrefix() {
        return super.shortcutFieldPrefix();
    }
    public static class Config {
    }
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }
    private boolean isAuthMissing(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }
    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        System.out.println("точка 8");
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
//                .header("role", String.valueOf(claims.get("role")))
                .build();
        System.out.println("контроль заголовка" + exchange.getRequest().getHeaders());
    }
}
