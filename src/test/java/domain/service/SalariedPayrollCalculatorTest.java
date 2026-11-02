package domain.service;

import domain.vo.Money;
import domain.vo.Seniority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SalariedPayrollCalculatorTest {

    private static final Money SALARY = new Money("1750905");

    private SalariedPayrollCalculator calculator(int years) {
        return new SalariedPayrollCalculator(SALARY, new Seniority((short) years));
    }

    @Test
    void elSalarioBrutoEsElSalarioFijoMensual() {
        assertEquals(SALARY, calculator(1).calculateGrossSalary());
    }

    @Test
    void conMasDe5AniosRecibeBonoDel10PorCiento() {
        assertEquals(new Money("175090.50"), calculator(6).calculateBonuses());
    }

    @Test
    void conExactamente5AniosNoRecibeBono() {
        assertEquals(Money.ZERO, calculator(5).calculateBonuses());
    }

    @Test
    void recibeBonoDeAlimentacion() {
        assertEquals(new Money("1000000"), calculator(1).calculateBenefits());
    }
}