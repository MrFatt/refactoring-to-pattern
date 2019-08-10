import java.time.LocalDate;
import java.util.Iterator;

import static java.time.temporal.ChronoUnit.DAYS;

public class CapitalStrategy {

    private static final long DAYS_PER_YEAR = 365;

    public double capital(Loan loan) {
        if (loan.expiry == null && loan.maturity != null)
            //for newTermLoan
            return loan.commitment * loan.duration() * riskFactorFor(loan);
        if (loan.expiry != null && loan.maturity == null) {
            if (loan.getUnusedPercentage() != 1.0)
                //for newAdvisedLine
                return loan.commitment * loan.getUnusedPercentage() * loan.duration() * riskFactorFor(loan);
            else
                //for newRevolver
                return (loan.outstandingRiskAmount() * loan.duration() * riskFactorFor(loan))
                        + (loan.unusedRiskAmount() * loan.duration() * unusedRiskFactorFor(loan));
        }
        return 0.0;
    }

    public double duration(Loan loan) {
        if (loan.expiry == null && loan.maturity != null)
            return weightedAverageDuration(loan);
        else if (loan.expiry != null && loan.maturity == null)
            return yearsTo(loan.expiry, loan);
        return 0.0;
    }

    private double riskFactorFor(Loan loan) {
        return RiskFactor.getFactors().forRating(loan.riskRating);
    }

    private double unusedRiskFactorFor(Loan loan) {
        return UnusedRiskFactors.getFactors().forRating(loan.riskRating);
    }

    private double weightedAverageDuration(Loan loan) {
        double duration = 0.0;
        double weightedAverage = 0.0;
        double sumOfPayments = 0.0;
        Iterator loanPayments = loan.payments.iterator();
        while (loanPayments.hasNext()) {
            Payment payment = (Payment) loanPayments.next();
            sumOfPayments += payment.amount;
            weightedAverage += yearsTo(payment.date, loan) * payment.amount;
        }
        if (loan.commitment != 0.0)
            duration = weightedAverage / sumOfPayments;
        return duration;
    }

    private double yearsTo(LocalDate endDate, Loan loan) {
        LocalDate beginDate = (loan.today == null ? loan.start : loan.today);
        return DAYS.between(beginDate, endDate) / DAYS_PER_YEAR;
    }
}
