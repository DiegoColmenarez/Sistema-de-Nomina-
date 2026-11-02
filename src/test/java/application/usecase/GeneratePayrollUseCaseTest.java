package application.usecase;

import application.data.PayrollData;
import application.exception.EmployeeNotFoundException;
import application.exception.InvalidPayrollDataException;
import application.factory.CommissionCalculatorFactory;
import application.factory.HourlyCalculatorFactory;
import application.factory.PayrollCalculatorRegistry;
import application.factory.SalariedCalculatorFactory;
import application.factory.TemporaryCalculatorFactory;
import application.model.EmployeeRecord;
import application.model.EmployeeType;
import application.repository.EmployeeRepository;
import domain.exceptions.InvalidHorasTrabajadasException;
import domain.vo.Money;
import domain.vo.Payroll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneratePayrollUseCaseTest {

    private final EmployeeRecord salaried = new EmployeeRecord(UUID.randomUUID(), "Ana", EmployeeType.SALARIED);
    private final EmployeeRecord hourly = new EmployeeRecord(UUID.randomUUID(), "Luis", EmployeeType.HOURLY);
    private final EmployeeRecord commission = new EmployeeRecord(UUID.randomUUID(), "Marta", EmployeeType.COMMISSION);
    private final EmployeeRecord temporary = new EmployeeRecord(UUID.randomUUID(), "Carlos", EmployeeType.TEMPORARY);

    private GeneratePayrollUseCase useCase;

    @BeforeEach
    void setUp() {
        List<EmployeeRecord> employees = List.of(salaried, hourly, commission, temporary);

        EmployeeRepository repository = new EmployeeRepository() {
            @Override
            public Optional<EmployeeRecord> findById(UUID id) {
                return employees.stream().filter(e -> e.id().equals(id)).findFirst();
            }

            @Override
            public List<EmployeeRecord> findAll() {
                return employees;
            }
        };

        var registry = new PayrollCalculatorRegistry(List.of(
                new SalariedCalculatorFactory(),
                new HourlyCalculatorFactory(),
                new CommissionCalculatorFactory(),
                new TemporaryCalculatorFactory()));

        useCase = new GeneratePayrollUseCase(repository, registry);
    }

    @Test
    void asalariadoConMasDe5AniosRecibeBonoYBonoDeAlimentacion() {
        Payroll payroll = useCase.execute(salaried.id(), new PayrollData(Map.of("seniorityYears", "6")));

        assertEquals(new Money("1750905"), payroll.gross());
        assertEquals(new Money("175090.50"), payroll.bonuses());
        assertEquals(new Money("1000000"), payroll.benefits());
        assertEquals(new Money("70036.20"), payroll.deductions());
        assertEquals(new Money("2855959.30"), payroll.net());
    }

    @Test
    void empleadoPorHorasConHorasExtraCobraLasExtrasA1Punto5() {
        Payroll payroll = useCase.execute(hourly.id(), new PayrollData(Map.of(
                "hoursWorked", "45", "seniorityYears", "0", "acceptsSavingsFund", "n")));

        assertEquals(new Money("396055"), payroll.gross());
        assertEquals(Money.ZERO, payroll.bonuses());
        assertEquals(Money.ZERO, payroll.benefits());
        assertEquals(new Money("15842.20"), payroll.deductions());
        assertEquals(new Money("380212.80"), payroll.net());
    }

    @Test
    void empleadoPorComisionConVentasAltasRecibeComisionDel3PorCiento() {
        Payroll payroll = useCase.execute(commission.id(), new PayrollData(Map.of("sales", "30000000")));

        assertEquals(new Money("2650905"), payroll.gross());
        assertEquals(Money.ZERO, payroll.bonuses());
        assertEquals(new Money("1000000"), payroll.benefits());
        assertEquals(new Money("106036.20"), payroll.deductions());
        assertEquals(new Money("3544868.80"), payroll.net());
    }

    @Test
    void empleadoTemporalNoNecesitaDatosNiRecibeBonosNiBeneficios() {
        Payroll payroll = useCase.execute(temporary.id(), new PayrollData(Map.of()));

        assertEquals(new Money("1750905"), payroll.gross());
        assertEquals(Money.ZERO, payroll.bonuses());
        assertEquals(Money.ZERO, payroll.benefits());
        assertEquals(new Money("1680868.80"), payroll.net());
    }

    @Test
    void conHorasNegativasLanzaExcepcionDeDominio() {
        var data = new PayrollData(Map.of("hoursWorked", "-5", "seniorityYears", "0", "acceptsSavingsFund", "n"));

        assertThrows(InvalidHorasTrabajadasException.class, () -> useCase.execute(hourly.id(), data));
    }

    @Test
    void conUnDatoNoNumericoLanzaExcepcion() {
        var data = new PayrollData(Map.of("sales", "abc"));

        assertThrows(InvalidPayrollDataException.class, () -> useCase.execute(commission.id(), data));
    }

    @Test
    void conUnEmpleadoInexistenteLanzaExcepcion() {
        assertThrows(EmployeeNotFoundException.class,
                () -> useCase.execute(UUID.randomUUID(), new PayrollData(Map.of())));
    }
}