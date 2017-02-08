package xyz.thelostsoul.bean;

public class DBAcct {
    private String dbAcctCode;

    private String username;

    private String password;

    private String host;

    private Integer port;

    private String sid;

    private Short defaultConnMin;

    private Short defaultConnMax;

    private String state;

    private String remarks;

    public String getDbAcctCode() {
        return dbAcctCode;
    }

    public void setDbAcctCode(String dbAcctCode) {
        this.dbAcctCode = dbAcctCode == null ? null : dbAcctCode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public Short getDefaultConnMin() {
        return defaultConnMin;
    }

    public void setDefaultConnMin(Short defaultConnMin) {
        this.defaultConnMin = defaultConnMin;
    }

    public Short getDefaultConnMax() {
        return defaultConnMax;
    }

    public void setDefaultConnMax(Short defaultConnMax) {
        this.defaultConnMax = defaultConnMax;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}