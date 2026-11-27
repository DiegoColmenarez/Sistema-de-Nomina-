package com.nomina.domain.service;

import com.nomina.domain.vo.Money;

public interface PayrollCalculator{
    Money calculateGrossSalary();
    Money calculateBonuses();
    Money calculateBenefits();
}
