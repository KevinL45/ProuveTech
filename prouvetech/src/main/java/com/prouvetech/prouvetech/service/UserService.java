package com.prouvetech.prouvetech.service;

import com.prouvetech.prouvetech.config.JwtTokenProvider;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public String authenticateUser(String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return "Email et mot de passe ne doivent pas être vides.";
        }

        User user = userRepository.findByMail(email);

        if (user == null) {
            return "Utilisateur introuvable avec cet email.";
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Mot de passe incorrect.";
        }

        String token = jwtTokenProvider.createToken(user.getMail());
        return token;
    }

    public String createUser(User user) {

        User emailExist = userRepository.findByMail(user.getMail());

        if (emailExist != null)
            return "Un utilisateur avec cet email existe déjà.";

        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
        return "Vous êtes inscrit";
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Aucun utilisateur"));
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail);
        return user;
    }

}
