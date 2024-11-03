package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import com.example.bo.BOFactory;
import com.example.bo.custom.TransactionBO;
import com.example.dto.TransactionDto;
import com.example.tm.AdminHistoryTm;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class AdminTransactionHistoryFormController {

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colTransactionId;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<AdminHistoryTm> tblHistory;

    private ObservableList<AdminHistoryTm> obList;

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSACTION);

    public void initialize(){
        setCellValue();
        getAllTransaction();
    }

    private void setCellValue() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void getAllTransaction(){
        obList= FXCollections.observableArrayList();
        List<TransactionDto> allTransaction = transactionBO.getTransactions();

        for (TransactionDto dto: allTransaction){

            Button buttonRemove=createRemoveButton();

            obList.add(new AdminHistoryTm(
                    dto.getTransactionId(),
                    dto.getUser().getUserName(),
                    dto.getBook().getTitle(),
                    dto.getBook().getGenre(),
                    (Date) dto.getBorrowingDate(),
                    (Date) dto.getReturnDate(),
                    dto.getStatus(),
                    buttonRemove

            ));
        }

        tblHistory.setItems(obList);
    }

    public Button createRemoveButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("removeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setDeleteBtnAction(btn);
        return btn;
    }

    private void setDeleteBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();


            if (type.orElse(no) == yes) {
                int focusedIndex = tblHistory.getSelectionModel().getSelectedIndex();
                AdminHistoryTm adminHistoryTm = (AdminHistoryTm) tblHistory.getSelectionModel().getSelectedItem();

                if (adminHistoryTm != null) {
                    String transactionId = adminHistoryTm.getTransactionId();
                    boolean b = transactionBO.delete(transactionId);
                    if (b) {
                        Image image=new Image("/assests/icons/iconsDelete.png");
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Book Delete Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                        System.out.println("delete selected");
                        obList.remove(focusedIndex);
                        getAllTransaction();
                    }
                }
            }
        });
    }

}

