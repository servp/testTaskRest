package testtask.entity;

import java.math.BigDecimal;

public class Statistic {
    private int count;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal sum;


    public Statistic(){}
    public Statistic(int count, BigDecimal minAmount, BigDecimal maxAmount, BigDecimal sum) {
        this.count = count;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
