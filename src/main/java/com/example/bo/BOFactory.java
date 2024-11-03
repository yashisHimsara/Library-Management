package com.example.bo;


import com.example.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBOFactory(){
        return (boFactory==null)?boFactory=new BOFactory():boFactory;
    }
    public enum BOTypes{
        ADMIN, USER, BOOKS, BRANCHES, TRANSACTION
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case ADMIN:
                return new AdminBoImpl();
            case USER:
                return new UserBoImpl();
            case BOOKS:
                return new BooksBoImpl();
            case BRANCHES:
                return new BranchesBoImpl();
            case TRANSACTION:
                return new TransactionBOImpl();
            default:
                return null;
        }
    }
}
