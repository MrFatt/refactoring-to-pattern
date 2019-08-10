import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {


    LocalDate expiry; //过期
    LocalDate maturity; //到期
    double commitment; //承诺费
    double outstanding; //未偿
    List<Payment> payments;
    LocalDate today;
    LocalDate start;

    double riskRating;
    double unusedPercentage;

    private CapitalStrategy capitalStrategy;

    public Loan(LocalDate expiry, LocalDate maturity, double commitment, LocalDate start, double riskRating, CapitalStrategy capitalStrategy) {
        this.capitalStrategy = capitalStrategy;
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
        return new Loan(null, maturity, commitment, start, riskRating, new CapitalStrategyTermLoan());
    }

    public static Loan newRevolver(double commitment, LocalDate start, LocalDate expiry, int riskRating) {
        return new Loan(expiry, null, commitment, start, riskRating, new CapitalStrategyRevolver());
    }

    public static Loan newAdvisedLine(double commitment, LocalDate start, LocalDate expiry, int riskRating) {
        if (riskRating > 3) return null;
        Loan advisedLine = new Loan(expiry, null, commitment, start, riskRating, new CapitalStrategyAdvisedLine());
        advisedLine.setUnusedPercentage(0.1);
        return advisedLine;
    }


    public void payment(double amount, LocalDate paymentDate) {
        payments.add(new Payment(amount, paymentDate));
    }

    // 资本
    public double capital() {
        return capitalStrategy.capital(this);
    }


    //期限
    public double duration() {
        return capitalStrategy.duration(this);
    }


    double outstandingRiskAmount() {
        return outstanding;
    }


    double unusedRiskAmount() {
        return (commitment - outstanding);
    }
}
