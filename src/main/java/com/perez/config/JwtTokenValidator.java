package com.perez.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtConstant.JWT_HEADER); //inside this header we will get the jwt

		if(jwt!=null) {
			jwt=jwt.substring(7);
			
			
			try {
				
				SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				
				Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String email=String.valueOf(claims.get("email"));
				
				String authorities=String.valueOf(claims.get("authorities"));
				
				System.out.println("authorities -------- "+authorities);


				//role customer, role admin
				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);  //we authorize all roles
				//List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(authorities);
				Authentication athentication=new UsernamePasswordAuthenticationToken(email,null, auths);
				
				SecurityContextHolder.getContext().setAuthentication(athentication);

			} catch (Exception e) {
				throw new BadCredentialsException("token invalido...");
			}
		}
		filterChain.doFilter(request, response);
		
	}


}
//eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3NDg1NzU2ODgsImV4cCI6MTc0ODY2MjA4OCwiZW1haWwiOiJoZWN0b3JAZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOiJST0xFX1JFU1RBVVJBTlRfT1dORVIifQ.9tFcethjiV3Y8f8CH-Bn_xuqvFakTxdgrgPbUbpQpEBDSexc4QyANuCvi6BuW1Up