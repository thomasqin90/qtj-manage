package com.qtj.manageserver.filter;

import cn.hutool.json.JSONUtil;
import com.qtj.manageserver.common.Result;
import com.qtj.manageserver.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    // 白名单
    private static final List<String> whiteList = List.of("/auth/login", "/public/**");

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (matchWhiteList(uri, whiteList)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 提取Header Token
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null || token.trim().isEmpty()) {
            whiteError(response, Result.error(401, "未登录，请携带Token"));
            return;
        }
        if(token.startsWith(TOKEN_PREFIX) && token.length() > 7) {
            token = token.substring(7);
        }
        // 提取jwt中的
        Long userId;
        if(jwtUtil.isTokenExpired(token)){
            //
            whiteError(response, Result.error(401, "Token已过期"));
            return;
        } else {
            userId = jwtUtil.getUserIdFromToken(token);
        }
        // 存入request供Controller直接获取
        request.setAttribute("loginUserId", userId);
        // 放行后续过滤器与接口
        filterChain.doFilter(request, response);
    }

    /**
     * 模糊匹配白名单，支持 /**
     */
    private boolean matchWhiteList(String uri, List<String> whiteList) {
        for (String pattern : whiteList) {
            if (pattern.endsWith("/**")) {
                String prefix = pattern.replace("/**", "");
                if (uri.startsWith(prefix)) {
                    return true;
                }
            } else if (uri.equals(pattern)) {
                return true;
            }
        }
        return false;
    }

    private static void whiteError(HttpServletResponse response, Result<?> result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        // 状态码可选：401 / 200，前后端约定即可
        response.setStatus(401);
        PrintWriter out = response.getWriter();
        out.write(JSONUtil.toJsonStr(result));
        out.flush();
        out.close();
    }
}
