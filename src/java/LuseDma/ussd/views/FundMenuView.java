package LuseDma.ussd.views;

public class FundMenuView {
    private String newLine = String.format("%n", new Object[0]);
    public static final String FORM_NAME = "fundAccount_form";

    public String amountView(boolean isError){
        String view = "";

        if(isError){
            view = view + "Invalid input"+this.newLine;
        }

        view = view + "Fund account:"+this.newLine;
        view = view + "Enter amount"+this.newLine;

        return view;
    }

    public String fundDetailsView(String amount){
        String view = "";
        view = view + "Fund Account Details"+this.newLine;
        view = view + "Amount entered ZMW: "+Double.parseDouble(amount)+this.newLine;

        return view;
    }

    public String fundResponse(){
        String view = "";
        view = view + "request process complete."+this.newLine;

        return view;
    }
}
