package prototype.controllers;

import com.primavera.PrimaveraException;
import com.primavera.integration.common.DatabaseInstance;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import prototype.config.ConfigSql;
import prototype.primavera.dLogin;

public class PrimaLoginController {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public ComboBox serverComboBox;
    @FXML
    public ComboBox dbComboBox;
    @FXML
    public Button configServerButton;
    @FXML
    public Button loginButton;

    public dLogin primaveraLogin;

    public void init(dLogin primaveraLogin) {
        this.primaveraLogin = primaveraLogin;

//        if (primaveraLogin.sCallingMode.equals(dLogin.LOCAL_MODE)) {
            DatabaseInstance[] dbInstances = primaveraLogin.getDatabaseInstances(false);
            serverComboBox.getItems().add(ConfigSql.primaServer);
            serverComboBox.getSelectionModel().selectFirst();
            dbComboBox.getItems().clear();
            for (DatabaseInstance dbInstance : dbInstances) {
                dbComboBox.getItems().add(dbInstance);
            }
            dbComboBox.getSelectionModel().selectFirst();
//        }
    }

    public void configServer(ActionEvent actionEvent) {

    }

    public void login(Event actionEvent) {

        dLogin.LoginInfo loginInfo = new dLogin.LoginInfo();
        loginInfo.sUserName = loginField.getText();
        loginInfo.sPassword = passwordField.getText();

        DatabaseInstance dbi = (DatabaseInstance) dbComboBox.getSelectionModel().getSelectedItem();
        loginInfo.sDatabaseId = dbi.getDatabaseId();
        primaveraLogin.setLoginInfo(loginInfo);
        try {
            primaveraLogin.login();
            close(actionEvent);
        } catch (PrimaveraException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Подключение к Primavera");
            alert.setHeaderText("Не удалось подключиться к серверу Primavera");
            alert.setContentText("Пожалуйста, проверьте настройки Primavera");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void cancelButton(ActionEvent actionEvent) {
        close(actionEvent);
    }

    private void close(Event actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login(keyEvent);
        }
    }
}
