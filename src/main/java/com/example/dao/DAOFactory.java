package com.example.dao;


import com.example.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes {
        ADMIN, USER, BOOKS, BRANCHES, TRANSACTION
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case ADMIN:
                return new AdminDaoImpl();
            case USER:
                return new UserDaoImpl();
            case BOOKS:
                return new BooksDaoImpl();
            case BRANCHES:
                return new BranchesDaoImpl();
            case TRANSACTION:
                return new TransactionDAOImpl();
            default:
                return null;
        }
    }
}
