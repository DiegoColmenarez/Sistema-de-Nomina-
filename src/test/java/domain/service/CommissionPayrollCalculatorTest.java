package domain.service;

import domain.vo.Money;
import domain.vo.Sales;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommissionPayrollCalculatorTest {

    private static final Money BASE_SALARY = new Money("1750905");

    private CommissionPayrollCalculator calculator(String sales) {
        return new CommissionPayrollCalculator(BASE_SALARY, new Sales(sales));
    }

    @Test
    void conVentasPorDebajoDelUmbralSoloRecibeElSalarioBase() {
        assertEquals(BASE_SALARY, calculator("10000000").calculateGrossSalary());
    }

    @Test
    void conVentasSobreElUmbralRecibeComisionDel3PorCientoDeLaVentaTotal() {
        // 3 % de 30.000.000 = 900.000  ->  1.750.905 + 900.000
        assertEquals(new Money("2650905"), calculator("30000000").calculateGrossSalary());
    }

    @Test
    void noRecibeBonoAdicional() {
        assertEquals(Money.ZERO, calculator("30000000").calculateBonuses());
    }

    @Test
    void recibeBonoDeAlimentacion() {
        assertEquals(new Money("1000000"), calculator("0").calculateBenefits());
    }
}