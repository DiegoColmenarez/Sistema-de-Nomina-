package application.usecase;

import application.dto.CommissionInput;
import application.dto.HourlyInput;
import application.dto.PayrollResult;
import application.dto.SalariedInput;
import application.dto.TemporaryInput;
import application.factory.PayrollCalculatorFactory;
import domain.exceptions.InvalidHorasTrabajadasException;
import domain.exceptions.InvalidSalesException;
import domain.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratePayrollServiceTest {

    private final GeneratePayrollUseCase useCase = new GeneratePayrollService(new PayrollCalculatorFactory());

    @Test
    void asalariadoConMasDe5AniosRecibeBonoYBonoDeAlimentacion() {
        PayrollResult result = useCase.execute(new SalariedInput("Ana", 6));

        assertEquals("Ana", result.employeeName());
        assertEquals(new Money("1750905"), result.gross());
        assertEquals(new Money("175090.50"), result.bonuses());
        assertEquals(new Money("1000000"), result.benefits());
        assertEquals(new Money("70036.20"), result.deductions());
        assertEquals(new Money("2855959.30"), result.net());
    }

    @Test
    void asalariadoConExactamente5AniosNoRecibeBono() {
        PayrollResult result = useCase.execute(new SalariedInput("Ana", 5));

        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(new Money("2680868.80"), result.net());
    }

    @Test
    void empleadoPorHorasConHorasExtraCobraLasExtrasA1Punto5() {
        PayrollResult result = useCase.execute(new HourlyInput("Luis", 45, 0, false));

        assertEquals(new Money("396055"), result.gross());
        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(Money.ZERO, result.benefits());
        assertEquals(new Money("15842.20"), result.deductions());
        assertEquals(new Money("380212.80"), result.net());
    }

    @Test
    void empleadoPorHorasConMasDeUnAnioYFondoRecibeAhorroDel2Porciento() {
        PayrollResult result = useCase.execute(new HourlyInput("Luis", 45, 2, true));

        assertEquals(new Money("7921.10"), result.benefits());
        assertEquals(new Money("388133.90"), result.net());
    }

    @Test
    void empleadoPorComisionConVentasAltasRecibeComisionDel3PorCiento() {
        PayrollResult result = useCase.execute(new CommissionInput("Marta", new BigDecimal("30000000")));

        assertEquals(new Money("2650905"), result.gross());
        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(new Money("1000000"), result.benefits());
        assertEquals(new Money("106036.20"), result.deductions());
        assertEquals(new Money("3544868.80"), result.net());
    }

    @Test
    void empleadoPorComisionConVentasBajasSoloRecibeSalarioBase() {
        PayrollResult result = useCase.execute(new CommissionInput("Marta", new BigDecimal("10000000")));

        assertEquals(new Money("1750905"), result.gross());
        assertEquals(new Money("2680868.80"), result.net());
    }

    @Test
    void empleadoTemporalNoRecibeBonosNiBeneficios() {
        PayrollResult result = useCase.execute(new TemporaryInput("Carlos"));

        assertEquals(new Money("1750905"), result.gross());
        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(Money.ZERO, result.benefits());
        assertEquals(new Money("1680868.80"), result.net());
    }

    @Test
    void conHorasNegativasLanzaExcepcionDeDominio() {
        assertThrows(InvalidHorasTrabajadasException.class,
                () -> useCase.execute(new HourlyInput("Luis", -5, 0, false)));
    }

    @Test
    void conVentasNegativasLanzaExcepcionDeDominio() {
        assertThrows(InvalidSalesException.class,
                () -> useCase.execute(new CommissionInput("Marta", new BigDecimal("-1"))));
    }
}