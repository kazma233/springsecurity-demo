package com.kazma.securitydemo.config.filter;

import com.kazma.securitydemo.constants.JwtConstants;
import com.kazma.securitydemo.entity.user.UserPermissionsDTO;
import com.kazma.securitydemo.entity.user.vo.UserToken;
import com.kazma.securitydemo.service.user.impl.CustomizeUserDetailService;
import com.kazma.securitydemo.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private CustomizeUserDetailService customizeUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = httpServletRequest.getHeader(JwtConstants.JWT_HEADER_NAME);

            UserToken userToken = JwtUtils.validationJwtAndGetSubject(token);

            // will add cache here
            UserDetails userDetails = customizeUserDetailService.loadUserByUsername(userToken.getUsername());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            // set authentication
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            LOGGER.error("校验登陆状态失败：{}", e.getMessage());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
