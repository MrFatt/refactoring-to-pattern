package design.pattern.strategy.after;

import java.util.Iterator;

public class CapitalStrategyTermLoan extends CapitalStrategy {
    public double capital(Loan loan) {
        return loan.commitment * duration(loan) * riskFactorFor(loan);
    }

    public double duration(Loan loan) {
        return weightedAverageDuration(loan);
    }

    private double weightedAverageDuration(Loan loan) {
        double duration = 0.0;
        double weightedAverage = 0.0;
        double sumOfPayments = 0.0;
        Iterator loanPayments = loan.payments.iterator();
        while (loanPayments.hasNext()) {
            Payment payment = (Payment)loanPayments.next();
            sumOfPayments += payment.amount;
            weightedAverage += yearsTo(payment.date, loan) * payment.amount;
        }
        if (loan.commitment != 0.0)
            duration = weightedAverage / sumOfPayments;
        return duration;
    }
}
