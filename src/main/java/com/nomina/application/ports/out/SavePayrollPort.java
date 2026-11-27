package com.nomina.application.ports.out;

import com.nomina.application.dto.PayrollResult;

public interface SavePayrollPort {
    void save(PayrollResult result);
}
