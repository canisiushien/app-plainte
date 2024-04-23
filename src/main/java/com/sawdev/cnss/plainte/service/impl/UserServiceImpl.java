package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.configuration.JWTUtils;
import com.sawdev.cnss.plainte.dto.PasswordModif;
import com.sawdev.cnss.plainte.dto.UserDTO;
import com.sawdev.cnss.plainte.entity.Privilege;
import com.sawdev.cnss.plainte.entity.Profile;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.exception.CustomException;
import com.sawdev.cnss.plainte.mapper.UserMapper;
import com.sawdev.cnss.plainte.repository.PrivilegeDao;
import com.sawdev.cnss.plainte.repository.ProfileDao;
import com.sawdev.cnss.plainte.repository.UserDao;
import com.sawdev.cnss.plainte.service.MailService;
import com.sawdev.cnss.plainte.service.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired //(required=true)
    private UserDao userdao;
    
    @Autowired
    private ProfileDao profileDao;
    
    @Autowired
    private PrivilegeDao privilegeDao;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JWTUtils jwtUtils;
    
    @Autowired
    private UserMapper mapper;
    
    @Transactional
    public void saveInitAuthorities() throws FileNotFoundException, IOException {
        if (privilegeDao.findAll().isEmpty()) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("data/authorities.txt").getFile());
            try (BufferedReader lineReader = new BufferedReader(new FileReader(file))) {
                String lineText;
                int count = 0;
                List<Privilege> roles = new ArrayList<>();
                while ((lineText = lineReader.readLine()) != null) {
                    String[] data = lineText.split(";");
                    if (count != 0) {
                        Privilege role = new Privilege();
                        role.setId(Long.valueOf(data[0]));
                        role.setCode(data[1]);
                        role.setLibelle(data[2]);
                        roles.add(role);
                    }
                    count++;
                }
                privilegeDao.saveAll(roles);
                log.info("Enregistrement des privilèges...ok");
            }
        }
    }
    
    @Transactional
    public void saveInitProfil() {
        if (profileDao.findAll().isEmpty()) {
            Set<Privilege> privileges = privilegeDao.findAll().stream().collect(Collectors.toSet());
            privileges.addAll(privileges);
            Profile profil = new Profile();
            profil.setLibelle("Administrateur");
            profil.setId(1L);
            profil.setCode("ADMIN");
            profil.setNativeProfile(true);
            profil.setPrivileges(privileges);
            profileDao.save(profil);
            log.info("Enregistrement de profile system...ok");
        }
    }
    
    @Transactional
    public void saveInitUser() {
        if (userdao.findAll().isEmpty()) {
            Profile profile = profileDao.findByCode("ADMIN").orElse(null);
            User user = new User();
            user.setId(1L);
            user.setUsername("admin");
            user.setProfile(profile);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setNom("admin");
            user.setPrenom("admin");
            user.setEmail("canisiushien@gmail.com");
            userdao.save(user);
            System.out.println("Utilisateur " + user.getNom() + "bien créé avec le profile " + profile.toString());
        }
    }
    
    @Override
    public void deleteUser(String userName) {
        log.info("Suppression de compte utilisateur : {}", userName);
        userdao.findOneByUsername(userName).ifPresent(user -> {
            userdao.deleteById(user.getId());
            this.clearUserCaches(user.getUsername());
            log.debug("Deleted user: {}", user);
        });
    }
    
    @Override
    public UserDTO registerNewUser(UserDTO customer) {
        log.info("Creation de compte utilisateur CLIENT : {}", customer);
        userdao.findOneByUsername(customer.getUsername().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNotEnabledUser(existingUser);
            if (!removed) {
                throw new CustomException("Ce nom d'utilisateur est déjà utilisé.");
            }
        });
        
        Profile profile = profileDao.findOneWithPrivilegesByCodeIgnoreCase("CUSTOMER").orElseThrow(() -> new CustomException("Le profile 'CUSTOMER' n'existe pas. Veuillez le parametrer SVP."));
        User response = new User();
        response.setProfile(profile);
        response.setUsername(customer.getUsername());
        response.setPassword(getEncodedPassword(customer.getPassword()));
        response.setNom(customer.getNom());
        response.setPrenom(customer.getPrenom());
        response.setEmail(customer.getTelephone());
        response.setTelephone(customer.getTelephone());
        response = userdao.save(response);
        this.clearUserCaches(response.getUsername());
        return mapper.toDto(response);
    }
    
    @Override
    public UserDTO registerNewInterneUser(UserDTO userDTO) {
        log.info("Creation de compte utilisateur INTERNE : {}", userDTO);
        userdao.findOneByUsername(userDTO.getUsername().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNotEnabledUser(existingUser);
            if (!removed) {
                throw new CustomException("Ce nom d'utilisateur est déjà utilisé.");
            }
        });
        
        Profile profile = profileDao.findOneWithPrivilegesByCodeIgnoreCase(userDTO.getProfile().getCode()).orElseThrow(() -> new CustomException("Le profile '" + userDTO.getProfile().getCode() + "' n'existe pas."));
        User response = new User();
        String defaultPassword = UUID.randomUUID().toString();
        response.setProfile(profile);
        response.setUsername(userDTO.getUsername());
        response.setPassword(this.getEncodedPassword(defaultPassword));
        response.setNom(userDTO.getNom());
        response.setPrenom(userDTO.getPrenom());
        response.setEmail(userDTO.getTelephone());
        response.setTelephone(userDTO.getTelephone());
        response = userdao.save(response);
        this.clearUserCaches(response.getUsername());
        
        String emailSubject = "Votre nouveau compte ";
        String emailBody = "Veuillez recevoir ci-dessous un mot de passe généré aléatoirement pour acceder à votre compte. <br>Mot de passe généré : " + defaultPassword;
        
        if (mailService.isEmailValid(response.getEmail())) {
            mailService.sendEmail(response.getEmail(), emailSubject, emailBody);
        } else {
            throw new CustomException("L'email de l'utilisateur n'est pas valide. Veuillez renvoyer son mot de passe SVP.");
        }
        return mapper.toDto(response);
    }
    
    @Transactional
    @Override
    public String resendPassword(String email) {
        User user = userdao.findOneByEmail(email).orElseThrow(() -> new CustomException("L'email ne correspond à aucun utilisateur."));
        String newPassword = UUID.randomUUID().toString();
        user.setPassword(this.getEncodedPassword(newPassword));
        user = userdao.save(user);
        this.clearUserCaches(user.getUsername());
        
        String emailSubject = "Votre mot de passe ";
        String emailBody = "Veuillez recevoir ci-dessous un mot de passe généré aléatoirement pour acceder à votre compte. <br>Mot de passe généré : " + newPassword;
        
        if (mailService.isEmailValid(user.getEmail())) {
            mailService.sendEmail(user.getEmail(), emailSubject, emailBody);
        } else {
            throw new CustomException("L'email de l'utilisateur n'est pas valide. Veuillez renvoyer son mot de passe SVP.");
        }
        return "Un mot de passe a été renvoyé";
    }

