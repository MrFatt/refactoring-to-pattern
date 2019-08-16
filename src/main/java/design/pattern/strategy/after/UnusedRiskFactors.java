package design.pattern.strategy.after;

public class UnusedRiskFactors {
    private UnusedRiskFactors() {
    }

    public static UnusedRiskFactors getFactors() {
        return new UnusedRiskFactors();
    }

    public double forRating(double riskRating) {
        return 0.01;
    }
}
