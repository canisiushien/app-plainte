package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.dto.PasswordModif;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.repository.UserDao;
import com.sawdev.cnss.plainte.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private UserDao userDao;

    @PostMapping({"/public/registerNewCustomer"})
    public User RegisterNewUser(@RequestBody User customer) {
        return userservice.registerNewUser(customer);
    }

    //for admin to register new engineer
    //@PreAuthorize("hasRole('Admin')")
    @PostMapping({"/admin/registerNewEngineer"})
    public User RegisterNewEngineer(@RequestBody User engg) {
        return userservice.registerNewEngineer(engg);
    }

    //for admin to register new manager
    @PostMapping({"/admin/registerNewManager"})
    public User RegisterNewManager(@RequestBody User manager) {
        return userservice.registerNewManager(manager);
    }

    /**
     * Reinitialisation de password oublié
     *
     * @param to
     * @param request
     * @return
     */
    @PostMapping("/public/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("to") String to) {
        return new ResponseEntity<>(userservice.resetPassword(to), HttpStatus.CREATED);
    }

    /**
     * Changer mot de passe
     *
     * @param passwordModif
     * @return
     */
    @PostMapping("/customer/change-user-password")
    public ResponseEntity<String> changeUserPassword(final @RequestBody PasswordModif passwordModif) {
        return new ResponseEntity<>(userservice.changeUserPassword(passwordModif), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('Admin')")
    @GetMapping("/admin/allUser")
    public List<User> getAllUsers() {
        return (List<User>) userDao.findAll();
    }

    // @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/admin/deleteUser/{userName}")
    public void deleteUser(@PathVariable String userName) {
        userservice.deleteUser(userName);
    }

    @GetMapping({"/forAdmin"})
    //@PreAuthorize("hasRole('Admin')") //this will give access only to users having role of Admin
    public String forAdmin() {
        return "This URL is only accessible to admin";
    }

    @GetMapping({"/forManager"})
    @PreAuthorize("hasRole('Manager')")
    public String forManager() {
        return "This URL is only accessible to Manager";
    }

    @GetMapping({"/forCustomer"})
    @PreAuthorize("hasRole('Customer')")
    public String forCustomer() {
        return "This URL is only accessible to Customer";
    }

    @GetMapping({"/forEngineer"})
    @PreAuthorize("hasRole('Engineer')")
    public String forEngineer() {
        return "This URL is only accessible to Engineer";
    }
}
