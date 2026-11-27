package application.usecase;

import com.nomina.application.dto.CommissionContract;
import com.nomina.application.dto.EmployeeContract;
import com.nomina.application.dto.HourlyContract;
import com.nomina.application.dto.PayrollResult;
import com.nomina.application.dto.SalariedContract;
import com.nomina.application.dto.TemporaryContract;
import com.nomina.application.dto.command.GeneratePayrollCommand;
import com.nomina.application.factory.PayrollCalculatorFactory;
import com.nomina.application.ports.out.EmployeeDataPort;
import com.nomina.application.ports.out.SavePayrollPort;
import com.nomina.application.usecase.GeneratePayrollService;
import com.nomina.application.usecase.GeneratePayrollUseCase;
import com.nomina.domain.exceptions.EmployeeNotFoundException;
import com.nomina.domain.exceptions.InvalidHoursWorkedException;
import com.nomina.domain.exceptions.InvalidSalesException;
import com.nomina.domain.vo.Money;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratePayrollUseCaseTest {

    private static final YearMonth PERIOD = YearMonth.of(2026, 11);
    private static final Money BASE_SALARY = new Money("1750905");
    private static final Money HOURLY_RATE = new Money("8338");

    private final List<PayrollResult> saved = new ArrayList<>();

    private GeneratePayrollUseCase useCaseWith(EmployeeContract contract) {
        EmployeeDataPort employees = id -> Optional.of(contract);
        SavePayrollPort storage = saved::add;
        return new GeneratePayrollService(employees, storage, new PayrollCalculatorFactory());
    }

    private GeneratePayrollCommand command(EmployeeContract contract, Integer hours, BigDecimal sales) {
        return new GeneratePayrollCommand(contract.employeeId(), PERIOD, hours, sales);
    }

    private SalariedContract salaried(LocalDate hireDate) {
        return new SalariedContract(UUID.randomUUID(), "Ana", hireDate, BASE_SALARY);
    }

    private HourlyContract hourly(LocalDate hireDate, boolean acceptsFund) {
        return new HourlyContract(UUID.randomUUID(), "Luis", hireDate, HOURLY_RATE, acceptsFund);
    }

    private CommissionContract commission() {
        return new CommissionContract(UUID.randomUUID(), "Marta", BASE_SALARY);
    }

    @Test
    void asalariadoConMasDe5AniosRecibeBonoYBonoDeAlimentacion() {
        SalariedContract contract = salaried(LocalDate.of(2020, 1, 1)); // 6 años a nov-2026

        PayrollResult result = useCaseWith(contract).execute(command(contract, null, null));

        assertEquals("Ana", result.employeeName());
        assertEquals(new Money("1750905"), result.gross());
        assertEquals(new Money("175090.50"), result.bonuses());
        assertEquals(new Money("1000000"), result.benefits());
        assertEquals(new Money("70036.20"), result.deductions());
        assertEquals(new Money("2855959.30"), result.net());
    }

    @Test
    void asalariadoConExactamente5AniosNoRecibeBono() {
        SalariedContract contract = salaried(LocalDate.of(2021, 6, 1)); // 5 años a nov-2026

        PayrollResult result = useCaseWith(contract).execute(command(contract, null, null));

        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(new Money("2680868.80"), result.net());
    }

    @Test
    void empleadoPorHorasConHorasExtraCobraLasExtrasA1Punto5() {
        HourlyContract contract = hourly(LocalDate.of(2026, 1, 1), false); // 0 años

        PayrollResult result = useCaseWith(contract).execute(command(contract, 45, null));

        assertEquals(new Money("396055"), result.gross());
        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(Money.ZERO, result.benefits());
        assertEquals(new Money("15842.20"), result.deductions());
        assertEquals(new Money("380212.80"), result.net());
    }

    @Test
    void empleadoPorHorasConMasDeUnAnioYFondoRecibeAhorroDel2Porciento() {
        HourlyContract contract = hourly(LocalDate.of(2024, 1, 1), true); // 2 años

        PayrollResult result = useCaseWith(contract).execute(command(contract, 45, null));

        assertEquals(new Money("7921.10"), result.benefits());
        assertEquals(new Money("388133.90"), result.net());
    }

    @Test
    void empleadoPorComisionConVentasAltasRecibeComisionDel3PorCiento() {
        CommissionContract contract = commission();

        PayrollResult result = useCaseWith(contract)
                .execute(command(contract, null, new BigDecimal("30000000")));

        assertEquals(new Money("2650905"), result.gross());
        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(new Money("1000000"), result.benefits());
        assertEquals(new Money("106036.20"), result.deductions());
        assertEquals(new Money("3544868.80"), result.net());
    }

    @Test
    void empleadoPorComisionConVentasBajasSoloRecibeSalarioBase() {
        CommissionContract contract = commission();

        PayrollResult result = useCaseWith(contract)
                .execute(command(contract, null, new BigDecimal("10000000")));

        assertEquals(new Money("1750905"), result.gross());
        assertEquals(new Money("2680868.80"), result.net());
    }

    @Test
    void empleadoTemporalNoRecibeBonosNiBeneficios() {
        TemporaryContract contract =
                new TemporaryContract(UUID.randomUUID(), "Carlos", BASE_SALARY);

        PayrollResult result = useCaseWith(contract).execute(command(contract, null, null));

        assertEquals(new Money("1750905"), result.gross());
        assertEquals(Money.ZERO, result.bonuses());
        assertEquals(Money.ZERO, result.benefits());
        assertEquals(new Money("1680868.80"), result.net());
    }

    @Test
    void guardaLaNominaGeneradaConSuEmpleadoYPeriodo() {
        TemporaryContract contract =
                new TemporaryContract(UUID.randomUUID(), "Carlos", BASE_SALARY);

        PayrollResult result = useCaseWith(contract).execute(command(contract, null, null));

        assertEquals(1, saved.size());
        assertEquals(result, saved.get(0));
        assertEquals(contract.employeeId(), result.employeeId());
        assertEquals(PERIOD, result.period());
    }

    @Test
    void conHorasNegativasLanzaExcepcionDeDominio() {
        HourlyContract contract = hourly(LocalDate.of(2026, 1, 1), false);

        assertThrows(InvalidHoursWorkedException.class,
                () -> useCaseWith(contract).execute(command(contract, -5, null)));
    }

    @Test
    void sinHorasParaUnEmpleadoPorHorasLanzaExcepcionDeDominio() {
        HourlyContract contract = hourly(LocalDate.of(2026, 1, 1), false);

        assertThrows(InvalidHoursWorkedException.class,
                () -> useCaseWith(contract).execute(command(contract, null, null)));
    }

    @Test
    void conVentasNegativasLanzaExcepcionDeDominio() {
        CommissionContract contract = commission();

        assertThrows(InvalidSalesException.class,
                () -> useCaseWith(contract).execute(command(contract, null, new BigDecimal("-1"))));
    }

    @Test
    void sinVentasParaUnEmpleadoPorComisionLanzaExcepcionDeDominio() {
        CommissionContract contract = commission();

        assertThrows(InvalidSalesException.class,
                () -> useCaseWith(contract).execute(command(contract, null, null)));
    }

    @Test
    void siElEmpleadoNoExisteLanzaEmployeeNotFound() {
        EmployeeDataPort employees = id -> Optional.empty();
        GeneratePayrollUseCase useCase =
                new GeneratePayrollService(employees, saved::add, new PayrollCalculatorFactory());

        assertThrows(EmployeeNotFoundException.class,
                () -> useCase.execute(new GeneratePayrollCommand(UUID.randomUUID(), PERIOD, null, null)));
    }
}