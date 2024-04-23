package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.dto.PasswordModif;
import com.sawdev.cnss.plainte.dto.UserDTO;
import com.sawdev.cnss.plainte.service.UserService;
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

    /**
     * un usager simple créer son propre compte avec son password directement
     * associé. On lui associera en arriere plan le profile CUSTOMER qui sera
     * parametré
     *
     * @param customer
     * @return
     */
    @PostMapping({"/public/registerNewCustomer"})
    public ResponseEntity<?> registerNewUser(@RequestBody UserDTO customer) {
        return new ResponseEntity<>(userservice.registerNewUser(customer), HttpStatus.CREATED);
    }

    /**
     * L'admin peut creer tout autre user (manager, engineer, ...) avec un
     * quelconque profile parametré à l'avance. le password est aléatoirement
     * generé en envoyé par mail directement au propriétaire du nouveau compte
     *
     * @param userDTO
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping({"/registerNewUser"})
    public ResponseEntity<?> registerNewInterneUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userservice.registerNewInterneUser(userDTO), HttpStatus.CREATED);
    }

    /**
     * L'admin peut renvoyer un mot géneré aléatoirement par mail à un user
     *
     * @param email: l'email du user à qui on renvoie le password
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping({"/resend-password/{email}"})
    public ResponseEntity<?> renvoyerPassword(@PathVariable String email) {
        return new ResponseEntity<>(userservice.resendPassword(email), HttpStatus.OK);
    }
//    
//    //for admin to register new engineer
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PostMapping({"/registerNewEngineer"})
//    public User RegisterNewEngineer(@RequestBody User engg) {
//        return userservice.registerNewEngineer(engg);
//    }
//
//    //for admin to register new manager
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PostMapping({"/registerNewManager"})
//    public User RegisterNewManager(@RequestBody User manager) {
//        return userservice.registerNewManager(manager);
//    }

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
    @PostMapping("/change-user-password")
    public ResponseEntity<String> changeUserPassword(final @RequestBody PasswordModif passwordModif) {
        return new ResponseEntity<>(userservice.changeUserPassword(passwordModif), HttpStatus.OK);
    }

    @GetMapping("/allUser")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userservice.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        userservice.deleteUser(userName);
        return new ResponseEntity<>("Suppression réussie.", HttpStatus.NO_CONTENT);
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
