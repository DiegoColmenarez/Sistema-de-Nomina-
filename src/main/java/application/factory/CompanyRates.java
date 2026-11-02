package application.factory;

import domain.vo.Money;

final class CompanyRates {

    static final Money BASE_SALARY = new Money("1750905");
    static final Money HOURLY_RATE = new Money("8338");

    private CompanyRates() {
    }
}
