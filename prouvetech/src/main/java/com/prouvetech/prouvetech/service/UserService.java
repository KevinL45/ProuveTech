package com.prouvetech.prouvetech.service;

import com.prouvetech.prouvetech.config.JwtTokenProvider;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.repository.UserRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

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

    public ResponseMessage authenticateUser(String email, String password) {

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return new ResponseMessage(false, "Email ou mot de passe ne doivent pas être vides.");
        }

        User user = userRepository.findByMail(email);

        if (user == null) {
            return new ResponseMessage(false, "Identifiants invalides");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseMessage(false, "Identifiants invalides");
        }

        String token = jwtTokenProvider.createToken(user.getMail());
        return new ResponseMessage(true, token);
    }

    public ResponseMessage createUser(User user) {

        User emailExist = userRepository.findByMail(user.getMail());

        if (emailExist != null)
            return new ResponseMessage(false, "Vous avez déja un compte");

        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
        return new ResponseMessage(true, "Vous êtes inscrit");
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
