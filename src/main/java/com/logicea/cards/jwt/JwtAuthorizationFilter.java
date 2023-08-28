package com.logicea.cards.jwt;


import com.auth0.jwt.JWT;
import com.logicea.cards.entity.User;
import com.logicea.cards.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
   private JwtConfig jwtConfig;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,JwtConfig jwtConfig, UserRepository userRepository) {
        super(authenticationManager);
        this.jwtConfig=jwtConfig;
        this.userRepository=userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        System.out.println("headString "+ jwtConfig.getSecret());
        String header = request.getHeader(jwtConfig.getHeadString());
        System.out.println("headString header "+header);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeadString())
                .replace(jwtConfig.getPrefix(),"");

        if (token != null) {
            // parse the token and validate it
            String userName = getUserName(token);

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
                User user = userRepository.findByEmail(userName).get();
                CustomUserDetails principal = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }

    public String getUserName(String token) {
       return JWT.require(HMAC512(jwtConfig.getSecret().getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}

