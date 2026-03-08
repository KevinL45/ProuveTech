package com.prouvetech.prouvetech.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @jakarta.annotation.PostConstruct
    public void init() {
        // Charge le fichier .env
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

        // Affiche les valeurs des propriétés
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

    }
}
