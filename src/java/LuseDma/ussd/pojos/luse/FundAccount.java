package LuseDma.ussd.pojos.luse;

public class FundAccount {

    private String subscriberId;
    private String amount;
    private String msisdn;

    public FundAccount(String subscriberId, String amount, String msisdn) {
        this.subscriberId = subscriberId;
        this.amount = amount;
        this.msisdn = msisdn;
    }

    public FundAccount() {
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}
