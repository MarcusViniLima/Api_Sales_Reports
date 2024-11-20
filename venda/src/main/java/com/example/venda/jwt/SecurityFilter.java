package com.example.venda.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.venda.entities.Client;
import com.example.venda.entities.Seller;
import com.example.venda.entities.Supervisor;
import com.example.venda.entities.Users;
import com.example.venda.entities.Enum.AcessLevels;
import com.example.venda.repository.ClientRepository;
import com.example.venda.repository.SellerRepository;
import com.example.venda.repository.SupervisorRepository;
import com.example.venda.repository.UsersRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            try {
                var subject = jwtService.validateToken(token);
                System.out.println("Token validado para o usuário: " + subject);
                UserDetails user = usersRepository.findByEmail(subject).get();
                if (user != null) {
                    System.out.println("Usuário autenticado: " + user.getUsername());
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                System.err.println("Falha ao validar token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            return null;
        }
        return authorizationHeader.replace("Bearer ", "");
    }

    

}
