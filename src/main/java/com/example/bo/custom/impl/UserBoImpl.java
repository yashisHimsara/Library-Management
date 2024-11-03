package com.example.bo.custom.impl;

import com.example.bo.custom.UserBO;
import com.example.dao.DAOFactory;
import com.example.dao.custom.UserDAO;
import com.example.dto.UserDTO;
import com.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBO {
    private UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean addUser(UserDTO dto) {
        return userDAO.add(new User(dto.getUserId(),dto.getUserName(),dto.getEmail(),dto.getPassword()));
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> all = userDAO.getAll();
        List<UserDTO> allDto = new ArrayList<UserDTO>();

        for (User user:all) {
            allDto.add(new UserDTO(user.getUserId(),user.getUserName(),user.getEmail(),user.getPassword()));
        }

        return allDto;
    }

    @Override
    public boolean updateUser(UserDTO dto) {
        return userDAO.update(new User(dto.getUserId(),dto.getUserName(),dto.getEmail(),dto.getPassword()));
    }

    @Override
    public boolean isExistUser(String id) {
        return userDAO.isExists(id);
    }

    @Override
    public UserDTO searchUser(String id) {
        User search = userDAO.search(id);
        UserDTO userDto = new UserDTO(search.getUserId(),search.getUserName(),search.getEmail(),search.getPassword());
        return userDto;
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }

    @Override
    public String getLastUserId() {
        return userDAO.getLastUserId();
    }
}
