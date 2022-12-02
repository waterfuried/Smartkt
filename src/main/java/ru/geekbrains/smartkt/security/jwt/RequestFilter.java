package ru.geekbrains.smartkt.security.jwt;

import static ru.geekbrains.smartkt.prefs.Prefs.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.*;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.*;
import java.util.stream.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestFilter extends OncePerRequestFilter {
    private final TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException
    {
        final String bearer = "Bearer ";
        // получить заголовок авторизации из запроса сервису
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith(bearer)) {
            jwt = authHeader.substring(bearer.length());
            try { username = tokenUtil.getUsernameFromToken(jwt); }
            catch (ExpiredJwtException ex) { log.debug(ERR_TOKEN_EXPIRED); }
        }

        // добавить авторизованного пользователя в конекст
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            SecurityContextHolder
                .getContext()
                .setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            tokenUtil
                                    .getRoles(jwt)
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())));
        // вставить и применить этот фильтр в цепь фильтров и вернуть ей "управление",
        // пропуская авторизованного пользователя к указанному в адресной строке месту назначения
        filterChain.doFilter(request, response);
    }
}