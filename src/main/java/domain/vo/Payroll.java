package domain.vo;

import domain.exceptions.InvalidPayrollException;

import java.util.UUID;

public record Payroll(UUID employeeId, Money gross, Money bonuses,
                      Money benefits, Money deductions, Money net) {

    public static Payroll create(UUID employeeId, Money gross, Money bonuses, Money benefits, Money deductions){

        Money totalIncome = gross.add(bonuses).add(benefits);

        if (deductions.isGreaterThan(totalIncome)) {
            throw InvalidPayrollException.negativeNet();
        }

        return new Payroll(employeeId, gross, bonuses, benefits,
                deductions, totalIncome.subtract(deductions));
    }
}