package com.prouvetech.prouvetech.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String firstname, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("...");
            helper.setTo(to);
            helper.setSubject("Bienvenue sur la bêta fermée de Prouve Tech !");

            String html = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 32px; background-color: #f9f9f9; border-radius: 8px;">
                        <h1 style="color: #1a1a1a;">Bienvenue sur Prouve Tech, %s !</h1>
                        <p style="color: #444; font-size: 16px;">
                            Tu fais partie des premiers testeurs de la bêta de <strong>Prouve Tech</strong>.<br>
                            Merci de nous rejoindre et d'aider à améliorer la plateforme !
                        </p>

                        <div style="background-color: #ffffff; border: 1px solid #e0e0e0; border-radius: 6px; padding: 20px; margin: 24px 0;">
                            <p style="margin: 0 0 8px; color: #888; font-size: 13px;">TES IDENTIFIANTS DE CONNEXION</p>
                            <p style="margin: 4px 0; font-size: 15px;"><strong>Email :</strong> %s</p>
                            <p style="margin: 4px 0; font-size: 15px;"><strong>Mot de passe :</strong> %s</p>
                        </div>

                        <p style="color: #444; font-size: 15px;">
                            Tu as des retours, des bugs à signaler ou des idées à partager ?<br>
                            Rejoins le serveur Discord pour échanger. :
                        </p>

                        <div style="text-align: center; margin: 24px 0;">
                            <a href="https://discord.gg/NPqFRaGV9p"
                               style="background-color: #5865F2; color: #ffffff; text-decoration: none; padding: 12px 28px; border-radius: 6px; font-size: 15px; font-weight: bold;">
                                Rejoindre le Discord
                            </a>
                        </div>

                        <p style="color: #444; font-size: 15px; text-align: center;">
                            Prêt à explorer la plateforme ?
                        </p>

                        <div style="text-align: center; margin: 16px 0;">
                            <a href="https://www.prouvetech.fr/"
                               style="background-color: #1a1a1a; color: #ffffff; text-decoration: none; padding: 12px 28px; border-radius: 6px; font-size: 15px; font-weight: bold;">
                                Accéder à Prouve Tech
                            </a>
                        </div>

                        <p style="color: #888; font-size: 13px; margin-top: 32px;">
                            Cet email a été envoyé automatiquement, merci de ne pas y répondre.<br>
                            © Prouve Tech
                        </p>
                    </div>
                    """
                    .formatted(firstname, to, password);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'email de bienvenue : " + e.getMessage());
        }
    }
}
