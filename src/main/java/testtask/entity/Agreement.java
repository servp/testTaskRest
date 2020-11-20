package testtask.entity;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


public class Agreement {

    private Integer agreementId;

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer productId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Date startDate;

    private Date timeStamp;


    public Agreement() {

       this.timeStamp = new Date();
    }

    public Agreement(Integer clientId, Integer productId, BigDecimal amount, Date startDate) {
        this.timeStamp = new Date();
        this.clientId = clientId;
        this.productId = productId;
        this.amount = amount;
        this.startDate = startDate;
        this.timeStamp = new Date();
    }

    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement agreement = (Agreement) o;
        return agreementId.equals(agreement.agreementId) &&
                clientId.equals(agreement.clientId) &&
                productId.equals(agreement.productId) &&
                amount.equals(agreement.amount) &&
                startDate.equals(agreement.startDate) &&
                timeStamp.equals(agreement.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agreementId, clientId, productId, amount, startDate, timeStamp);
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "agreementId=" + agreementId +
                ", clientId=" + clientId +
                ", productId=" + productId +
                ", amount=" + amount +
                ", startDate=" + startDate +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
