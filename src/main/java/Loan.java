import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Loan {


    LocalDate expiry; //过期
    LocalDate maturity; //到期
    double commitment; //承诺费
    double outstanding; //未偿
    List<Payment> payments;
    LocalDate today;
    LocalDate start;

    private long MILLIS_PER_DAY = 86400000;
    private long DAYS_PER_YEAR = 365;
    double riskRating;
    double unusedPercentage;

    public Loan(LocalDate expiry, LocalDate maturity, double commitment, LocalDate start, double riskRating) {
        this.expiry = expiry;
        this.maturity = maturity;
        this.commitment = commitment;
        this.start = start;
        this.riskRating = riskRating;
        this.today = null;
        this.unusedPercentage = 1.0;
        payments = new ArrayList<>();
    }

    public double getUnusedPercentage() {
        return unusedPercentage;
    }

    public void setUnusedPercentage(double unusedPercentage) {
        this.unusedPercentage = unusedPercentage;
    }

    public static Loan newTermLoan(double commitment, LocalDate start, LocalDate maturity, int riskRating) {
        return new Loan(null, maturity, commitment, start, riskRating);
    }

    public static Loan newRevolver(double commitment, LocalDate start, LocalDate expiry, int riskRating) {
        return new Loan(expiry, null, commitment, start, riskRating);
    }

    public static Loan newAdvisedLine(double commitment, LocalDate start, LocalDate expiry, int riskRating) {
        if (riskRating > 3) return null;
        Loan advisedLine = new Loan(expiry, null, commitment, start, riskRating);
        advisedLine.setUnusedPercentage(0.1);
        return advisedLine;
    }


    public void payment(double amount, LocalDate paymentDate) {
        payments.add(new Payment(amount, paymentDate));
    }

    // 资本
    public double capital() {
        return new CapitalStrategy().capital(this);
    }


    //期限
    public double duration() {
        if (expiry == null && maturity != null)
            return weightedAverageDuration();
        else if (expiry != null && maturity == null)
            return yearsTo(expiry);
        return 0.0;
    }


    double outstandingRiskAmount() {
        return outstanding;
    }


    double unusedRiskAmount() {
        return (commitment - outstanding);
    }


    private double weightedAverageDuration() {
        double duration = 0.0;
        double weightedAverage = 0.0;
        double sumOfPayments = 0.0;
        Iterator loanPayments = payments.iterator();
        while (loanPayments.hasNext()) {
            Payment payment = (Payment) loanPayments.next();
            sumOfPayments += payment.amount;
            weightedAverage += yearsTo(payment.date) * payment.amount;
        }
        if (commitment != 0.0)
            duration = weightedAverage / sumOfPayments;
        return duration;
    }

    private double yearsTo(LocalDate endDate) {
        LocalDate beginDate = (today == null ? start : today);
        return DAYS.between(beginDate, endDate) / DAYS_PER_YEAR;
    }

    private double riskFactor() {
        return RiskFactor.getFactors().forRating(riskRating);
    }

    private double unusedRiskFactor() {
        return UnusedRiskFactors.getFactors().forRating(riskRating);
    }
}
