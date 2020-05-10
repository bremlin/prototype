package prototype.config;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MsSqlConnect {

    public static Connection ConnectDb(DB type) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String connString = null;
//            switch (type) {
//                case Factage:
//                    connString = "jdbc:postgresql://" + FactageConfigSql.factageServer.config();
//                    break;
//                case Infactage:
//                    connString = "jdbc:postgresql://" + FactageConfigSql.infactageServer.config();
//                    break;
//            }
//            return DriverManager.getConnection(connString, FactageConfigSql.factageServer.getUser(), FactageConfigSql.factageServer.getPassword());
            switch (type) {
                case Factage:
                    connString = "jdbc:sqlserver://" + ConfigSql.factageServer.config();
                    System.out.println("connection: " + connString);
                    break;
                case Infactage:
                    connString = "jdbc:sqlserver://" + ConfigSql.infactageServer.config();
                    break;
            }
            return DriverManager.getConnection(connString);
        } catch (ClassNotFoundException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Конфигурация подключения Factage");
            alert.setHeaderText("Неверные настройки подключения");
            alert.setContentText("Пожалуйста, проведите настройку подключения Factage");
            alert.showAndWait();
            return null;
        }
    }

    public static Connection ConnectDb(String connInfo) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String connString = "jdbc:sqlserver://" + connInfo;
                    System.out.println("connection: " + connString);

            return DriverManager.getConnection(connString);
        } catch (ClassNotFoundException | SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Конфигурация подключения Factage");
            alert.setHeaderText("Неверные настройки подключения");
            alert.setContentText("Пожалуйста, проведите настройку подключения Factage");
            alert.showAndWait();
            return null;
        }
    }

    public enum DB {
        Factage,
        Infactage,
        Primavera,
        Check
    }
}