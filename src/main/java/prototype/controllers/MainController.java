package prototype.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import prototype.HandlerXls;
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
    public TreeView relationPrimaveraResourceTree;

    private Image indicatorOffImage;
    private Image indicatorOnImage;

    private TreeTableUtils treeTableUtils = new TreeTableUtils();

    @FXML
    public void initialize() {
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

        activityTreeView.getColumns().add(treeTableUtils.addColumnId());
        activityTreeView.getColumns().add(treeTableUtils.addColumnName());
        activityTreeView.getColumns().add(treeTableUtils.addColumnDuration());
        activityTreeView.getColumns().add(treeTableUtils.addColumnStart());
        activityTreeView.getColumns().add(treeTableUtils.addColumnFinish());
        activityTreeView.getColumns().add(treeTableUtils.addColumnType());
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
        HandlerXls handlerXls;
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
    }

    public void close(ActionEvent actionEvent) {

    }

    public void primaConnect(ActionEvent actionEvent) {

    }

    public void primaSettings(ActionEvent actionEvent) {

    }

    public void about(ActionEvent actionEvent) {

    }

    public void addRelation(ActionEvent actionEvent) {

    }

    public void removeRelation(ActionEvent actionEvent) {

    }
}