package application.dto;

import domain.vo.Money;

public record PayrollResult(String employeeName, Money gross, Money bonuses,
                            Money benefits, Money deductions, Money net) { }