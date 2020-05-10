package prototype.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prototype.config.ConfigSql;
import prototype.config.MsSqlConnect;
import prototype.config.SqlConfig;

import java.io.IOException;
import java.util.Optional;

public class FactageDBConfigController {
    @FXML
    public ComboBox serverFactageBox;
    @FXML
    public ComboBox serverInfactageBox;
    @FXML
    public ComboBox serverPrimaveraBox;

    private ConfigSql factageConfigSql;

    private boolean change = false;

    public void fillData(ConfigSql factageConfigSql) {
        this.factageConfigSql = factageConfigSql;

        if (factageConfigSql.getFactageConfigs().size() > 0) {
            serverFactageBox.getItems().addAll(factageConfigSql.getFactageConfigs());
            if (factageConfigSql.getFactageServer() != null) serverFactageBox.getSelectionModel().select(factageConfigSql.getFactageServer());
        }

        if (factageConfigSql.getInfactageConfigs().size() > 0) {
            serverInfactageBox.getItems().addAll(factageConfigSql.getInfactageConfigs());
            if (factageConfigSql.getInfactageConfigs() != null) serverInfactageBox.getSelectionModel().select(factageConfigSql.getInfactageServer());
        }

        if (factageConfigSql.getPrimaConfigs().size() > 0) {
            serverPrimaveraBox.getItems().addAll(factageConfigSql.getPrimaConfigs());
            if (factageConfigSql.getPrimaConfigs() != null) serverPrimaveraBox.getSelectionModel().select(ConfigSql.getPrimaServer());
        }
    }

    public void save(ActionEvent actionEvent) {
        setDefaultServer(serverFactageBox);
        setDefaultServer(serverInfactageBox);
        setDefaultServer(serverPrimaveraBox);
//        factageConfigSql.saveConfigs(serverFactageBox.getItems(), serverInfactageBox.getItems(), serverPrimaveraBox.getItems());
        change = true;
        close(actionEvent);
    }

    private void setDefaultServer(ComboBox comboBox) {
        if (comboBox.getSelectionModel().getSelectedItem() != null) {
            SqlConfig selectSqlConfig = (SqlConfig) comboBox.getSelectionModel().getSelectedItem();
            for (Object item : comboBox.getItems()) {
                SqlConfig sqlConfig = (SqlConfig) item;
                if (sqlConfig == selectSqlConfig) {
                    sqlConfig.setDefaultServer(true);
                } else {
                    sqlConfig.setDefaultServer(false);
                }
            }
        }
    }

    public void cancel(ActionEvent actionEvent) {
        close(actionEvent);
    }

    private void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public boolean isChange() {
        return change;
    }

    public void factageConfigEdit(ActionEvent actionEvent) {
        editConfig(serverFactageBox);
    }

    private void editConfig(ComboBox comboBox) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/dbConfig.fxml"));
            loader.load();

            DBConfigController dbConfigController = loader.getController();
            dbConfigController.editConfig((SqlConfig) comboBox.getSelectionModel().getSelectedItem());
            Parent parent = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Настройки подключения");
            stage.getIcons().add(new Image("/images/openProject.png"));
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            if (dbConfigController.getSqlConfig() != null) {
                comboBox.getItems().remove(comboBox.getSelectionModel().getSelectedItem());
                comboBox.getItems().add(dbConfigController.getSqlConfig());
                comboBox.getSelectionModel().select(dbConfigController.getSqlConfig());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void infactageConfigEdit(ActionEvent actionEvent) {
        editConfig(serverInfactageBox);
    }

    public void addConfigFactage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/dbConfig.fxml"));
            loader.load();

            DBConfigController dbConfigController = loader.getController();
            dbConfigController.setMode(MsSqlConnect.DB.Factage);
            Parent parent = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Настройки подключения Factage");
            stage.getIcons().add(new Image("/images/openProject.png"));
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            if (dbConfigController.getSqlConfig() != null) {
                serverFactageBox.getItems().add(dbConfigController.getSqlConfig());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addConfigInfactage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/dbConfig.fxml"));
            loader.load();

            DBConfigController dbConfigController = loader.getController();
            dbConfigController.setMode(MsSqlConnect.DB.Infactage);
            Parent parent = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Настройки подключения InFactage");
            stage.getIcons().add(new Image("/images/openProject.png"));
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            if (dbConfigController.getSqlConfig() != null) {
                serverInfactageBox.getItems().add(dbConfigController.getSqlConfig());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteConfigFactage(ActionEvent actionEvent) {
        removeCurrentConfig(serverFactageBox);
    }

    public void deleteConfigInfactage(ActionEvent actionEvent) {
        removeCurrentConfig(serverInfactageBox);
    }

    private void removeCurrentConfig(ComboBox comboBox) {
        Dialog<ButtonType> dialog = new Dialog<>();
        ButtonType removeButtonType = new ButtonType("Удалить", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.setContentText("Вы действительно хотите удалить конфигурацию?");

        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);
        dialog.getDialogPane().getButtonTypes().add(removeButtonType);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get().getButtonData().equals(ButtonType.APPLY.getButtonData())) {
            comboBox.getItems().remove(comboBox.getSelectionModel().getSelectedItem());
        }
    }

    public void deleteConfigPrima(ActionEvent actionEvent) {
        removeCurrentConfig(serverPrimaveraBox);
    }

    public void addConfigPrima(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/primaConfig.fxml"));
            loader.load();

            DBConfigController dbConfigController = loader.getController();
            dbConfigController.setMode(MsSqlConnect.DB.Primavera);
            Parent parent = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Настройки подключения к Primavera");
            stage.getIcons().add(new Image("/images/openProject.png"));
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            if (dbConfigController.getSqlConfig() != null) {
                serverPrimaveraBox.getItems().add(dbConfigController.getSqlConfig());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editConfigPrima(ComboBox comboBox) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/primaConfig.fxml"));
            loader.load();

            DBConfigController dbConfigController = loader.getController();
            dbConfigController.editConfig((SqlConfig) comboBox.getSelectionModel().getSelectedItem());
            Parent parent = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Настройки подключения");
            stage.getIcons().add(new Image("/images/openProject.png"));
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            if (dbConfigController.getSqlConfig() != null) {
                comboBox.getItems().remove(comboBox.getSelectionModel().getSelectedItem());
                comboBox.getItems().add(dbConfigController.getSqlConfig());
                comboBox.getSelectionModel().select(dbConfigController.getSqlConfig());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void primaveraConfigEdit(ActionEvent actionEvent) {
        editConfigPrima(serverPrimaveraBox);
    }
}