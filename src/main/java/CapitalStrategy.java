public class CapitalStrategy {

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

    private double riskFactorFor(Loan loan) {
        return RiskFactor.getFactors().forRating(loan.riskRating);
    }

    private double unusedRiskFactorFor(Loan loan) {
        return UnusedRiskFactors.getFactors().forRating(loan.riskRating);
    }
}
