package design.pattern.strategy.after;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public abstract class CapitalStrategy {

    private static final long DAYS_PER_YEAR = 365;

    public double capital(Loan loan) {
        return 0.0;
    }

    public double duration(Loan loan) {
        return 0.0;
    }

    double riskFactorFor(Loan loan) {
        return RiskFactor.getFactors().forRating(loan.riskRating);
    }

    double unusedRiskFactorFor(Loan loan) {
        return UnusedRiskFactors.getFactors().forRating(loan.riskRating);
    }

    double yearsTo(LocalDate endDate, Loan loan) {
        LocalDate beginDate = (loan.today == null ? loan.start : loan.today);
        return DAYS.between(beginDate, endDate) / DAYS_PER_YEAR;
    }
}
