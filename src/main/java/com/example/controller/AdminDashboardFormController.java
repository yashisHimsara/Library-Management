package com.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.bo.BOFactory;
import com.example.bo.custom.BooksBO;
import com.example.bo.custom.TransactionBO;
import com.example.bo.custom.UserBO;
import com.example.dto.BooksDTO;
import com.example.dto.UserDTO;
import com.example.entity.Books;
import com.example.entity.Transaction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class AdminDashboardFormController {

    @FXML
    private Pane availableBooksPane;

    @FXML
    private Pane categoryPane;

    @FXML
    private Pane helloPane;

    @FXML
    private ImageView imgBooks;

    @FXML
    private ImageView imgBranches;

    @FXML
    private ImageView imgDashBoard;

    @FXML
    private ImageView imgUsers;

    @FXML
    private ImageView imgHistory;

    @FXML
    private Label lblAvailableBookCount;

    @FXML
    private Label lblTodayBorrowedBooksCount;

    @FXML
    private Label lblBiographyAndAutobiographyAndMemoirCount;

    @FXML
    private Label lblBooks;

    @FXML
    private Label lblBranches;

    @FXML
    private Label lblDashBoard;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblHistory;

    @FXML
    private Label lblInspirationalAndSelfHelpAndReligiousCount;

    @FXML
    private Label lblChildrensCount;

    @FXML
    private Label lblRomanceCount;

    @FXML
    private Label lblTotalBooksCount;

    @FXML
    private Label lblTotalReadersCount;

    @FXML
    private Label lblMysteryCount;

    @FXML
    private Label lblTrillersAndHorrorsCount;

    @FXML
    private Label lblUsers;

    @FXML
    private Label lblYoungAdultCount;

    @FXML
    private Label lblFantacyAndScienceCount;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private Pane totalReadersPane;

    @FXML
    private Pane topReaderPane;

    @FXML
    private Pane totalBooksPane;

    @FXML
    private Pane totalBorrowedBooksPane;

    @FXML
    private Pane logOutPane;

    private BooksBO booksBO= (BooksBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKS);

    private UserBO userBO= (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    private TransactionBO transactionBO= (TransactionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.TRANSACTION);

    public void initialize(){
        imgDashBoardFocused();
        setShadowsToPanes();
        setDate();
        customLogOutPane();
        setLblCounts();
    }

    @FXML
    void lblBooksOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/BooksForm.fxml");

        imgBooksFocused();
        imgUsersDefault();
        imgDashBoardDefault();
        imgBranchesDefault();
        imgHistoryDefault();
    }

    @FXML
    void btnSeeHistoryOnAction(ActionEvent event) throws IOException {
        setUI(subRoot,"/view/AdminTransactionHistoryForm.fxml");

        imgDashBoardDefault();
        imgUsersDefault();
        imgBooksDefault();
        imgBranchesDefault();
        imgHistoryFocused();
    }

    @FXML
    void lblBranchesOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/BranchesForm.fxml");

        imgBranchesFocused();
        imgDashBoardDefault();
        imgUsersDefault();
        imgBooksDefault();
        imgHistoryDefault();
    }

    @FXML
    void lblDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(root,"/view/AdminDashboardForm.fxml");


        imgDashBoardFocused();
        imgUsersDefault();
        imgBooksDefault();
        imgBranchesDefault();
        imgHistoryDefault();
    }

    @FXML
    void lblUsersOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/UserForm.fxml");


        imgDashBoardDefault();
        imgUsersFocused();
        imgBooksDefault();
        imgBranchesDefault();
        imgHistoryDefault();
    }

    @FXML
    void lblHistoryOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/AdminTransactionHistoryForm.fxml");

        imgDashBoardDefault();
        imgUsersDefault();
        imgBooksDefault();
        imgBranchesDefault();
        imgHistoryFocused();
    }

    public void setUI (AnchorPane pane,String location) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource(location)));
    }

    void setImageToImageView(ImageView imageView, String imagePath) {
        File file = new File(imagePath);

        if (file.exists()) {
            Image image = new Image(file.toURI().toString());

            imageView.setImage(image);
        } else {
            System.out.println("Image file not found: " + imagePath);
        }
    }

    void imgDashBoardDefault (){
        lblDashBoard.setStyle("-fx-text-fill: #000000;");

        setImageToImageView(imgDashBoard, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-dashboard-48 (2).png");
    }

    void imgDashBoardFocused (){
        lblDashBoard.setStyle("-fx-text-fill: #1479ff;");

        setImageToImageView(imgDashBoard, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-dashboard-48 (3).png");
    }

    void imgUsersDefault(){
        lblUsers.setStyle("-fx-text-fill: #000000;");
        setImageToImageView(imgUsers, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-users-30.png");
    }

    void imgUsersFocused(){
        lblUsers.setStyle("-fx-text-fill: #1479ff;");
        setImageToImageView(imgUsers, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-users-30 (1).png");
    }

    void imgBooksDefault(){
        lblBooks.setStyle("-fx-text-fill: #000000;");
        setImageToImageView(imgBooks, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-books-24.png");
    }

    void imgBooksFocused(){
        lblBooks.setStyle("-fx-text-fill: #1479ff;");
        setImageToImageView(imgBooks, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-books-24 (1).png");
    }

    void imgBranchesDefault(){
        lblBranches.setStyle("-fx-text-fill: #000000;");
        setImageToImageView(imgBranches, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-houses-50.png");
    }

    void imgBranchesFocused(){
        lblBranches.setStyle("-fx-text-fill: #1479ff;");
        setImageToImageView(imgBranches, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-houses-50 (1).png");
    }

    void imgHistoryDefault(){
        lblHistory.setStyle("-fx-text-fill: #000000;");
        setImageToImageView(imgHistory, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-history-30 (1).png");
    }

    void imgHistoryFocused(){
        lblHistory.setStyle("-fx-text-fill: #1479ff;");
        setImageToImageView(imgHistory, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-history-30.png");
    }

    void setShadowsToPanes(){
        /*totalReadersPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #599dff, 10, 0, 1, 2); -fx-background-radius: 10px;");*/
        totalReadersPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        totalBooksPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        availableBooksPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        totalBorrowedBooksPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        helloPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        categoryPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
    }

    void customLogOutPane(){
        logOutPane.setStyle("-fx-background-color: linear-gradient(to bottom, #207cca 0%,#9db2c4 100%); -fx-background-radius: 10px;");
    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {


        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Logout?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminLoginForm.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    void setLblCounts(){
        setLblFantacyAndScienceCount();
        setLblRomanceCount();
        setLblMysteryCount();
        setLblYoungAdultCount();
        setLblChildrensCount();
        setLblTrillersAndHorrorsCount();
        setLblInspirationalAndSelfHelpAndReligiousCount();
        setLblBiographyAndAutobiographyAndMemoirCount();
        setLblTotalReadersCount();
        setLblTotalBooksCount();
        setLblAvailableBookCount();
        setLblTodayBorrowedBooksCount();
    }

    public void setLblMysteryCount(){
        String genre = "Mystery";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();

        if (size < 10) {
            lblMysteryCount.setText("0" + String.valueOf(size));
        }else {
            lblMysteryCount.setText(String.valueOf(size));
        }
    }

    public void setLblRomanceCount(){
        String genre = "Romance";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();

        if (size < 10) {
            lblRomanceCount.setText("0" + String.valueOf(size));
        }else {
            lblRomanceCount.setText(String.valueOf(size));
        }
    }

    public void setLblFantacyAndScienceCount(){
        String genre = "Fantasy";
        String genre2 = "Science";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();
        bookByGenre = booksBO.getBookByGenre(genre2);
        size = size + bookByGenre.size();

        if (size < 10) {
            lblFantacyAndScienceCount.setText("0" + String.valueOf(size));
        }else {
            lblFantacyAndScienceCount.setText(String.valueOf(size));
        }
    }

    public void setLblTrillersAndHorrorsCount(){
        String genre = "Thriller";
        String genre2 = "Horror";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();
        bookByGenre = booksBO.getBookByGenre(genre2);
        size = size + bookByGenre.size();

        if (size < 10) {
            lblTrillersAndHorrorsCount.setText("0" + String.valueOf(size));
        }else {
            lblTrillersAndHorrorsCount.setText(String.valueOf(size));
        }
    }

    public void setLblYoungAdultCount(){
        String genre = "Young Adult";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();

        if (size < 10) {
            lblYoungAdultCount.setText("0" + String.valueOf(size));
        }else {
            lblYoungAdultCount.setText(String.valueOf(size));
        }
    }

    public void setLblChildrensCount(){
        String genre = "Children's";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();

        if (size < 10) {
            lblChildrensCount.setText("0" + String.valueOf(size));
        }else {
            lblChildrensCount.setText(String.valueOf(size));
        }
    }

    public void setLblInspirationalAndSelfHelpAndReligiousCount(){
        String genre = "Inspirational";
        String genre2 = "Religious";
        String genre3 = "Self-help";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();
        bookByGenre = booksBO.getBookByGenre(genre2);
        size = size + bookByGenre.size();
        bookByGenre = booksBO.getBookByGenre(genre3);
        size = size + bookByGenre.size();

        if (size < 10) {
            lblInspirationalAndSelfHelpAndReligiousCount.setText("0" + String.valueOf(size));
        }else {
            lblInspirationalAndSelfHelpAndReligiousCount.setText(String.valueOf(size));
        }
    }

    public void setLblBiographyAndAutobiographyAndMemoirCount(){
        String genre = "Biography";
        String genre2 = "Autobiography";
        String genre3 = "Memoir";
        List<Books> bookByGenre = booksBO.getBookByGenre(genre);
        int size = bookByGenre.size();
        bookByGenre = booksBO.getBookByGenre(genre2);
        size = size + bookByGenre.size();
        bookByGenre = booksBO.getBookByGenre(genre3);
        size = size + bookByGenre.size();

        if (size < 10) {
            lblBiographyAndAutobiographyAndMemoirCount.setText("0" + String.valueOf(size));
        }else {
            lblBiographyAndAutobiographyAndMemoirCount.setText(String.valueOf(size));
        }
    }

    public void setLblTotalReadersCount(){
        List<UserDTO> users = userBO.getAllUser();
        int size = users.size();
        if (size < 10) {
            lblTotalReadersCount.setText("0" + String.valueOf(size));
        }else {
            lblTotalReadersCount.setText(String.valueOf(size));
        }
    }

    public void setLblTotalBooksCount(){
        List<BooksDTO> books = booksBO.getAllBooks();
        int size = books.size();
        if (size < 10) {
            lblTotalBooksCount.setText("0" + String.valueOf(size));
        }else {
            lblTotalBooksCount.setText(String.valueOf(size));
        }
    }

    public void setLblAvailableBookCount(){
        List<Books> books = booksBO.getBookByAvailability();
        int size = books.size();
        if (size < 10) {
            lblAvailableBookCount.setText("0" + String.valueOf(size));
        }else {
            lblAvailableBookCount.setText(String.valueOf(size));
        }
    }

    public void setLblTodayBorrowedBooksCount(){
        List<Transaction> transactions = transactionBO.getAllToday();
        int size = transactions.size();
        if (size < 10) {
            lblTodayBorrowedBooksCount.setText("0" + String.valueOf(size));
        }else {
            lblTodayBorrowedBooksCount.setText(String.valueOf(size));
        }
    }
}
