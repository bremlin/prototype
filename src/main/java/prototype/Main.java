package prototype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String ACTIVITY_DATA = "Activity";
    public static final String RESOURCE_DATA = "Resource";
    public static final String RELATION_DATA = "Relation";
    public static final String WBS_DATA = "WBS";

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainFrame.fxml"));
        stage.setTitle("Prototype");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
//        stage.getIcons().add(new Image("/icons/logo/logo_64x64.png"));
        stage.show();
    }
}
