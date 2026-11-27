package domain.vo;

import com.nomina.domain.exceptions.InvalidHoursWorkedException;
import com.nomina.domain.exceptions.InvalidPayrollException;
import com.nomina.domain.exceptions.InvalidSalesException;
import com.nomina.domain.exceptions.InvalidSeniorityException;
import com.nomina.domain.vo.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BusinessValidationsTest {

    @Test
    void lasHorasTrabajadasNoPuedenSerNegativas() {
        assertThrows(InvalidHoursWorkedException.class, () -> new HoursWorked(-1));
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