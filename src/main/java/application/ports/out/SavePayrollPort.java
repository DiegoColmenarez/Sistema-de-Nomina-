package application.ports.out;

import application.dto.PayrollResult;

public interface SavePayrollPort {
    void save(PayrollResult result);
}
