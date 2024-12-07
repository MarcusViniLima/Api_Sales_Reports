package com.example.venda.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.venda.entities.Client;
import com.example.venda.entities.Seller;
import com.example.venda.entities.Supervisor;
import com.example.venda.entities.Users;
import com.example.venda.entities.Enum.AcessLevels;
import com.example.venda.repository.ClientRepository;
import com.example.venda.repository.SellerRepository;
import com.example.venda.repository.SupervisorRepository;
import com.example.venda.repository.UsersRepository;

@Service
public class CostumerUserDetailsService implements UserDetailsService{

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    SupervisorRepository supervisorRepository;
    @Autowired
    UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(username).orElse(null);
        if (client != null) {
            System.out.println("CLiente encontrado");
            return createUsers(client.getEmail(), client.getPassword(), AcessLevels.ROLE_CLIENT);
        }

        Seller seller = sellerRepository.findByEmail(username).orElse(null);
        if (seller != null) {
            System.out.println("Vendedor encontrado");
            return createUsers(seller.getEmail(), seller.getPassword(), AcessLevels.ROLE_SELLER);
        }

        Supervisor supervisor = supervisorRepository.findByEmail(username).orElse(null);
        if (supervisor != null) {
            System.out.println("Supervisor encontrado");
            return createUsers(supervisor.getEmail(), supervisor.getPassword(), AcessLevels.ROLE_SUPERVISOR);
        }

        Users user = usersRepository.findByEmail(username).orElse(null);
        if (user != null) {
            System.out.println("User encontrado");
            return createUsers(user.getEmail(), user.getPassword(), user.getAcessLevels());
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }

    private Users createUsers(String email, String password, AcessLevels role) {
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        user.setAcessLevels(role);
        return user;
    }

}
