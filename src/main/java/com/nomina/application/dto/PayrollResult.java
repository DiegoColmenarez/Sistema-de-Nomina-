package com.nomina.application.dto;

import com.nomina.domain.vo.Money;
import java.time.YearMonth;
import java.util.UUID;

public record PayrollResult(UUID employeeId, String employeeName, YearMonth period,
                            Money gross, Money bonuses, Money benefits,
                            Money deductions, Money net) {}