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
import com.example.bo.custom.BooksBO;
import com.example.bo.custom.TransactionBO;
import com.example.dto.UserTransactionDto;
import com.example.tm.BorrowedBookTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowedBooksFormController {

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colReturn;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private TableColumn<?, ?> colTransactionId;

    @FXML
    private Label lblReadingCount;

    @FXML
    private Label lblToReturnCount;

    @FXML
    private Label lblReadCount;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<BorrowedBookTm> tblBorrowedBooks;

    private ObservableList<BorrowedBookTm> obList;

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSACTION);

    private BooksBO booksBO= (BooksBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKS);

    public void initialize(){
        setDate();
        setCellValue();
        getAllTransaction();
    }

    private void setCellValue() {
        colTransactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturn.setCellValueFactory(new PropertyValueFactory<>("returnBook"));
    }

    private void getAllTransaction(){
        obList= FXCollections.observableArrayList();
        List<UserTransactionDto> allTransaction = transactionBO.getUserTransactions();
        List<String> suggestionList = new ArrayList<>();

        for (UserTransactionDto dto: allTransaction){
            suggestionList.add(String.valueOf(dto.getBookId()));

            Button buttonReturn=createReturnButton();

            obList.add(new BorrowedBookTm(
                    dto.getTransactionId(),
                    dto.getBookId(),
                    dto.getTitle(),
                    dto.getAuthor(),
                    dto.getGenre(),
                    (Date) dto.getBorrowingDate(),
                    (Date) dto.getReturnDate(),
                    buttonReturn
            ));
        }

        tblBorrowedBooks.setItems(obList);
    }

    public Button createReturnButton(){
        Button btn=new Button("Return");
        btn.getStyleClass().add("removeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setReturnButtonOnAction(btn);
        return btn;
    }

    public void setReturnButtonOnAction(Button button){
        button.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Return Book?", yes, no).showAndWait();


            if (type.orElse(no) == yes) {
                int focusedIndex = tblBorrowedBooks.getSelectionModel().getSelectedIndex();
                BorrowedBookTm borrowedBookTm = (BorrowedBookTm) tblBorrowedBooks.getSelectionModel().getSelectedItem();

                if (borrowedBookTm != null) {
                    String bookId = borrowedBookTm.getBookId();
                    String transactionId = borrowedBookTm.getTransactionId();
                    boolean b = booksBO.returnBook(bookId);
                    if (b) {
                        boolean b1 = transactionBO.updateStatus(transactionId);
                        if (b1) {
                            Image image = new Image("/assests/icons/iconsOk.png");
                            Notifications notifications = Notifications.create();
                            notifications.graphic(new ImageView(image));
                            notifications.text("Book return Successfully");
                            notifications.title("Successfully");
                            notifications.hideAfter(Duration.seconds(5));
                            notifications.position(Pos.TOP_RIGHT);
                            notifications.show();
                            obList.remove(focusedIndex);
                            getAllTransaction();
                            /*searchTable();*/
                        }
                    }
                }
            }
        });
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

}
