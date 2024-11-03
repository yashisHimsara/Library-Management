/*package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UserFormController {

    @FXML
    private Pane bookDetailsPane;

    @FXML
    private Pane selectUserFromTablePane01;

    @FXML
    private Pane selectUserFromTablePane02;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private Pane userDetailsPane;

    public void initialize(){
        setShadowsToPanes();

        selectUserFromTablePane01.setVisible(false);
        selectUserFromTablePane02.setVisible(false);
    }

    void setShadowsToPanes(){
        userDetailsPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        bookDetailsPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
    }

}*/

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
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;
import com.example.bo.BOFactory;
import com.example.bo.custom.UserBO;
import com.example.dto.UserDTO;
import com.example.tm.UsersTm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFormController {

    @FXML
    private Pane bookDetailsPane;

    @FXML
    private CheckBox checkBoxNotify;

    @FXML
    private JFXComboBox<String> cmbBookName;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private Label lblSetBooksToReturn;

    @FXML
    private Label lblSetDueDate;

    @FXML
    private Label lblSetEmail;

    @FXML
    private Label lblSetUserName;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblSetUserNameOnBooksDetailsPane;

    @FXML
    private Pane selectUserFromTablePane01;

    @FXML
    private Pane selectUserFromTablePane02;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<UsersTm> tblUser;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private Pane userDetailsPane;

    private UserBO userBO= (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    private ObservableList<UsersTm> obList;

    public void initialize(){
        setShadowsToPanes();
        setCellValue();
        loadAllUsers();
        searchTable();

        lblUserId.setVisible(false);
    }

    private void setCellValue() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("delete"));
    }

    private void loadAllUsers(){
        obList= FXCollections.observableArrayList();
        List<UserDTO> allUser = userBO.getAllUser();
        List<String> suggestionList = new ArrayList<>();

        for (UserDTO dto: allUser){
            suggestionList.add(String.valueOf(dto.getUserId()));

            Button buttonRemove=createRemoveButton();

            obList.add(new UsersTm(
                    dto.getUserId(),
                    dto.getUserName(),
                    dto.getEmail(),
                    buttonRemove
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearch, suggestionArray);

        tblUser.setItems(obList);
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
                int focusedIndex = tblUser.getSelectionModel().getSelectedIndex();
                UsersTm userTm = (UsersTm) tblUser.getSelectionModel().getSelectedItem();

                if (userTm != null) {
                    String userId = userTm.getUserId();
                    boolean b = userBO.deleteUser(userId);
                    if (b) {

                        Image image=new Image("/assest/icon/iconsDelete.png");
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("User Delete Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                        System.out.println("delete selected");
                        obList.remove(focusedIndex);
                        loadAllUsers();
                        searchTable();
                    }
                }
            }
        });
    }

    public void searchTable() {
        FilteredList<UsersTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(userTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String userId = String.valueOf(userTm.getUserId());
                String name = userTm.getUserName().toLowerCase();
                String mail = userTm.getEmail().toLowerCase();

                return userId.contains(lowerCaseFilter) || name.contains(lowerCaseFilter) || mail.contains(lowerCaseFilter);
            });
        });

        SortedList<UsersTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblUser.comparatorProperty());
        tblUser.setItems(sortedData);
    }

    @FXML
    void btnNotifyViaEmailOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveUserOnAction(ActionEvent event) {
        String userId = lblUserId.getText();
        boolean b = userBO.deleteUser(userId);
        if (b) {
            Image image=new Image("/assest/icon/iconsDelete.png");
            Notifications notifications=Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("User Delete Successfully");
            notifications.title("Successfully");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_RIGHT);
            notifications.show();
            loadAllUsers();
            searchTable();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBookNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    public void selectUserOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        int focusedIndex = tblUser.getSelectionModel().getSelectedIndex();
        UsersTm usersTm = (UsersTm) tblUser.getSelectionModel().getSelectedItem();

        if (usersTm != null) {
            String userId = usersTm.getUserId();
            UserDTO userDTO = new UserDTO();
            userDTO = userBO.searchUser(userId);

            lblUserId.setText(userDTO.getUserId());
            lblSetUserName.setText(userDTO.getUserName());
            lblSetEmail.setText(userDTO.getEmail());
            lblSetUserNameOnBooksDetailsPane.setText(userDTO.getUserName());

            selectUserFromTablePane01.setVisible(false);
            searchTable();
        }
    }

    void setShadowsToPanes(){
        userDetailsPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
        bookDetailsPane.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, #a9cdfa, 10, 0, 0, 6); -fx-background-radius: 10px;");
    }

}