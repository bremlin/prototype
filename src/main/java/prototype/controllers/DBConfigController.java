package prototype.controllers;

import com.primavera.ServerException;
import com.primavera.integration.client.ClientException;
import com.primavera.integration.client.RMIURL;
import com.primavera.integration.client.Session;
import com.primavera.integration.network.NetworkException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import prototype.config.MsSqlConnect;
import prototype.config.SqlConfig;

public class DBConfigController {
    @FXML
    public TextField serverName;
    @FXML
    public TextField configName;
    @FXML
    public Button applyButton;
    @FXML
    public TextField serverPort;
    @FXML
    public TextField dbName;
    @FXML
    public TextField user;
    @FXML
    public PasswordField pass;
    @FXML
    public Label labelName;
    @FXML
    public Label labelPass;
    @FXML
    public AnchorPane pane;

    private MsSqlConnect.DB type;
    private Action action;

    private SqlConfig sqlConfig;

    public void setMode(MsSqlConnect.DB type) {
        this.type = type;
        this.action = Action.ADD;
    }

    public void editConfig(SqlConfig configEdit) {
        sqlConfig = configEdit;
        this.type = configEdit.getType();
        if (type != MsSqlConnect.DB.Primavera) {
            this.dbName.setText(configEdit.getDbName());
        }
        this.configName.setText(configEdit.getConfigName());
        this.serverName.setText(configEdit.getServerName());
        this.serverPort.setText(configEdit.getPort());
        this.user.setText(configEdit.getUser());
        this.pass.setText(configEdit.getPassword());
        this.action = Action.EDIT;
    }

    public void apply(ActionEvent actionEvent) {
        if (checkField(configName) && checkField(serverName) && checkField(serverPort) && checkField(user) && checkField(pass)) {
            switch (action) {
                case ADD:
                    switch (type) {
                        case Primavera:
                            sqlConfig = new SqlConfig(configName.getText(), serverName.getText(), serverPort.getText(),"", user.getText(), pass.getText(), type);
                            break;
                            default:
                                sqlConfig = new SqlConfig(configName.getText(), serverName.getText(), serverPort.getText(), dbName.getText(), user.getText(), pass.getText(), type);
                            break;
                    }
                    break;
                case EDIT:
                    sqlConfig.setConfigName(configName.getText());
                    sqlConfig.setServerName(serverName.getText());
                    sqlConfig.setPort(serverPort.getText());
                    switch (type) {
                        case Primavera:
                            sqlConfig.setDbName("");
                            break;
                            default:
                                sqlConfig.setDbName(dbName.getText());
                                break;
                    }
                    sqlConfig.setUser(user.getText());
                    sqlConfig.setPassword(pass.getText());
                    break;
            }
            close(actionEvent);
        }
    }

    private boolean checkField(TextField textField) {
        if (textField.getText().length() > 0) {
            textField.setStyle("-fx-border-color: #a1f3ff");
            return true;
        } else {
            textField.setStyle("-fx-border-color: red");
            return false;
        }
    }

    public void cancel(ActionEvent actionEvent) {
        close(actionEvent);
    }

    private void close(Event actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public SqlConfig getSqlConfig() {
        return sqlConfig;
    }

    public void connectionCheck(ActionEvent actionEvent) {
        System.out.println(System.getProperty("java.version"));

        switch (type) {
            case Primavera:
                try {
                    Session.getDatabaseInstances(RMIURL.getRmiUrl(1, serverName.getText(), Integer.parseInt(serverPort.getText())));
                    if (Session.getDatabaseInstances(RMIURL.getRmiUrl(1, serverName.getText(), Integer.parseInt(serverPort.getText()))).length > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Конфигурация подключения к Primavera");
                        alert.setHeaderText("Успешное подключение");
                        alert.setContentText("Подключение к " + serverName.getText() + " успешно произведено");
                        alert.showAndWait();
                    }
                } catch (ServerException | NetworkException | ClientException e) {
                    e.printStackTrace();
                }
                break;
            default:
                StringBuilder configString = new StringBuilder();
                configString.append(serverName.getText());

                configString.append(":");
                configString.append(serverPort.getText());

                String userName = user.getText();
                String domainName;

                if (userName.contains("\\")) {
//            System.setProperty("java.library.path", "C:\\Users\\s_shmakov\\Downloads\\sqljdbc_auth.dll");
                    configString.append(";integratedSecurity=true");
                    configString.append(";authenticationScheme=NTLM");
                    configString.append(";domain=");
                    domainName = userName.substring(0, userName.indexOf("\\"));
                    userName = userName.substring(userName.indexOf("\\")+1);
                    configString.append(domainName);
                    configString.append(";databasename=");
                    configString.append(dbName.getText());
                } else {
                    configString.append(";database=");
                    configString.append(dbName.getText());
                }

                configString.append(";user=");
                configString.append(userName);

                configString.append(";password=");
                configString.append(pass.getText());
                configString.append(";");

                if (MsSqlConnect.ConnectDb(configString.toString()) != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Конфигурация подключения Factage");
                    alert.setHeaderText("Успешное подключение");
                    alert.setContentText("Подключение к " + dbName.getText() + " успешно произведено");
                    alert.showAndWait();
                }
                break;
        }
    }

    public enum Action {
        ADD,
        EDIT
    }
}


