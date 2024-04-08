package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.configuration.JWTUtils;
import com.sawdev.cnss.plainte.dto.PasswordModif;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.repository.UserDao;
import com.sawdev.cnss.plainte.service.MailService;
import com.sawdev.cnss.plainte.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired //(required=true)
    private UserDao userdao;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public void deleteUser(String userName) {
        log.info("Suppression de compte utilisateur : {}", userName);
        User user = userdao.findOneByUsername(userName).get();
        userdao.deleteById(user.getId());
    }

    @Override
    public User registerNewUser(User user) {
        log.info("Creation de compte utilisateur CLIENT : {}", user);
//        Role role = roledao.findByName("CUSTOMER").get();
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
        user.setRole("CUSTOMER");
        user.setPassword(getEncodedPassword(user.getPassword()));

        return userdao.save(user);
    }

    @Override
    public User registerNewEngineer(User engg) {
        log.info("Creation de compte utilisateur ENGINEER : {}", engg);
        engg.setRole("ENGINEER");
        engg.setPassword(getEncodedPassword(engg.getPassword()));

        return userdao.save(engg);
    }

    @Override
    public User registerNewManager(User manager) {
        log.info("Creation de compte utilisateur MANAGER : {}", manager);
        manager.setRole("MANAGER");
        manager.setPassword(getEncodedPassword(manager.getPassword()));

        return userdao.save(manager);
    }

    public String resetPassword(final String to) {
        User optionalUser = userdao.findOneByEmail(to).orElse(null);

        if (optionalUser == null) {
            throw new RuntimeException("Le compte avec l'adresse mail " + to + " n'existe pas");
        }

        if (!optionalUser.isEnabled()) {
            throw new RuntimeException("Le compte avec l'adresse mail " + to + " n'est pas encore validé");
        }

        String defaultPassword = UUID.randomUUID().toString();
        String emailSubject = "Demande de réinitialisation de mot de passe";
        String emailBody = "Veuillez recevoir ci-dessous un mot de passe généré aléatoirement pour acceder à votre compte. <br>Mot de passe généré : " + defaultPassword;

        optionalUser.setPassword(this.getEncodedPassword(defaultPassword));
        userdao.save(optionalUser);
        if (mailService.isEmailValid(to)) {
            mailService.sendEmail(to, emailSubject, emailBody);
        } else {
            throw new RuntimeException("L'email de l'utilisateur n'est pas valide.");
        }
        return "Lien de réinitialisation de mot de passe envoyé";
    }

    public String changeUserPassword(final PasswordModif passwordModif) {
        User optionalUser = userdao.findOneByUsername(jwtUtils.extractUsername(passwordModif.getToken())).orElse(null);
        if (optionalUser == null) {
            throw new RuntimeException("Le token n'est pas valide. Il correspond à aucun utilisateur.");
        }
        if (jwtUtils.isTokenExpired(passwordModif.getToken())) {
            throw new RuntimeException("Le token a expiré.");
        }

        if (!passwordModif.getNewPassword().equals(passwordModif.getConfirmNewPassword())) {
            throw new RuntimeException("Les deux mots de passe ne sont pas identiques.");
        }

        optionalUser.setPassword(this.getEncodedPassword(passwordModif.getNewPassword()));
        userdao.save(optionalUser);
        //VIDER LA CACHE ET EXPIRE DE FORCE LE TOKEN FOURNI EN PARAMS
        return "Le mot de passe a été changé";
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Liste des comptes utilisateur");
        return userdao.findAll();
    }

    @Override
    public void initUsers() {
        if (userdao.count() < 2) {
            log.info("Creation de comptes utilisateur systeme");
            User adminUser = new User();
            adminUser.setNom("admin");
            adminUser.setPrenom("admin123");
            adminUser.setUsername("admin");
            adminUser.setPassword(getEncodedPassword("pwd@123"));
            adminUser.setRole("ADMIN");
            userdao.save(adminUser);

            User managerUser = new User();
            managerUser.setNom("ABC Manager1");
            managerUser.setPrenom("manager1");
            managerUser.setUsername("manager");
            managerUser.setPassword(getEncodedPassword("manager1@pwd"));;

            managerUser.setRole("MANAGER");
            userdao.save(managerUser);
        }
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String forAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String forManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String forCustomer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String forEngineer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

/*
 * adminUser.setUserpassword("pwd@123");
 * This has to store encrypted password*/
