package prototype.config;

import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;

public class SqlConfig implements Serializable {

    private String configName;
    private String serverName;
    private String port;
    private String dbName;
    private String user;
    private String password;

    private MsSqlConnect.DB type;

    private boolean defaultServer;

    public SqlConfig(String configName, String serverName, String port, String dbName, String user, String password, MsSqlConnect.DB type) {
        this.configName = configName;
        this.serverName =  Base64.encodeBase64String(serverName.getBytes());
        this.port = port;
        this.dbName = dbName;
        this.user = Base64.encodeBase64String(user.getBytes());
        this.password = Base64.encodeBase64String(password.getBytes());
        this.type = type;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getServerName() {
        return new String(Base64.decodeBase64(serverName));
    }

    public void setServerName(String serverName) {
        this.serverName = Base64.encodeBase64String(serverName.getBytes());
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return new String(Base64.decodeBase64(user));
    }

    public void setUser(String user) {
        this.user = Base64.encodeBase64String(user.getBytes());
    }

    public String getPassword() {
        return new String(Base64.decodeBase64(password));
    }

    public void setPassword(String password) {
        this.password = Base64.encodeBase64String(password.getBytes());
    }

    public boolean isDefaultServer() {
        return defaultServer;
    }

    public void setDefaultServer(boolean defaultServer) {
        this.defaultServer = defaultServer;
    }

    public MsSqlConnect.DB getType() {
        return type;
    }

    public void setType(MsSqlConnect.DB type) {
        this.type = type;
    }

    public String config() {
        StringBuilder configString = new StringBuilder();
        configString.append(new String(Base64.decodeBase64(serverName)));

        configString.append(":");
        configString.append(port);

        String userName = new String(Base64.decodeBase64(user));
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
            configString.append(dbName);
        } else {
            configString.append(";database=");
            configString.append(dbName);
        }



        configString.append(";user=");
        configString.append(userName);

        configString.append(";password=");
        configString.append(new String(Base64.decodeBase64(password)));
        configString.append(";");

        return configString.toString();
    }

    @Override
    public String toString() {
        return configName;
    }
}