//    @Override
//    public User registerNewEngineer(User engg) {
//        log.info("Creation de compte utilisateur ENGINEER : {}", engg);
//        engg.setPassword(getEncodedPassword(engg.getPassword()));
//        
//        return userdao.save(engg);
//    }
//    
//    @Override
//    public User registerNewManager(User manager) {
//        log.info("Creation de compte utilisateur MANAGER : {}", manager);
////        manager.setRole("MANAGER");
//        manager.setPassword(getEncodedPassword(manager.getPassword()));
//        
//        return userdao.save(manager);
//    }
    @Override
    public String resetPassword(final String to) {
        User optionalUser = userdao.findOneByEmail(to).orElse(null);
        
        if (optionalUser == null) {
            throw new CustomException("Le compte avec l'adresse mail " + to + " n'existe pas");
        }
        
        if (!optionalUser.isEnabled()) {
            throw new CustomException("Le compte avec l'adresse mail " + to + " n'est pas encore validé");
        }
        
        String defaultPassword = UUID.randomUUID().toString();
        String emailSubject = "Demande de réinitialisation de mot de passe";
        String emailBody = "Veuillez recevoir ci-dessous un mot de passe généré aléatoirement pour acceder à votre compte. <br>Mot de passe généré : " + defaultPassword;
        
        optionalUser.setPassword(this.getEncodedPassword(defaultPassword));
        userdao.save(optionalUser);
        if (mailService.isEmailValid(to)) {
            mailService.sendEmail(to, emailSubject, emailBody);
        } else {
            throw new CustomException("L'email de l'utilisateur n'est pas valide.");
        }
        return "Lien de réinitialisation de mot de passe envoyé";
    }
    
    @Override
    public String changeUserPassword(final PasswordModif passwordModif) {
        User optionalUser = userdao.findOneByUsername(jwtUtils.extractUsername(passwordModif.getToken())).orElse(null);
        if (optionalUser == null) {
            throw new CustomException("Le token n'est pas valide. Il correspond à aucun utilisateur.");
        }
        if (jwtUtils.isTokenExpired(passwordModif.getToken())) {
            throw new CustomException("Le token a expiré.");
        }
        
        if (!passwordModif.getNewPassword().equals(passwordModif.getConfirmNewPassword())) {
            throw new CustomException("Les deux mots de passe ne sont pas identiques.");
        }
        
        optionalUser.setPassword(this.getEncodedPassword(passwordModif.getNewPassword()));
        optionalUser = userdao.save(optionalUser);
        this.clearUserCaches(optionalUser.getUsername());
        return "Le mot de passe a été changé";
    }
    
    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Liste des comptes utilisateur");
        return userdao.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
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

    /**
     * CRON : tout utilisateur desactivé (enabled=false) est automatique
     * supprimé après 5 mois.
     *
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotEnabledUsers() {
        userdao
                .findAllByEnabledIsFalseAndCreatedDateBefore(Instant.now().minus(5, ChronoUnit.MONTHS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getUsername());
                    userdao.delete(user);
                    this.clearUserCaches(user.getUsername());
                });
    }
    
    private boolean removeNotEnabledUser(User existingUser) {
        if (existingUser.isEnabled()) {
            return false;
        }
        userdao.delete(existingUser);
        userdao.flush();
        this.clearUserCaches(existingUser.getUsername());
        return true;
    }

    /**
     * Cette méthode va vider le cache pour l'utilisateur spécifié
     *
     * @param username
     */
    @CacheEvict(value = "userCache", key = "#username")
    public void clearUserCaches(String username) {
    }
}
