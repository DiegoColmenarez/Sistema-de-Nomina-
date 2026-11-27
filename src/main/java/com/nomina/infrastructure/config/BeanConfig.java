package com.nomina.infrastructure.config;

import com.nomina.application.factory.PayrollCalculatorFactory;
import com.nomina.application.ports.out.EmployeeDataPort;
import com.nomina.application.ports.out.SavePayrollPort;
import com.nomina.application.usecase.GeneratePayrollService;
import com.nomina.application.usecase.GeneratePayrollUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    PayrollCalculatorFactory payrollCalculatorFactory() {
        return new PayrollCalculatorFactory();
    }

    @Bean
    GeneratePayrollUseCase generatePayrollUseCase(EmployeeDataPort employees,
                                                  SavePayrollPort storage,
                                                  PayrollCalculatorFactory factory) {
        return new GeneratePayrollService(employees, storage, factory);
    }

    // + beans de GetPayrollUseCase y ListPayrollsUseCase cuando existan
}
