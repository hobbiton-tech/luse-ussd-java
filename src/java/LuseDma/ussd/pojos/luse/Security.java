package LuseDma.ussd.pojos.luse;

public class Security {

    private String id;
    private String csdId;
    private String atsId;
    private String securityType;
    private String securityCode;
    private String name;
    private String description;
    private String marketCap;
    private String changeInPrice;
    private String closingPrice;
    private String settlementPrice;
    private String openInterest;
    private String symbol;
    private String currency;
    private String issueDate;
    private String maturityDate;
    private String couponRate;

    public Security() {
    }

    public Security(String id, String csdId, String atsId, String securityType, String securityCode, String name, String description, String marketCap, String changeInPrice, String closingPrice, String settlementPrice, String openInterest, String symbol, String currency, String issueDate, String maturityDate, String couponRate) {
        this.id = id;
        this.csdId = csdId;
        this.atsId = atsId;
        this.securityType = securityType;
        this.securityCode = securityCode;
        this.name = name;
        this.description = description;
        this.marketCap = marketCap;
        this.changeInPrice = changeInPrice;
        this.closingPrice = closingPrice;
        this.settlementPrice = settlementPrice;
        this.openInterest = openInterest;
        this.symbol = symbol;
        this.currency = currency;
        this.issueDate = issueDate;
        this.maturityDate = maturityDate;
        this.couponRate = couponRate;
    }

    public String getId() {
        return id;
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

    public String getAtsId() {
        return atsId;
    }

    public void setAtsId(String atsId) {
        this.atsId = atsId;
    }

    public String getSecurityType() {
        return securityType;
    }


    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getChangeInPrice() {
        return changeInPrice;
    }

    public void setChangeInPrice(String changeInPrice) {
        this.changeInPrice = changeInPrice;
    }

    public String getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(String closingPrice) {
        this.closingPrice = closingPrice;
    }

    public String getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(String settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(String openInterest) {
        this.openInterest = openInterest;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(String couponRate) {
        this.couponRate = couponRate;
    }

    @Override
    public String toString() {
        return "Security{" +
                "id='" + id + '\'' +
                ", csdId='" + csdId + '\'' +
                ", atsId='" + atsId + '\'' +
                ", securityType='" + securityType + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", marketCap=" + marketCap +
                ", changeInPrice=" + changeInPrice +
                ", closingPrice=" + closingPrice +
                ", settlementPrice=" + settlementPrice +
                ", openInterest=" + openInterest +
                ", symbol='" + symbol + '\'' +
                ", currency='" + currency + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", maturityDate='" + maturityDate + '\'' +
                ", couponRate=" + couponRate +
                '}';
    }
}
