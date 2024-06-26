/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sawdev.cnss.plainte.service;

import com.sawdev.cnss.plainte.dto.PasswordModif;
import com.sawdev.cnss.plainte.dto.UserDTO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface UserService {

    void saveInitAuthorities() throws FileNotFoundException, IOException;

    void saveInitProfil();

    void saveInitUser();

    UserDTO registerNewUser(UserDTO customer);

    UserDTO registerNewInterneUser(UserDTO userDTO);

    String resendPassword(String email);

//    User registerNewEngineer(User engg);
//
//    User registerNewManager(User manager);
    String resetPassword(String to);

    String changeUserPassword(PasswordModif passwordModif);

    List<UserDTO> getAllUsers();

    void deleteUser(String userName);

    String forAdmin();

    String forManager();

    String forCustomer();

    String forEngineer();

}
