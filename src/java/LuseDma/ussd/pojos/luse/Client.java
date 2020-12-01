package LuseDma.ussd.pojos.luse;

public class Client {

    private String id;
    private String msisdn;
    private String csdId;
    private String accessToken;
    private String walletBalance;
    private String fullname;
    private String atsAccountsBrokerId;
    private String atsAccountsAtsId;

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getId() {
        return id;
    }

    public Client() {
    }

    public Client(String id, String msisdn, String csdId, String accessToken, String walletBalance, String fullname, String atsAccountsBrokerId, String atsAccountsAtsId) {
        this.id = id;
        this.msisdn = msisdn;
        this.csdId = csdId;
        this.accessToken = accessToken;
        this.walletBalance = walletBalance;
        this.fullname = fullname;
        this.atsAccountsBrokerId = atsAccountsBrokerId;
        this.atsAccountsAtsId = atsAccountsAtsId;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", csdId='" + csdId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", walletBalance='" + walletBalance + '\'' +
                ", fullname='" + fullname + '\'' +
                ", atsAccountsBrokerId='" + atsAccountsBrokerId + '\'' +
                ", atsAccountsAtsId='" + atsAccountsAtsId + '\'' +
                '}';
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCsdId() {
        return csdId;
    }

    public void setCsdId(String csdId) {
        this.csdId = csdId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAtsAccountsBrokerId() {
        return atsAccountsBrokerId;
    }

    public void setAtsAccountsBrokerId(String atsAccountsBrokerId) {
        this.atsAccountsBrokerId = atsAccountsBrokerId;
    }

    public String getAtsAccountsAtsId() {
        return atsAccountsAtsId;
    }

    public void setAtsAccountsAtsId(String atsAccountsAtsId) {
        this.atsAccountsAtsId = atsAccountsAtsId;
    }

}
