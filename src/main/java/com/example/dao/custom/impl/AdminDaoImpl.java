package com.example.dao.custom.impl;

import com.example.dao.custom.AdminDAO;
import com.example.entity.Admin;

import java.util.List;

public class AdminDaoImpl implements AdminDAO {
    @Override
    public boolean add(Admin entity) {
        return false;
    }

    @Override
    public List<Admin> getAll() {
        return null;
    }

    @Override
    public boolean update(Admin entity) {
        return false;
    }

    @Override
    public boolean isExists(String id) {
        return false;
    }

    @Override
    public Admin search(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
