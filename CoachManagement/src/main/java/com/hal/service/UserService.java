/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hal.service;

import com.hal.pojo.User;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface UserService {
    User getUserById(int id);
    boolean addUser(User user);
    List<User> getUsers(String username);
}
