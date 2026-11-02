package domain.vo;

import domain.exceptions.InvalidHorasTrabajadasException;
import domain.exceptions.InvalidPayrollException;
import domain.exceptions.InvalidSalesException;
import domain.exceptions.InvalidSeniorityException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessValidationsTest {

    @Test
    void lasHorasTrabajadasNoPuedenSerNegativas() {
        assertThrows(InvalidHorasTrabajadasException.class, () -> new HoursWorked(-1));
    }

    @Test
    void lasVentasNoPuedenSerNegativas() {
        assertThrows(InvalidSalesException.class, () -> new Sales("-1"));
    }

    @Test
    void laAntiguedadNoPuedeSerNegativa() {
        assertThrows(InvalidSeniorityException.class, () -> new Seniority((short) -1));
    }

    @Test
    void ningunEmpleadoPuedeTenerSalarioNetoNegativo() {
        assertThrows(InvalidPayrollException.class, () -> Payroll.create(
                UUID.randomUUID(),
                new Money("100"), Money.ZERO, Money.ZERO,
                new Money("200")));
    }
}