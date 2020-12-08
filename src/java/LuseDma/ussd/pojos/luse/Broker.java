package LuseDma.ussd.pojos.luse;

public class Broker {

    private String id;
    private String msisdn;
    private String brokerId;
    private String atsId;

    @Override
    public String toString() {
        return "Broker{" +
                "id='" + id + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", brokerId='" + brokerId + '\'' +
                ", atsId='" + atsId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                '}';
    }

    private String subscriberId;

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Broker(String id, String msisdn, String brokerId, String atsId, String subscriberId) {
        this.id = id;
        this.msisdn = msisdn;
        this.brokerId = brokerId;
        this.atsId = atsId;
        this.subscriberId = subscriberId;
    }

    public Broker() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getAtsId() {
        return atsId;
    }

    public void setAtsId(String atsId) {
        this.atsId = atsId;
    }
}
