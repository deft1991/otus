package ru.deft.homework.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.constants.BanknoteDenominationUs;
import ru.deft.homework.errors.CashMachineException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractATMTest {

    private static CashMachineActions rubAtm;
    private static CashMachineActions usdAtm;

    @BeforeAll static void setUp() {
        rubAtm = new RubATM();
        usdAtm = new UsdATM();
    }

    @Test void depositCashInRubATM() {
        rubAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 10, rubAtm.getBalance());
    }

    @Test void depositCashInUsdATM() {
        usdAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 10, usdAtm.getBalance());
    }

    @Test void withdrawCashRubATM() {
        rubAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        rubAtm.withdrawCash(1000);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 9, rubAtm.getBalance());
    }

    @Test void withdrawCashRubATMErr() {
        rubAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertThrows(CashMachineException.class, () -> rubAtm.withdrawCash(1_000_000));
    }

    @Test void withdrawCashUsdATM() {
        usdAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        usdAtm.withdrawCash(1000);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 9, usdAtm.getBalance());
    }

    @Test void withdrawCashUsdATMErr() {
        usdAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertThrows(CashMachineException.class, () -> usdAtm.withdrawCash(1_000_000));
    }
}
