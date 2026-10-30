package domain.service;

import domain.vo.Money;

public interface PayrollCalculator{
    Money calculateGrossSalary();
    Money calculateBonuses();
    Money calculateBenefits();
}
