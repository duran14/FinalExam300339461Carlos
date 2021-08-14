public class Savings {

    private String cusNum;
    private String cusName;
    private String initialDeposit;
    private String numYears;
    private String typeSavings;

    public String getCusNum() {
        return cusNum;
    }

    public void setCusNum(String cusNum) {
        this.cusNum = cusNum;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(String initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public String getNumYears() {
        return numYears;
    }

    public void setNumYears(String numYears) {
        this.numYears = numYears;
    }

    public String getTypeSavings() {
        return typeSavings;
    }

    public void setTypeSavings(String typeSavings) {
        this.typeSavings = typeSavings;
    }

    public Savings(String cusNum, String cusName, String initialDeposit, String numYears, String typeSavings) {
        this.cusNum = cusNum;
        this.cusName = cusName;
        this.initialDeposit = initialDeposit;
        this.numYears = numYears;
        this.typeSavings = typeSavings;
    }
}
