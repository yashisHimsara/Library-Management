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
import com.example.bo.custom.BranchesBO;
import com.example.dto.BranchesDTO;
import com.example.entity.Books;
import com.example.tm.BranchesTm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BranchesFormController {

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane subRoot;

    @FXML
    private TableView<BranchesTm> tblBranches;

    @FXML
    private JFXTextField txtBranchCode;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtLocation;

    @FXML
    private JFXTextField txtSearch;

    private BranchesBO branchesBO= (BranchesBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.BRANCHES);

    private ObservableList<BranchesTm> obList;

    public void initialize(){
        setComboBoxItems();
        setCellValue();
        loadAllBranches();
        searchTable();
        setTxtBranchCode();
    }

    private void setCellValue() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }

    private void loadAllBranches(){
        obList= FXCollections.observableArrayList();
        List<BranchesDTO> allBranch = branchesBO.getAllBranch();
        List<String> suggestionList = new ArrayList<>();

        for (BranchesDTO dto: allBranch){
            suggestionList.add(String.valueOf(dto.getCode()));

            Button buttonRemove=createRemoveButton();

            obList.add(new BranchesTm(
                    dto.getCode(),
                    dto.getLocation(),
                    dto.getContactNumber(),
                    dto.getStatus(),
                    buttonRemove
            ));
        }
        String[] suggestionArray = suggestionList.toArray(new String[0]);
        TextFields.bindAutoCompletion(txtSearch, suggestionArray);

        tblBranches.setItems(obList);
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
                System.out.println("awa");
                int focusedIndex = tblBranches.getSelectionModel().getSelectedIndex();
                BranchesTm branchTm = (BranchesTm) tblBranches.getSelectionModel().getSelectedItem();

                if (branchTm != null) {
                    String bookId = branchTm.getCode();
                    boolean b = branchesBO.deleteBranch(bookId);
                    System.out.println("awa1");
                    if (b) {
                        System.out.println("awa3");
                        Image image=new Image("/assests/icons/iconsDelete.png");
                        Notifications notifications=Notifications.create();
                        notifications.graphic(new ImageView(image));
                        notifications.text("Branch Delete Successfully");
                        notifications.title("Successfully");
                        notifications.hideAfter(Duration.seconds(5));
                        notifications.position(Pos.TOP_RIGHT);
                        notifications.show();
                        System.out.println("delete selected");
                        obList.remove(focusedIndex);
                        loadAllBranches();
                        searchTable();
                    }
                }else{
                    System.out.println("awa null");
                }
            }
        });
    }

    public void searchTable() {
        FilteredList<BranchesTm> filteredData = new FilteredList<>(obList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(branchTm -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String branchId = String.valueOf(branchTm.getCode());
                String location = branchTm.getLocation().toLowerCase();

                return branchId.contains(lowerCaseFilter) || location.contains(lowerCaseFilter);
            });
        });

        SortedList<BranchesTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblBranches.comparatorProperty());
        tblBranches.setItems(sortedData);
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
            List<Books> books=new ArrayList<>();

            boolean b = branchesBO.addBranch(new BranchesDTO(txtBranchCode.getText(), txtLocation.getText(), txtContact.getText(), cmbStatus.getValue()));

            if (b){
                Image image=new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("branch add success");
                    notifications.title("success");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }

                loadAllBranches();
                clearFields();
                setTxtBranchCode();
            }
        }
    }

    private boolean isEmptyCheck() {

        if(txtBranchCode.getText().isEmpty()){
            txtBranchCode.requestFocus();
            txtBranchCode.setFocusColor(Color.RED);
            System.out.println("Branch id field is empty");
            return true;
        }
        if(txtLocation.getText().isEmpty()){
            txtLocation.requestFocus();
            txtLocation.setFocusColor(Color.RED);
            System.out.println("Branch name field is empty");
            return true;
        }
        if (txtContact.getText().isEmpty()){
            txtContact.requestFocus();
            txtContact.setFocusColor(Color.RED);
            System.out.println("Branch location field is empty");
            return true;
        }
        return false;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String bookId = txtBranchCode.getText();
        boolean b = branchesBO.deleteBranch(bookId);
        if (b) {
            Image image=new Image("/assests/icons/iconsDelete.png");
            Notifications notifications=Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Branch Delete Successfully");
            notifications.title("Successfully");
            notifications.hideAfter(Duration.seconds(5));
            notifications.position(Pos.TOP_RIGHT);
            notifications.show();
            loadAllBranches();
            searchTable();
        }else{
            System.out.println("Error");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSearch.getText();

        if (branchesBO.isExistBranch(id)){
            BranchesDTO branchDto = branchesBO.searchBranch(id);

            if (branchDto!=null){
                txtBranchCode.setText(branchDto.getCode());
                txtLocation.setText(branchDto.getLocation());
                txtContact.setText(branchDto.getContactNumber());
                cmbStatus.setValue(branchDto.getStatus());

                Image image=new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications=Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Branch Search Successfully");
                    notifications.title("Successfully");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("no branch found");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (isEmptyCheck()){
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
            boolean b = branchesBO.updateBranch(new BranchesDTO(txtBranchCode.getText(), txtLocation.getText(), txtContact.getText(), cmbStatus.getValue()));


            if (b) {
                Image image = new Image("/assests/icons/iconsOk.png");
                try {
                    Notifications notifications = Notifications.create();
                    notifications.graphic(new ImageView(image));
                    notifications.text("Branch update success");
                    notifications.title("Warning");
                    notifications.hideAfter(Duration.seconds(5));
                    notifications.position(Pos.TOP_RIGHT);
                    notifications.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                loadAllBranches();
                clearFields();
            }
        }
    }

    @FXML
    void selectBranchOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        int focusedIndex = tblBranches.getSelectionModel().getSelectedIndex();
        BranchesTm branchesTm = (BranchesTm) tblBranches.getSelectionModel().getSelectedItem();

        if (branchesTm != null) {
            String branchCode = branchesTm.getCode();
            BranchesDTO branchesDTO = new BranchesDTO();
            branchesDTO = branchesBO.searchBranch(branchCode);

            txtBranchCode.setText(branchesDTO.getCode());
            txtLocation.setText(branchesDTO.getLocation());
            txtContact.setText(branchesDTO.getContactNumber());
            cmbStatus.setValue(branchesDTO.getStatus());

            searchTable();
        }
    }

    void setComboBoxItems(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        String open = "Open";
        String close = "Closed";

        obList.add(open);
        obList.add(close);

        cmbStatus.setItems(obList);
    }

    void clearFields(){
        txtBranchCode.clear();
        txtLocation.clear();
        txtContact.clear();
        cmbStatus.setValue("Open or Close");
    }

    public void setTxtBranchCode(){
        txtBranchCode.setText(splitBranchId(branchesBO.getLastBranchId()));
    }

    private static String splitBranchId(String currentUserId) {
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

