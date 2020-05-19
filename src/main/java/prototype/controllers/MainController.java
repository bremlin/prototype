package prototype.controllers;

import com.primavera.ServerException;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.network.NetworkException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prototype.HandlerXls;
import prototype.config.ConfigSql;
import prototype.config.MsSqlConnect;
import prototype.config.SqlConfig;
import prototype.helpers.EPSHelper;
import prototype.helpers.ResourceHelper;
import prototype.objects.PActivity;
import prototype.primavera.dLogin;
import prototype.utils.TableUtils;
import prototype.utils.TreeTableUtils;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    public Label mndIndicator;
    @FXML
    public Label primaveraIndicator;
    @FXML
    public TabPane tabPaneMND;
    @FXML
    public TreeTableView activityTreeView;
    @FXML
    public TableView resourceTable;
    @FXML
    public TableView activityCodeTable;
    @FXML
    public TreeView wbsTree;
    @FXML
    public TreeView relationMndResourceTree;
    @FXML
    public TableView relationPrimaveraResourceTree;
    @FXML
    public TreeView epsTreeView;

    private Image indicatorOffImage;
    private Image indicatorOnImage;

    private TreeTableUtils treeTableUtils = new TreeTableUtils();

    private HandlerXls handlerXls;
    private boolean loginStatus = false;

    private ConfigSql configSql;
    @FXML
    public void initialize() {
        configSql = new ConfigSql();

        indicatorOffImage = new Image("/icons/red_light.gif");
        indicatorOnImage = new Image("/icons/green_light.gif");
        ImageView mndIndicatorView = new ImageView(indicatorOffImage);
        ImageView primaveraIndicatorView = new ImageView(indicatorOffImage);
        mndIndicatorView.setFitHeight(7);
        mndIndicatorView.setFitWidth(7);
        primaveraIndicatorView.setFitHeight(7);
        primaveraIndicatorView.setFitWidth(7);
        mndIndicator.setGraphic(mndIndicatorView);
        primaveraIndicator.setGraphic(primaveraIndicatorView);

        TableUtils tableUtils = new TableUtils();

        activityTreeView.getColumns().add(treeTableUtils.addColumnId());
        activityTreeView.getColumns().add(treeTableUtils.addColumnName());
        activityTreeView.getColumns().add(treeTableUtils.addColumnDuration());
        activityTreeView.getColumns().add(treeTableUtils.addColumnStart());
        activityTreeView.getColumns().add(treeTableUtils.addColumnFinish());
        activityTreeView.getColumns().add(treeTableUtils.addColumnType());

        resourceTable.getColumns().add(tableUtils.addColumnName());
        resourceTable.getColumns().add(tableUtils.addColumnValue());
        resourceTable.getColumns().add(tableUtils.addColumnPvApply());

        relationPrimaveraResourceTree.getColumns().add(tableUtils.addColumnId());
        relationPrimaveraResourceTree.getColumns().add(tableUtils.addColumnName());
    }

    private void setIndicatorOn(Label indicator) {
        ImageView imageView = new ImageView(indicatorOnImage);
        imageView.setFitHeight(7);
        imageView.setFitWidth(7);
        indicator.setGraphic(imageView);
    }

    public void openXLSFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите XLS файл с моделью накопления данных");
        File xlsMND = fileChooser.showOpenDialog(mndIndicator.getScene().getWindow());

        if (xlsMND != null) {
            handlerXls = new HandlerXls(xlsMND);
            try {
                handlerXls.loadData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setIndicatorOn(mndIndicator);
            showData(handlerXls);
        }
    }

    private void showData(HandlerXls handlerXls) {
        wbsTree.setRoot(handlerXls.getPwbsHelper().getRoot());
        activityTreeView.setRoot(handlerXls.getpActivityHelper().getRoot());
        activityTreeView.setShowRoot(false);
    }

    public void close(ActionEvent actionEvent) {

    }

    public void primaConnect(ActionEvent actionEvent) {
        if (loginStatus) {
            dLogin.getInstance().logout();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/primaLogin.fxml"));
                loader.load();

                PrimaLoginController primaLoginController = loader.getController();
                Parent parent = loader.getRoot();

                Stage stage = new Stage();
                stage.setTitle("Войти в Primavera P6");
//                stage.getIcons().add(new Image("/images/openProject.png"));
                stage.setScene(new Scene(parent));
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);

                primaLoginController.init(dLogin.getInstance());

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (dLogin.session != null && dLogin.session.isValid()) {
                loginStatus = true;
                setIndicatorOn(primaveraIndicator);
                ResourceHelper resourceHelper = new ResourceHelper(dLogin.session);
                resourceHelper.run();
                relationPrimaveraResourceTree.getItems().addAll(resourceHelper.getResource());

                EPSHelper epsHelper = new EPSHelper(dLogin.session);
                epsHelper.load();
                epsTreeView.setRoot(epsHelper.getRootItem());
                System.out.println("test");
            } else {
                loginStatus = false;
//                logInOut();
            }
//            logInOut();
        } catch (NetworkException e) {
            e.printStackTrace();
        } catch (BusinessObjectException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    public void primaSettings(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/primaConfig.fxml"));
            loader.load();

            DBConfigController dbConfigController = loader.getController();
            if (configSql.getPrimaConfigs().size() > 0) {
                dbConfigController.editConfig(configSql.getPrimaConfigs().get(0));
            } else {
                dbConfigController.setMode(MsSqlConnect.DB.Primavera);
            }

            Parent parent = loader.getRoot();

            Stage stage = new Stage();
            stage.setTitle("Настройки подключения");
//            stage.getIcons().add(new Image("/images/openProject.png"));
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            if (dbConfigController.getSqlConfig() != null) {
                configSql.saveConfigs(null, null, dbConfigController.getSqlConfig());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void about(ActionEvent actionEvent) {

    }

    public void addRelation(ActionEvent actionEvent) {

    }

    public void removeRelation(ActionEvent actionEvent) {

    }

    public void activityTreeClick(MouseEvent mouseEvent) {
        if (activityTreeView.getSelectionModel().getSelectedItem() != null) {
            TreeItem selectedItem = (TreeItem) activityTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem.getValue().getClass().equals(PActivity.class)) {
                PActivity activity = (PActivity) selectedItem.getValue();
                if (handlerXls.getpResourceAssignmentHelper().getResources(activity.getId()) != null) {
                    resourceTable.getItems().clear();
                    resourceTable.getItems().addAll(handlerXls.getpResourceAssignmentHelper().getResources(activity.getId()));
                }
            } else {
                resourceTable.getItems().clear();
            }
        }
    }

    public void createProjectAction(ActionEvent actionEvent) {

    }

    public void setEPS(MouseEvent mouseEvent) {

    }
}