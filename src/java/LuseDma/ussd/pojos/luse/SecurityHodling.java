package LuseDma.ussd.pojos.luse;

public class SecurityHodling {

    private String subscriberId;
    private String csdId;
    private String securityCode;
    private String securityName;
    private String shortLongIndicator;
    private String holdingsBalance;
    private String availableBalance;

    @Override
    public String toString() {
        return "SecurityHodlings{" +
                "subscriberId='" + subscriberId + '\'' +
                ", csdId='" + csdId + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", securityName='" + securityName + '\'' +
                ", shortLongIndicator='" + shortLongIndicator + '\'' +
                ", holdingsBalance='" + holdingsBalance + '\'' +
                ", availableBalance='" + availableBalance + '\'' +
                '}';
    }

    public SecurityHodling() {
    }

    public SecurityHodling(String subscriberId, String csdId, String securityCode, String securityName, String shortLongIndicator, String holdingsBalance, String availableBalance) {
        this.subscriberId = subscriberId;
        this.csdId = csdId;
        this.securityCode = securityCode;
        this.securityName = securityName;
        this.shortLongIndicator = shortLongIndicator;
        this.holdingsBalance = holdingsBalance;
        this.availableBalance = availableBalance;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getCsdId() {
        return csdId;
    }

    public void setCsdId(String csdId) {
        this.csdId = csdId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getShortLongIndicator() {
        return shortLongIndicator;
    }

    public void setShortLongIndicator(String shortLongIndicator) {
        this.shortLongIndicator = shortLongIndicator;
    }

    public String getHoldingsBalance() {
        return holdingsBalance;
    }

    public void setHoldingsBalance(String holdingsBalance) {
        this.holdingsBalance = holdingsBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }
}
