package LuseDma.ussd.pojos.luse;

public class SecurityOrder {

    private String id;
    private String subscriberId;
    private String securityType;
    private String securityAtsId;
    private String securitySymbol;
    private String subscriberAtsId;
    private String brokerAtsId;
    private String volume;
    private String price;
    private String buyOrSell;
    private String orderType;
    private String orderCapacity;
    private String comment;

    public SecurityOrder(String id, String subscriberId, String securityType, String securityAtsId, String securitySymbol, String subscriberAtsId, String brokerAtsId, String volume, String price, String buyOrSell, String orderType, String orderCapacity, String comment, String msisdn, String maturityDate) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.securityType = securityType;
        this.securityAtsId = securityAtsId;
        this.securitySymbol = securitySymbol;
        this.subscriberAtsId = subscriberAtsId;
        this.brokerAtsId = brokerAtsId;
        this.volume = volume;
        this.price = price;
        this.buyOrSell = buyOrSell;
        this.orderType = orderType;
        this.orderCapacity = orderCapacity;
        this.comment = comment;
        this.msisdn = msisdn;
        this.maturityDate = maturityDate;
    }

    private String msisdn;

    @Override
    public String toString() {
        return "SecurityOrder{" +
                "id='" + id + '\'' +
                "msisdn='" + msisdn + '\'' +
                "subscriberId='" + subscriberId + '\'' +
                ", securityType='" + securityType + '\'' +
                ", securityAtsId='" + securityAtsId + '\'' +
                ", securitySymbol='" + securitySymbol + '\'' +
                ", maturityDate='" + maturityDate + '\'' +
                ", subscriberAtsId='" + subscriberAtsId + '\'' +
                ", brokerAtsId='" + brokerAtsId + '\'' +
                ", volume='" + volume + '\'' +
                ", price='" + price + '\'' +
                ", buyOrSell='" + buyOrSell + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderCapacity='" + orderCapacity + '\'' +
                ", comment='" + comment + '\'' +
                '}';
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

    public SecurityOrder() {
    }

    private String maturityDate;

    public String getId() {
        return id;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityAtsId() {
        return securityAtsId;
    }

    public void setSecurityAtsId(String securityAtsId) {
        this.securityAtsId = securityAtsId;
    }

    public String getSecuritySymbol() {
        return securitySymbol;
    }

    public void setSecuritySymbol(String securitySymbol) {
        this.securitySymbol = securitySymbol;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getSubscriberAtsId() {
        return subscriberAtsId;
    }

    public void setSubscriberAtsId(String subscriberAtsId) {
        this.subscriberAtsId = subscriberAtsId;
    }

    public String getBrokerAtsId() {
        return brokerAtsId;
    }

    public void setBrokerAtsId(String brokerAtsId) {
        this.brokerAtsId = brokerAtsId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderCapacity() {
        return orderCapacity;
    }

    public void setOrderCapacity(String orderCapacity) {
        this.orderCapacity = orderCapacity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



}
