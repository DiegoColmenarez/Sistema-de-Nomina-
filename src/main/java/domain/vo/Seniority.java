package domain.vo;

import domain.exceptions.InvalidSeniorityException;

public record Seniority(short years) {

    private static final int SENIORITY_BONUS_THRESHOLD = 5;
    private static final int SAVINGS_FUND_THRESHOLD = 1;

    public Seniority {
        notNegatives(years);
    }

    private void  notNegatives(short years){
        if (years < 0) {
            throw InvalidSeniorityException.negativeValue();
        }
    }

    public boolean isEligibleForSeniorityBonus() {
        return years > SENIORITY_BONUS_THRESHOLD;
    }

    public boolean isEligibleForSavingsFund() {
        return years > SAVINGS_FUND_THRESHOLD;
    }

    public Seniority addYears(short additionalYears) {
        return new Seniority((short) (this.years + additionalYears));
    }

    @Override
    public String toString() {
        return years == 1 ? "1 año" : years + " años";
    }
}