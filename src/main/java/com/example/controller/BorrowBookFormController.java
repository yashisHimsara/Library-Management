package com.example.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import com.example.bo.BOFactory;
import com.example.bo.custom.BooksBO;
import com.example.bo.custom.BranchesBO;
import com.example.bo.custom.TransactionBO;
import com.example.bo.custom.UserBO;
import com.example.dto.BooksDTO;
import com.example.dto.BranchesDTO;
import com.example.dto.TransactionDto;
import com.example.dto.UserDTO;
import com.example.entity.Books;
import com.example.entity.User;
import com.example.tm.BorrowBookTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowBookFormController {

    public CheckBox checkBoxAcceptTerms;

    @FXML
    private Label lblReturnDate;

    public Label lblTodayDate;
    public JFXComboBox cmbBranchSelect;
    public Label lblAuthorOnBorrowBook;
    public Label lblgenreOnBorrowBook;
    public Label lblBookNameOnBorrowBook;
    @FXML
    private Pane BooksDetailsPane;

    @FXML
    private Pane BorrowBookPane;

    @FXML
    private JFXButton btnBorrow;

    @FXML
    private JFXButton btnComfirm;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Pane bookDetailsPaneParent;

    @FXML
    private Pane borrowBookPaneParent;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private Pane tablePane;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label lblAvailableOrNot;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblGenre;

    @FXML
    private Label lblDate;

    @FXML
    private TableView<BorrowBookTm> tblBorrowBook;

    @FXML
    private JFXTextField txtSearch;

    private BooksBO booksBO= (BooksBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKS);

    private UserBO userBO= (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    private ObservableList<BorrowBookTm> obList;

    private BranchesBO branchesBO= (BranchesBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BRANCHES);

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSACTION);

    private String userId;

    public void initialize(){
        setShadowsToPanes();
        setDate();
        setCellValue();
        loadAllBooks();
        setCmbBranchSelect();
        searchTable();

        userId = UserLoginFormController.userId;
    }

    private void setCellValue() {
        colName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }

    public void loadAllBooks() {
        obList = FXCollections.observableArrayList();
        List<BooksDTO> allBooks = booksBO.getAllBooks();
        List<String> suggestionList = new ArrayList<>();

        for (BooksDTO dto : allBooks) {
            suggestionList.add(String.valueOf(dto.getId()));


            obList.add(new BorrowBookTm(
                    dto.getTitle(),
                    dto.getGenre()
            ));
        }

        tblBorrowBook.setItems(obList);
    }

    public void selectBookOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        int focusedIndex = tblBorrowBook.getSelectionModel().getSelectedIndex();
        BorrowBookTm borrowBookTm = (BorrowBookTm) tblBorrowBook.getSelectionModel().getSelectedItem();

        if (borrowBookTm != null) {
            String bookName = borrowBookTm.getBookName();
            BooksDTO booksDTO = new BooksDTO();
            booksDTO = booksBO.getBookByTitle(bookName);

            lblBookName.setText(booksDTO.getTitle());
            lblGenre.setText(booksDTO.getGenre());
            lblAuthor.setText(booksDTO.getAuthor());

            boolean availability = booksDTO.isAvailability();
            String available = null;
            if (availability){
                available = "Available";
                lblAvailableOrNot.setText(available);
                lblAvailableOrNot.setStyle("-fx-text-fill: #00ff00");
                btnBorrow.setDisable(false);

            }else {
                available = "Not Available";
                lblAvailableOrNot.setText(available);
                lblAvailableOrNot.setStyle("-fx-text-fill: red");
                btnBorrow.setDisable(true);
            }
            BooksDetailsPane.setVisible(false);
            BorrowBookPane.setVisible(true);
            searchTable();
        }
    }

    @FXML
    void btnBorrowOnAction(ActionEvent event) {
        BorrowBookPane.setVisible(false);

        lblBookNameOnBorrowBook.setText(lblBookName.getText());
        lblgenreOnBorrowBook.setText(lblGenre.getText());
        lblAuthorOnBorrowBook.setText(lblAuthor.getText());
        lblTodayDate.setText(lblDate.getText());
        lblReturnDate.setText(LocalDate.now().plusDays(14).toString());
        btnComfirm.setDisable(true);
        checkBoxAcceptTerms.setSelected(false);
        searchTable();

    }

    void setShadowsToPanes(){
        tablePane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        bookDetailsPaneParent.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        borrowBookPaneParent.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
    }

    public void btnComfirmOnAction(ActionEvent actionEvent) {
        String transactionId = transactionBO.getLastTransactionId();
        String newTransactionId = splitTransactionId(transactionId);
        String status = "To return";

        long millis=System.currentTimeMillis();
        Date borrowingDate=new Date(millis);

        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(14);
        Date returnDate = Date.valueOf(futureDate);

        String bookName = lblBookNameOnBorrowBook.getText();

        BooksDTO booksDTO = booksBO.getBookByTitle(bookName);
        UserDTO userDTO = userBO.searchUser(userId);

        Books books = new Books(booksDTO.getId(), booksDTO.getTitle(), booksDTO.getAuthor(), booksDTO.getGenre(), booksDTO.isAvailability());
        User user = new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getEmail(), userDTO.getPassword());

        TransactionDto transactionDto = new TransactionDto(newTransactionId, borrowingDate, returnDate, user, books, status);

        boolean isSaved = transactionBO.saveTransaction(transactionDto);
        if (isSaved){
            boolean isBorrowed = booksBO.borrowBook(booksDTO.getId());
            if (isBorrowed){
                Image image=new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Book borrowed");
                    notifications.title("Success ");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void searchTable() {
        FilteredList<BorrowBookTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(BorrowBookTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String title = BorrowBookTm.getBookName().toLowerCase();
                String genre = BorrowBookTm.getGenre().toLowerCase();

                return title.contains(lowerCaseFilter) || genre.contains(lowerCaseFilter);
            });
        });

        SortedList<BorrowBookTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblBorrowBook.comparatorProperty());
        tblBorrowBook.setItems(sortedData);
    }

    @FXML
    void checkBoxOnAction(ActionEvent event) {
        if (checkBoxAcceptTerms.isSelected()){
            btnComfirm.setDisable(false);
        }else{
            btnComfirm.setDisable(true);
        }
    }

    public void setCmbBranchSelect(){
        ObservableList<String> branchlist;
        branchlist= FXCollections.observableArrayList();
        List<BranchesDTO> allBranch = branchesBO.getAllBranch();

        for (BranchesDTO dto: allBranch){
            branchlist.add(dto.getLocation());
        }

        cmbBranchSelect.setItems(branchlist);
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    private static String splitTransactionId(String currentUserId) {
        if(currentUserId != null) {
            String[] split = currentUserId.split("T0");

            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "T00" + id;
            } else if (id < 100) {
                return "T0" + id;
            } else {
                return "T" + id;
            }
        } else {
            return "T001";
        }
    }
}