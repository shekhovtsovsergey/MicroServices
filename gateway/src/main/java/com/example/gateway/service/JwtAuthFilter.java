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
        System.out.println("point 0 - went into the method apply");
        return (exchange, chain) -> {
            System.out.println("point 1 - started executing lambda");
            ServerHttpRequest request = exchange.getRequest();

            if (request.getHeaders().containsKey("username")) {
                System.out.println("point 2 - Invalid header username");
                return this.onError(exchange, "Invalid header username", HttpStatus.BAD_REQUEST);
            }
            if (!request.getHeaders().containsKey("Authorization")) {
                System.out.println("point 3 - there is no token in the header");
                return this.onError(exchange, "there is no token in the header", HttpStatus.BAD_REQUEST);
            }
            if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
                System.out.println("point 4 - token doesn't start with Bearer");
                return this.onError(exchange, "token doesn't start with Bearer", HttpStatus.BAD_REQUEST);
            }
            final String token = getAuthHeader(request);
            if (jwtUtil.isInvalid(token)) {
               System.out.println("point 4 - token has expired");
               return this.onError(exchange, "token has expired", HttpStatus.UNAUTHORIZED);
            }
            System.out.println("point 5 - all checks passed");
            System.out.println("header modification location" + exchange + token);
            populateRequestWithHeaders(exchange, token);
            System.out.println("point 6 - header is ok");
            return chain.filter(exchange);
        };
    }
    @Override
    public GatewayFilter apply(String routeId, Config config) {
        System.out.println("point 7 - other apply");
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


    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        System.out.println("point 8 - method populateRequestWithHeaders");
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())
//                .header("role", String.valueOf(claims.get("role")))
                .build();
        System.out.println("point 9 - header: " + exchange.getRequest().getHeaders());
    }
}
