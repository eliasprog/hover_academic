package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
//import jfxOptionPane.application.JFXOptionPane;
import sample.model.bean.Session;
import sample.ConfigApp;
import sample.MainApp;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sample.criptography.Criptography;
import sample.LoginApp;
import sample.model.DAO.UserDAO;
import sample.model.bean.Usuario;



public class LoginController implements Initializable {
    
    @FXML ImageView btnConfig = new ImageView();
    @FXML ImageView btnClose = new ImageView();
    @FXML JFXTextField txtLogin = new JFXTextField();
    @FXML JFXPasswordField txtPassword = new JFXPasswordField();
    @FXML JFXButton btnLogin = new JFXButton();
    @FXML Label textUserMessage = new Label();
    @FXML Label textPassMessage = new Label();

    //JFXOptionPane optionPane = new JFXOptionPane(LoginApp.getLoginStage());


    double x = 0;
    double y = 0;
    
    @FXML 
    public void btnConfigAction () throws IOException {

        //Tratar erros
        new ConfigApp(LoginApp.getLoginStage());

    }
    
    @FXML
    public void close(){

       LoginApp.getLoginStage().close();
       Platform.exit();
       System.exit(0);

    }
    
    @FXML
    public void login(ActionEvent evt) throws IOException {

        if(txtLogin.getText().equals("")) {

            textUserMessage.setText("Digite um usuário!");
            textPassMessage.setText(null);

        }else {

            UserDAO userDAO = new UserDAO();
            Usuario user = userDAO.select(txtLogin.getText());

            if(user.getLogin() == null) {

                // System.out.println("Usuário Inválido!");
                textUserMessage.setText(txtLogin.getText()+" não é  um usuário cadastrado!");
                textPassMessage.setText(null);

            }else {

                textUserMessage.setText(null);
                textPassMessage.setText(null);

                if(Criptography.enc(txtPassword.getText()).equals(user.getSenha())){

                    Session.start(user);

                    txtLogin.setText(null);
                    txtPassword.setText(null);

                    new MainApp();

                    LoginApp.getLoginStage().close();


                }else{
                    //System.out.println("Senha Inválida!");
                    textPassMessage.setText("Senha Inválida!");
                }

            }

        }

    }

    @FXML
    public void dragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void pressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtLogin.setText("root");
        txtPassword.setText("1234");

        txtLogin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                // oldValue = Texto anterior a edição
                // newValue = Texto atual
                textUserMessage.setText(null);
            }
        });

        txtPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                // oldValue = Texto anterior a edição
                // newValue = Texto atual
                textPassMessage.setText(null);
            }
        });

    }
}
