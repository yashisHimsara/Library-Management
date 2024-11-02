package com.example.dao.custom;

import com.example.dao.CrudDAO;
import com.example.entity.User;

public interface UserDAO extends CrudDAO<User> {
    public String getLastUserId();
}
