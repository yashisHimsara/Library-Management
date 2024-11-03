package com.example.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import com.example.bo.BOFactory;
import com.example.bo.custom.UserBO;
import com.example.dto.UserDTO;

import java.io.IOException;

public class UserRegisterFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtPasswordReEnter;

    @FXML
    private JFXTextField txtUsername;

    private UserBO userBO= (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnSignupOnAction(ActionEvent event) {
        String userId = splitUserId(userBO.getLastUserId());

        boolean b = userBO.addUser(new UserDTO(userId, txtUsername.getText(), txtEmail.getText(), txtPasswordReEnter.getText()));
        if (b){
            Image image=new Image("/assests/icons/iconsOk.png");
            try {
                Notifications notifications=Notifications.create();
                notifications.graphic(new ImageView(image));
                notifications.text("User register complete");
                notifications.title("success");
                notifications.hideAfter(Duration.seconds(5));
                notifications.position(Pos.TOP_RIGHT);
                notifications.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void lblAdminLoginOnAction(MouseEvent event) throws IOException {
        setUI(root,"/view/AdminLoginForm.fxml");
    }

    @FXML
    void lblLoginOnAction(MouseEvent event) throws IOException {
        setUI(root,"/view/UserLoginForm.fxml");
    }

    @FXML
    void lblReaderLoginOnAction(MouseEvent event) throws IOException {
        setUI(root,"/view/UserLoginForm.fxml");
    }

    public void setUI (AnchorPane pane, String location) throws IOException {
        pane.getChildren().clear();
        pane.getChildren().add(FXMLLoader.load(getClass().getResource(location)));
    }


    private static String splitUserId(String currentUserId) {
        if(currentUserId != null) {
            String[] split = currentUserId.split("U0");

            int id = Integer.parseInt(split[1]);
            id++;
            if(id < 10) {
                return "U00" + id;
            } else if (id < 100) {
                return "U0" + id;
            } else {
                return "u" + id;
            }
        } else {
            return "U001";
        }
    }

}
