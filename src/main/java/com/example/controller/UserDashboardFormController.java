package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.bo.BOFactory;
import com.example.bo.custom.BooksBO;
import com.example.bo.custom.UserBO;
import com.example.dto.BooksDTO;
import com.example.dto.UserDTO;
import com.example.entity.Books;
import com.example.tm.OurBooksTm;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDashboardFormController {

    public Label lblChildrenCount;
    public Label lblInspirationalAndSelfHelpAndReligiousCount;
    public Label lblBiographyAndAutobiographyAndMemoirCount;
    public Label lblYoungAdultCount;
    public Label lblMysteryCount;
    public Label lblFantasyAndScienceCount;
    public Label lblThrillersAndHorrorsCount;
    public Label lblRomanceCount;
    @FXML
    private Pane DashBoardIconPane;

    @FXML
    private Pane UsersIconPane1;

    @FXML
    private Pane UsersIconPane11;

    @FXML
    private ImageView imgBarrowBooks;

    @FXML
    private ImageView imgBarrowedBooks;

    @FXML
    private ImageView imgDashBoard;

    @FXML
    private ImageView imgHistory;

    @FXML
    private Pane logOutPane;

    @FXML
    private Pane booksPane;

    @FXML
    private Pane helloPane;

    @FXML
    private Label lblBooks;

    @FXML
    private Label lblBooks1;

    @FXML
    private Label lblDashBoard;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblHistory;

    @FXML
    private Label lblUserName;

    @FXML
    private AnchorPane root;

    @FXML
    private Pane statusPane;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<OurBooksTm> tblBooks;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private Pane topReaderPane;

    private BooksBO booksBO= (BooksBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKS);

    private ObservableList<OurBooksTm> obList;

    private UserBO userBO= (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize(){
        imgDashBoardFocused();
        setShadowsToPanes();
        setDate();
        setCellValue();
        loadAllBooks();
        customLogOutPane();
        setLblCounts();
        setLblUserName();
    }

    private void setCellValue() {
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
    }

    public void loadAllBooks(){
        obList= FXCollections.observableArrayList();
        List<BooksDTO> allBooks = booksBO.getAllBooks();
        List<String> suggestionList = new ArrayList<>();

        for (BooksDTO dto: allBooks){
            suggestionList.add(String.valueOf(dto.getId()));


            obList.add(new OurBooksTm(
                    dto.getTitle()
            ));
        }

        tblBooks.setItems(obList);
    }

    public void btnBorrowABookOnAction(ActionEvent actionEvent) throws IOException {
        setUI(subRoot,"/view/BorrowBookForm.fxml");

        imgDashBoardDefault();
        imgBooksFocused();
        imgBarrowedBooksDefault();
        imgHistoryDefault();
    }

    @FXML
    void lblBarrowBooksOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/BorrowBookForm.fxml");

        imgDashBoardDefault();
        imgBooksFocused();
        imgBarrowedBooksDefault();
        imgHistoryDefault();
    }

    @FXML
    void lblBarrowedBooksOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/BorrowedBooksForm.fxml");

        imgDashBoardDefault();
        imgBooksDefault();
        imgBarrowedBooksFocused();
        imgHistoryDefault();
    }

    @FXML
    void lblDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(root,"/view/UserDashboardForm.fxml");

        imgDashBoardFocused();
        imgBooksDefault();
        imgBarrowedBooksDefault();
        imgHistoryDefault();
    }

    @FXML
    void lblHistoryOnAction(MouseEvent event) throws IOException {
        setUI(subRoot,"/view/userTrasactionForm.fxml");

        imgHistoryFocused();
        imgDashBoardDefault();
        imgBooksDefault();
        imgBarrowedBooksDefault();
    }

    public void setUI (AnchorPane pane, String location) throws IOException {
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

    void imgBooksDefault(){
        lblBooks.setStyle("-fx-text-fill: #000000;");
        setImageToImageView(imgBarrowBooks, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-books-24.png");
    }

    void imgBooksFocused(){
        lblBooks.setStyle("-fx-text-fill: #1479ff;");
        setImageToImageView(imgBarrowBooks, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-books-24 (1).png");
    }

    void imgBarrowedBooksDefault(){
        lblBooks1.setStyle("-fx-text-fill: #000000;");
        setImageToImageView(imgBarrowedBooks, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-books-24.png");
    }

    void imgBarrowedBooksFocused(){
        lblBooks1.setStyle("-fx-text-fill: #1479ff;");
        setImageToImageView(imgBarrowedBooks, "C:\\Users\\ASUS\\Documents\\GitHub\\Library-Management-System\\src\\main\\resources\\assests\\icons8-books-24 (1).png");
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
        booksPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        statusPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        helloPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserLoginForm.fxml"));
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
            lblFantasyAndScienceCount.setText("0" + String.valueOf(size));
        }else {
            lblFantasyAndScienceCount.setText(String.valueOf(size));
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
            lblThrillersAndHorrorsCount.setText("0" + String.valueOf(size));
        }else {
            lblThrillersAndHorrorsCount.setText(String.valueOf(size));
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
            lblChildrenCount.setText("0" + String.valueOf(size));
        }else {
            lblChildrenCount.setText(String.valueOf(size));
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

    public void setLblUserName(){
        UserDTO userDTO = userBO.searchUser(UserLoginFormController.userId);
        lblUserName.setText("Hello! "+userDTO.getUserName());
    }
}
