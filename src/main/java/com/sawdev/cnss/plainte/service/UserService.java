/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sawdev.cnss.plainte.service;

import com.sawdev.cnss.plainte.entity.User;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface UserService {

//    void initRoles();
    void initUsers();

    User registerNewUser(User customer);

    User registerNewEngineer(User engg);

    User registerNewManager(User manager);

    String forAdmin();

    String forManager();

    String forCustomer();

    String forEngineer();

    List<User> getAllUsers();

    void deleteUser(String userName);

}
