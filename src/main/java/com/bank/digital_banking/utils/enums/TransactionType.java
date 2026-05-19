package com.bank.digital_banking.utils.enums;

public enum TransactionType {
    DEPOSIT(1),
    WITHDRAW(-1),
    TRANSFER_IN(1),
    TRANSFER_OUT(-1);

    private final int multiplier;
    TransactionType(int multiplier) {
        this.multiplier = multiplier;
    }
    public int getMultiplier() {
        return multiplier;
    }
}
