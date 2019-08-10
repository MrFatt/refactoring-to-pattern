public class CapitalStrategyAdvisedLine extends CapitalStrategy {
    public double capital(Loan loan) {
        return loan.commitment * loan.getUnusedPercentage() * loan.duration() * riskFactorFor(loan);
    }

    public double duration(Loan loan) {
        return yearsTo(loan.expiry, loan);
    }
}
