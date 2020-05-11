package prototype.config;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ConfigSql implements Serializable {

    private ArrayList<SqlConfig> factageConfigs = new ArrayList<>();
    private ArrayList<SqlConfig> infactageConfigs = new ArrayList<>();
    private ArrayList<SqlConfig> primaConfigs = new ArrayList<>();

    public static SqlConfig factageServer;
    public static SqlConfig infactageServer;
    public static SqlConfig primaServer;

    private String fileName = "Prototype.cfg";
    private String directory = "config";

    public ConfigSql() {
        File file = new File(directory + "\\" + fileName);
        if (file.exists() && file.length() > 0) {
            SqlConfig config = null;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                config = (SqlConfig) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
//            for (SqlConfig sqlConfig : configList) {
//                switch (sqlConfig.getType()) {
//                    case Factage:
//                        factageConfigs.add(sqlConfig);
//                        if (sqlConfig.isDefaultServer()) factageServer = sqlConfig;
//                        break;
//                    case Infactage:
//                        infactageConfigs.add(sqlConfig);
//                        if (sqlConfig.isDefaultServer()) infactageServer = sqlConfig;
//                        break;
//                    case Primavera:
                        primaConfigs.add(config);
//                        if (sqlConfig.isDefaultServer()) primaServer = sqlConfig;
//                        break;

            primaServer = config;
//                }
            } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Конфигурация подключения Factage");
            alert.setHeaderText("Отсутствует или повреждён файл конфигурации Factage");
            alert.setContentText("Пожалуйста, проведите настройку подключения Factage");
            alert.showAndWait();
        }
    }

    public void saveConfigs(ObservableList factage, ObservableList infactage, SqlConfig prima) {
//        factageConfigs.addAll(factage);
//        infactageConfigs.addAll(infactage);
//        primaConfigs.addAll(prima);

//        ArrayList<SqlConfig> configList = new ArrayList<>();
//        configList.addAll(factage);
//        configList.addAll(infactage);
//        configList.addAll(prima);

        Path path = Paths.get(directory);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file = new File(directory + "\\" + fileName);
        if (file.exists() && file.length() > 0) {
            file.delete();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory + "\\" + fileName))) {
            oos.writeObject(prima);
            System.out.println("File has been written");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public SqlConfig getFactageServer() {
        return factageServer;
    }

    public void setFactageServer(SqlConfig factageServer) {
        ConfigSql.factageServer = factageServer;
    }

    public SqlConfig getInfactageServer() {
        return infactageServer;
    }

    public void setInfactageServer(SqlConfig infactageServer) {
        ConfigSql.infactageServer = infactageServer;
    }

    public ArrayList<SqlConfig> getFactageConfigs() {
        return factageConfigs;
    }

    public ArrayList<SqlConfig> getPrimaConfigs() {
        return primaConfigs;
    }

    public static SqlConfig getPrimaServer() {
        return primaServer;
    }

    public ArrayList<SqlConfig> getInfactageConfigs() {
        return infactageConfigs;
    }
}