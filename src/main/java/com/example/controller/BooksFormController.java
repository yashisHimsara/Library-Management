package com.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import com.example.bo.BOFactory;
import com.example.bo.custom.BooksBO;
import com.example.dto.BooksDTO;
import com.example.entity.Branches;
import com.example.tm.BooksTm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BooksFormController {

    @FXML
    private JFXComboBox<String> cmbAvailability;

    @FXML
    private JFXComboBox<String> cmbGenre;

    @FXML
    private TableColumn<?, ?> colAuthor;

    @FXML
    private TableColumn<?, ?> colAvailability;

    @FXML
    private TableColumn<?, ?> colBookId;

    @FXML
    private TableColumn<?, ?> colGenre;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<BooksTm> tblBooks;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtBookId;

    @FXML
    private JFXTextField txtSearchBar;

    @FXML
    private JFXTextField txtTitle;

    private BooksBO booksBO= (BooksBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BOOKS);

    private ObservableList<BooksTm> obList;

    public void initialize(){
        setCellValue();
        setGenreComboBoxItems();
        setAvailabilityComboBoxItems();
        loadAllBooks();
        searchTable();
        setTxtBookId();
    }

    private void setCellValue() {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(isEmptyCheck()){
            Image image=new Image("/assests/icons/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            String value = cmbAvailability.getValue();
            boolean available;
            if (value.equals("Available")){
                available = true;
            }else {
                available=false;
            }

            List<Branches> branches=new ArrayList<>();

            boolean b = booksBO.addBook(new BooksDTO(txtBookId.getText(), txtTitle.getText(), txtAuthor.getText(),
                    cmbGenre.getValue(), available));

            if (b){
                Image image=new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Book add success");
                    notifications.title("success");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }

                loadAllBooks();
                clearField();
                setTxtBookId();
            }
        }
    }

    public void loadAllBooks(){
        obList=FXCollections.observableArrayList();
        List<BooksDTO> allBooks = booksBO.getAllBooks();
        List<String> suggestionList = new ArrayList<>();

        for (BooksDTO dto: allBooks){
            suggestionList.add(String.valueOf(dto.getId()));

            Button buttonRemove=createRemoveButton();
            String available;
            if (dto.isAvailability()){
                available="available";
            }else{
                available="notAvailable";
            }

            obList.add(new BooksTm(
                    dto.getId(),
                    dto.getTitle(),
                    dto.getAuthor(),
                    dto.getGenre(),
                    available,
                    buttonRemove
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearchBar, suggestionArray);

        tblBooks.setItems(obList);
    }

    public Button createRemoveButton(){
        Button btn=new Button("Remove");
        btn.getStyleClass().add("removeBtn");
        btn.setCursor(Cursor.cursor("Hand"));
        setDeleteBtnAction(btn);
        return btn;
    }

    public void searchTable() {
        FilteredList<BooksTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(BooksTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String bookId = String.valueOf(BooksTm.getBookId());
                String title = BooksTm.getTitle().toLowerCase();
                String genre = BooksTm.getGenre().toLowerCase();

                return bookId.contains(lowerCaseFilter) || title.contains(lowerCaseFilter) || genre.contains(lowerCaseFilter);
            });
        });

        SortedList<BooksTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblBooks.comparatorProperty());
        tblBooks.setItems(sortedData);
    }

    private void setDeleteBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();


            if (type.orElse(no) == yes) {
                int focusedIndex = tblBooks.getSelectionModel().getSelectedIndex();
                BooksTm bookTm = (BooksTm) tblBooks.getSelectionModel().getSelectedItem();

                if (bookTm != null) {
                    String bookId = bookTm.getBookId();
                    boolean b = booksBO.deleteBook(bookId);
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
                        loadAllBooks();
                        searchTable();
                    }
                }
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookId = txtBookId.getText();
        boolean b = booksBO.deleteBook(bookId);
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
            loadAllBooks();
            searchTable();
            clearField();
        }else {
            System.out.println("something went wrong");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSearchBar.getText();

        if (booksBO.isExistBook(id)){
            BooksDTO bookDto = booksBO.searchBook(id);

            if (bookDto!=null){
                txtBookId.setText(bookDto.getId());
                txtTitle.setText(bookDto.getTitle());
                txtAuthor.setText(bookDto.getAuthor());
                cmbGenre.setValue(bookDto.getGenre());
                /*availabilityStatus.setValue(bookDto.isAvailability());*/

                Image image=new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Book Search Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("no book found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (isEmptyCheck()){
            Image image=new Image("/assest/icon/icons8-cancel-50.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("Value is empty! Please enter all values");
                notifications.title("Warning");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            String value = (String) cmbAvailability.getValue();
            boolean available;
            if (value.equals("Available")){
                available = true;
            }else {
                available=false;
            }
            List<Branches> branches=new ArrayList<>();


            boolean b = booksBO.updateBook(new BooksDTO(txtBookId.getText(), txtTitle.getText(), txtAuthor.getText(),
                    cmbGenre.getValue(), available));

            if (b) {
                Image image = new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Book update success");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                loadAllBooks();
                clearField();
                System.out.println("book update success");
            }
        }
    }

    @FXML
    void selectBookOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        int focusedIndex = tblBooks.getSelectionModel().getSelectedIndex();
        BooksTm booksTm = (BooksTm) tblBooks.getSelectionModel().getSelectedItem();

        if (booksTm != null) {
            String bookId = booksTm.getBookId();
            BooksDTO booksDTO = new BooksDTO();
            booksDTO = booksBO.searchBook(bookId);

            txtBookId.setText(booksDTO.getId());
            txtTitle.setText(booksDTO.getTitle());
            txtAuthor.setText(booksDTO.getAuthor());
            cmbGenre.setValue(booksDTO.getGenre());
            String available;
            if (booksDTO.isAvailability()){
                available="Available";
            }else{
                available="Not-available";
            }
            cmbAvailability.setValue(available);

            searchTable();
        }
    }

    void setGenreComboBoxItems(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        String romance = "Romance";
        String mystery = "Mystery";
        String fantasy = "Fantasy";
        String science = "Science";
        String thriller = "Thriller";
        String horror = "Horror";
        String youngAdult = "Young Adult";
        String children = "Children's";
        String inspirational = "Inspirational";
        String selfHelp = "Self-help";
        String religious = "Religious";
        String biography = "Biography";
        String autobiography = "Autobiography";
        String memoir = "Memoir";

        obList.add(romance);
        obList.add(mystery);
        obList.add(fantasy);
        obList.add(science);
        obList.add(thriller);
        obList.add(horror);
        obList.add(youngAdult);
        obList.add(children);
        obList.add(inspirational);
        obList.add(selfHelp);
        obList.add(religious);
        obList.add(biography);
        obList.add(autobiography);
        obList.add(memoir);

        cmbGenre.setItems(obList);
    }

    void setAvailabilityComboBoxItems(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        String available = "Available";
        String notAvailable = "Not-available";

        obList.add(available);
        obList.add(notAvailable);

        cmbAvailability.setItems(obList);
    }

    private boolean isEmptyCheck() {

        if(txtTitle.getText().isEmpty()){
            txtTitle.requestFocus();
            txtTitle.setFocusColor(Color.RED);
            System.out.println("Book title field is empty");
            /*Alert alert = new Alert(Alert.AlertType.ERROR, "Fill All fields");
            alert.showAndWait();*/
            return true;
        }
        if(txtAuthor.getText().isEmpty()){
            txtAuthor.requestFocus();
            txtAuthor.setFocusColor(Color.RED);
            System.out.println("Book author field is empty");
            /*Alert alert = new Alert(Alert.AlertType.ERROR, "Fill All fields");
            alert.showAndWait();*/
            return true;
        }
        if (cmbGenre.getValue().isEmpty()){
            cmbGenre.requestFocus();
            cmbGenre.setFocusColor(Color.RED);
            System.out.println("Book genre field is empty");
            /*Alert alert = new Alert(Alert.AlertType.ERROR, "Fill All fields");
            alert.showAndWait();*/
            return true;
        }
        return false;
    }

    public void clearField(){
        txtBookId.clear();
        txtTitle.clear();
        txtAuthor.clear();
        cmbGenre.setValue("Select genre");
        cmbAvailability.setValue("Select availability");
    }

    public void setTxtBookId(){
        txtBookId.setText(splitBooksId(booksBO.getLastBookId()));
    }

    private static String splitBooksId(String currentUserId) {
        if(currentUserId != null) {
            String[] split = currentUserId.split("B0");

            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "B00" + id;
            } else if (id < 100) {
                return "B0" + id;
            } else {
                return "B" + id;
            }
        } else {
            return "B001";
        }
    }

}
