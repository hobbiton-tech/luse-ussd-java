/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LuseDma.ussd.pojos.kyc;
/**
 *
 * @author Chisha
 */
public class IncomeDetails
{
    private String monthly_income_total;

    private String maximum_offer;

    private String source_of_income;

    private String employer;

    private String currency;

    private String source_of_income_other;

    public String getMonthlyIncomeTotal ()
    {
        return monthly_income_total;
    }

    public void setMonthlyIncomeTotal (String monthly_income_total)
    {
        this.monthly_income_total = monthly_income_total;
    }

    public String getMaximumOffer ()
    {
        return maximum_offer;
    }

    public void setMaximumOffer (String maximum_offer)
    {
        this.maximum_offer = maximum_offer;
    }

    public String getSourceOfIncome ()
    {
        return source_of_income;
    }

    public void setSourceOfIncome (String source_of_income)
    {
        this.source_of_income = source_of_income;
    }

    public String getEmployer ()
    {
        return employer;
    }

    public void setEmployer (String employer)
    {
        this.employer = employer;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getSourceOfIncome_other ()
    {
        return source_of_income_other;
    }

    public void setSourceOfIncomeOther (String source_of_income_other)
    {
        this.source_of_income_other = source_of_income_other;
    }
}
