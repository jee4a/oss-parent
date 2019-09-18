package com.jee4a.oss.gateway.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jee4a.oss.framework.CommonConstants;
import com.jee4a.oss.framework.lang.StringUtils;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author tpeng
 * @ProjectName oss-parent
 * @Description: 全局过滤，验证登录token
 * @date 2019/8/1216:50
 */
@Component
public class TokenFilter implements GlobalFilter,Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = null;
        try {
            request =  exchange.getRequest();
            String token = request.getQueryParams().getFirst("token");
            //token 不存在
            if (StringUtils.isEmpty(token)){
                return chain.filter(exchange.mutate().request(this.failRequest(exchange)).build());
            }
            String arg[] = token.split("\\.");
            //token格式不对
            if (arg == null || arg.length != 3){
                return chain.filter(exchange.mutate().request(this.failRequest(exchange)).build());
            }
            DecodedJWT jwt = JWT.decode(token);
            String userId = jwt.getClaim(CommonConstants.JWT_PAYLOAD_CLAIMS).asString() ;
            //userId为空
           /* if (userId == null){
                return chain.filter(exchange.mutate().request(this.failRequest(exchange)).build());
            }*/
            request.getQueryParams().add("userId",userId);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    private ServerHttpRequest failRequest(ServerWebExchange exchange) throws URISyntaxException{
        URI newUri = UriComponentsBuilder.fromUri(new URI("http://localhost:8001/test/fail"))
                .build(true)
                .toUri();
        return  exchange.getRequest().mutate().uri(newUri).build();
    }

    @Override
    public int getOrder() {
        return 1000;
    }
}
