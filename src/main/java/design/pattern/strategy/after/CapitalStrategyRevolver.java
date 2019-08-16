package design.pattern.strategy.after;

public class CapitalStrategyRevolver extends CapitalStrategy {

    public double capital(Loan loan) {
        return (loan.outstandingRiskAmount() * loan.duration() * riskFactorFor(loan))
                + (loan.unusedRiskAmount() * loan.duration() * unusedRiskFactorFor(loan));
    }

    public double duration(Loan loan) {
        return yearsTo(loan.expiry, loan);
    }
}
