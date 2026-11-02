package domain.service;

import domain.vo.HoursWorked;
import domain.vo.Money;
import domain.vo.Seniority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HourlyPayrollCalculatorTest {

    private static final Money HOURLY_RATE = new Money("8338");

    private HourlyPayrollCalculator calculator(int hours, int years, boolean acceptsFund) {
        return new HourlyPayrollCalculator(
                HOURLY_RATE, new HoursWorked(hours), new Seniority((short) years), acceptsFund);
    }

    @Test
    void sinHorasExtraPagaSoloLasHorasNormales() {
        // 30 h * 8.338 = 250.140
        assertEquals(new Money("250140"), calculator(30, 0, false).calculateGrossSalary());
    }

    @Test
    void lasHorasExtraSePaganA1Punto5VecesLaTarifa() {
        // 40 h * 8.338 = 333.520 + 5 h * 8.338 * 1,5 = 62.535  ->  396.055
        assertEquals(new Money("396055"), calculator(45, 0, false).calculateGrossSalary());
    }

    @Test
    void exactamente40HorasNoGeneraHorasExtra() {
        assertEquals(new Money("333520"), calculator(40, 0, false).calculateGrossSalary());
    }

    @Test
    void noRecibeBonos() {
        assertEquals(Money.ZERO, calculator(45, 10, true).calculateBonuses());
    }

    @Test
    void conMasDeUnAnioYAceptaYRecibeFondoDeAhorroDel2Porciento() {
        // 2 % de 396.055 = 7.921,10
        assertEquals(new Money("7921.10"), calculator(45, 2, true).calculateBenefits());
    }

    @Test
    void conUnAnioExactoNoRecibeFondoDeAhorro() {
        assertEquals(Money.ZERO, calculator(45, 1, true).calculateBenefits());
    }

    @Test
    void sinAceptarElFondoNoLoRecibe() {
        assertEquals(Money.ZERO, calculator(45, 5, false).calculateBenefits());
    }
}