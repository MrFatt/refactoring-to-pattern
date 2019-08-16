import design.pattern.strategy.before.Loan;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CapitalCalculationTests {

    private static final double TWO_DIGIT_PRECISION = 0.01;
    private static final double LOAN_AMOUNT = 3500;
    private static final int HIGH_RISK_RATING = 1;

    private LocalDate november(int day, int year) {
        return LocalDate.of(year, 11, day);
    }

    @Test
    public void testTermLoanSamePayments() {
        LocalDate start = november(20, 2003);
        LocalDate maturity = november(20, 2006);
        Loan termLoan = Loan.newTermLoan(LOAN_AMOUNT, start, maturity, HIGH_RISK_RATING);
        termLoan.payment(1000.00, november(20, 2004));
        termLoan.payment(1000.00, november(20, 2005));
        termLoan.payment(1000.00, november(20, 2006));
        assertEquals("duration", 2.0, termLoan.duration(), TWO_DIGIT_PRECISION);
        assertEquals("capital", 210.00, termLoan.capital(), TWO_DIGIT_PRECISION);
    }

    @Test
    public void testTermLoanDifferentPayments() {
        LocalDate start = november(20, 2003);
        LocalDate maturity = november(20, 2006);
        Loan termLoan = Loan.newTermLoan(LOAN_AMOUNT, start, maturity, HIGH_RISK_RATING);
        termLoan.payment(1000.00, november(20, 2004));
        termLoan.payment(2000.00, november(20, 2005));
        termLoan.payment(3000.00, november(20, 2006));
        assertEquals("duration", 2.33, termLoan.duration(), TWO_DIGIT_PRECISION);
        assertEquals("capital", 245.00, termLoan.capital(), TWO_DIGIT_PRECISION);
    }

    @Test
    public void testRevolverLoanSamePayments() {
        LocalDate start = november(20, 2003);
        LocalDate expiry = november(20, 2006);
        Loan termLoan = Loan.newRevolver(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING);
        termLoan.payment(1000.00, november(20, 2004));
        termLoan.payment(1000.00, november(20, 2005));
        termLoan.payment(1000.00, november(20, 2006));
        assertEquals("duration", 3, termLoan.duration(), TWO_DIGIT_PRECISION);
        assertEquals("capital", 105.00, termLoan.capital(), TWO_DIGIT_PRECISION);
    }

    @Test
    public void testRevolverLoanDifferentPayments() {
        LocalDate start = november(20, 2003);
        LocalDate expiry = november(20, 2006);
        Loan revolverLoan = Loan.newRevolver(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING);
        revolverLoan.payment(1000.00, november(20, 2004));
        revolverLoan.payment(2000.00, november(20, 2005));
        revolverLoan.payment(3000.00, november(20, 2006));
        assertEquals("duration", 3, revolverLoan.duration(), TWO_DIGIT_PRECISION);
        assertEquals("capital", 105.00, revolverLoan.capital(), TWO_DIGIT_PRECISION);
    }

    @Test
    public void testAdvisedLineSamePayments() {
        LocalDate start = november(20, 2003);
        LocalDate expiry = november(20, 2006);
        Loan advisedLine = Loan.newAdvisedLine(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING);
        advisedLine.payment(1000.00, november(20, 2004));
        advisedLine.payment(1000.00, november(20, 2005));
        advisedLine.payment(1000.00, november(20, 2006));
        assertEquals("duration", 3, advisedLine.duration(), TWO_DIGIT_PRECISION);
        assertEquals("capital", 31.50, advisedLine.capital(), TWO_DIGIT_PRECISION);
    }

    @Test
    public void testAdvisedLineDifferentPayments() {
        LocalDate start = november(20, 2003);
        LocalDate expiry = november(20, 2006);
        Loan advisedLine = Loan.newAdvisedLine(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING);
        advisedLine.payment(1000.00, november(20, 2004));
        advisedLine.payment(2000.00, november(20, 2005));
        advisedLine.payment(3000.00, november(20, 2006));
        assertEquals("duration", 3, advisedLine.duration(), TWO_DIGIT_PRECISION);
        assertEquals("capital", 31.50, advisedLine.capital(), TWO_DIGIT_PRECISION);
    }

}
