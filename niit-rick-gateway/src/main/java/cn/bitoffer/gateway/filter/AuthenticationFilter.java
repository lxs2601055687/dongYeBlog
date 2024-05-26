package cn.bitoffer.gateway.filter;

import cn.bitoffer.common.utils.jwt.JwtUtil;
import com.alibaba.nacos.common.utils.MapUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.ServletRequestPathUtils;
import java.util.Map;
import cn.dev33.satoken.stp.StpUtil;
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String tokenValue = StpUtil.getTokenValue(); // 使用 sa-token 获取 token
            if (tokenValue == null || tokenValue.isEmpty()) {
                throw new RuntimeException("missing authorization token");
            }

               //解析token
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("X-User-Id",(String) StpUtil.getLoginIdByToken(tokenValue)).build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
            return chain.filter(modifiedExchange);
        });
    }

    public static class Config {
        // 如果有配置参数，可以在这里添加
    }
}
