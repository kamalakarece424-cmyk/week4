package com.school.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        long start = System.currentTimeMillis();
        log.info("Gateway request: method={} uri={}", req.getMethod(), req.getURI());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long time = System.currentTimeMillis() - start;
            log.info("Gateway response: uri={} time={}ms status={}",
                    req.getURI(),
                    time,
                    exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        return -1; // run before other filters (lowest order = highest priority)
    }
}
