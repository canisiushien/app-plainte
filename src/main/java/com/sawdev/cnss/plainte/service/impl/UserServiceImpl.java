package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.repository.UserDao;
import com.sawdev.cnss.plainte.service.UserService;
import java.util.List;
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
    private PasswordEncoder passwordEncoder;

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

    @Override
    public void initUsers() {
        log.info("Creation de comptes utilisateur systeme");
        if (userdao.count() < 2) {
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

    @Override
    public List<User> getAllUsers() {
        log.info("Liste des comptes utilisateur");
        return userdao.findAll();
    }
}

/*
 * adminUser.setUserpassword("pwd@123");
 * This has to store encrypted password*/
