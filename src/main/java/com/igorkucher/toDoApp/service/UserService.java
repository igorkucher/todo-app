package com.igorkucher.toDoApp.service;

import com.igorkucher.toDoApp.model.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(Long id);
    User readByEmail(String email);
    User update(User user);
    void delete(Long id);
    List<User> getAll();

}
