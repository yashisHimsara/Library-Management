package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import com.example.bo.BOFactory;
import com.example.bo.custom.TransactionBO;
import com.example.dto.UserTransactionDto;
import com.example.tm.UserHistoryTm;

import java.sql.Date;
import java.util.List;

public class userTransactionFormController {

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colTransactionId;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<UserHistoryTm> tblHistory;

    private ObservableList<UserHistoryTm> obList;

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSACTION);

    public void initialize(){
        setCellValue();
        getAllTransaction();
    }

    private void setCellValue() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void getAllTransaction(){
        obList= FXCollections.observableArrayList();
        List<UserTransactionDto> allTransaction = transactionBO.getUserAllTransaction();

        for (UserTransactionDto dto: allTransaction){

            obList.add(new UserHistoryTm(
                    dto.getTransactionId(),
                    dto.getTitle(),
                    dto.getAuthor(),
                    dto.getGenre(),
                    (Date) dto.getBorrowingDate(),
                    (Date) dto.getReturnDate(),
                    dto.getStatus()
            ));
        }

        tblHistory.setItems(obList);
    }
}
