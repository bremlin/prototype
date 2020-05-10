package prototype.primavera;

import com.primavera.PrimaveraException;
import com.primavera.ServerException;
import com.primavera.integration.client.ClientException;
import com.primavera.integration.client.RMIURL;
import com.primavera.integration.client.Session;
import com.primavera.integration.common.DatabaseInstance;
import com.primavera.integration.network.NetworkException;
import prototype.config.ConfigSql;
import javafx.scene.control.Alert;

public class dLogin {

    //~ Статические поля класса ----------------------------------------------------------------------------------------

    private static dLogin INSTANCE;

    public static final String  REMOTE_MODE = "Remote";
    public static final String  LOCAL_MODE = "Local";

    //~ Поля подключения -----------------------------------------------------------------------------------------------

    public static Session       session;
    public static Session       sessionAdmin;

    public String               sCallingMode;
    public String               sHost;
    public int                  iPort;
    public int                  iRMIServiceMode;
    public String               sUserName;
    public String               sPassword;
    public String               sDatabaseId;

    private int                 count;

    private dLogin() {
        this.count = 0;
//        System.setProperty("primavera.bootstrap.home", "D:\\P6IntegrationAPI");
//        System.setProperty("primavera.bootstrap.home", "..\\");

        if (session == null) {
            boolean local;
            try {
                local = Session.getDatabaseInstances(null).length > 0;
            } catch (ServerException | NetworkException | ClientException e) {
                local = false;
            }
            if (local) {
                this.sCallingMode = LOCAL_MODE;
            } else {
                this.sCallingMode = REMOTE_MODE;
            }
        }
    }

    private String getURL() {
        String sRmiUrl = null;
        if (REMOTE_MODE.equalsIgnoreCase(this.sCallingMode)) {
            ConnectionInfo connectionInfo = new ConnectionInfo();
            connectionInfo.sHost = ConfigSql.primaServer.getServerName();
            connectionInfo.iPort = Integer.parseInt(ConfigSql.primaServer.getPort());
            connectionInfo.sCallingMode = REMOTE_MODE;
            connectionInfo.iRMIServiceMode = 1;
            setConnectionInfo(connectionInfo);
            sRmiUrl = RMIURL.getRmiUrl(this.iRMIServiceMode, this.sHost, this.iPort);
        }
        return sRmiUrl;
    }

    public DatabaseInstance[] getDatabaseInstances(boolean ignoreException) {
        try {
            return Session.getDatabaseInstances(getURL());
        } catch (ServerException e) {
            if (count == 1 || !ignoreException) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Подключение к серверу Primavera");
                alert.setHeaderText("Невозможно установить соединения с сервером");
                alert.setContentText("Сервер отказывает в соединении. Пожалуйста, обратитесь к своему системному администратору");
                alert.showAndWait();
                count = 0;
            } else {
                count++;
            }
        } catch (NetworkException e) {
            if (count == 1 || !ignoreException) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Подключение к серверу Primavera");
                alert.setHeaderText("Невозможно установить соединения с сервером");
                alert.setContentText("Отсутствует доступ к серверу Primavera или не запущена web-служба Primavera");
                alert.showAndWait();
                count = 0;
            } else {
                count++;
            }
        } catch (ClientException e) {
            if (count == 1 || !ignoreException) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Подключение к серверу Primavera");
                alert.setHeaderText("Невозможно установить соединения с сервером");
                alert.setContentText("Пожалуйста, обратитесь к разработчику");
                alert.showAndWait();
                count = 0;
            } else {
                count++;
            }
        }
        return null;
    }

    public void login() throws PrimaveraException {
        if (!REMOTE_MODE.equalsIgnoreCase(this.sCallingMode)) {
            session = Session.login(null, this.sDatabaseId, this.sUserName, this.sPassword);
            if (ConfigSql.primaServer.getUser() != null) {
                sessionAdmin = session;
//                sessionAdmin = Session.login(null, this.sDatabaseId, FactageConfigSql.primaServer.getUser(), FactageConfigSql.primaServer.getPassword());
            } else {
                //todo алерт?
                System.out.println("Отсутствует учётная запись суперпользователя");
                sessionAdmin = session;
            }
        } else {
            session = Session.login(getURL(), this.sDatabaseId, this.sUserName, this.sPassword);
            if (ConfigSql.primaServer.getUser() != null) {
//                sessionAdmin = Session.login(getURL(), sDatabaseId,  FactageConfigSql.primaServer.getUser(), FactageConfigSql.primaServer.getPassword());
                sessionAdmin = session;
            } else {
                //todo алерт?
                System.out.println("Отсутствует учётная запись суперпользователя");
                sessionAdmin = session;
            }
        }
    }

    public void logout() {
        if (session != null) {
            session.logout();
            sessionAdmin.logout();
        }
    }

    public void setConnectionInfo (ConnectionInfo connInfo) {
        if (connInfo != null) {
            this.sCallingMode = connInfo.sCallingMode;
            this.sHost = connInfo.sHost;
            this.iPort = connInfo.iPort;
            this.iRMIServiceMode = connInfo.iRMIServiceMode;
        }
    }

    public void setLoginInfo (LoginInfo logininfo) {
        this.sUserName = logininfo.sUserName;
        this.sPassword = logininfo.sPassword;
        this.sDatabaseId = logininfo.sDatabaseId;
    }

    public static class ConnectionInfo {
        public String sCallingMode;
        public String sHost;
        public int iPort;
        public int iRMIServiceMode;
    }

    public static class LoginInfo {
        public String sUserName;
        public String sPassword;
        public String sDatabaseId;
    }

    public static prototype.primavera.dLogin getInstance() {
        if (INSTANCE == null) INSTANCE = new prototype.primavera.dLogin();
        return INSTANCE;
    }
}