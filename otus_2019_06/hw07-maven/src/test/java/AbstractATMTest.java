import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.actions.impl.RubATM;
import ru.deft.homework.actions.impl.UsdATM;
import ru.deft.homework.constants.BanknoteDenominationUs;
import ru.deft.homework.department.impl.Department;
import ru.deft.homework.errors.CashMachineException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AbstractATMTest {

    private static CashMachineActions rubAtm;
    private static CashMachineActions usdAtm;

    @BeforeEach
    void setUp() {
        Department department = new Department();
        rubAtm = new RubATM(department);
        usdAtm = new UsdATM(department);
    }

    @Test
    void depositCashInRubATM() {
        rubAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 10, rubAtm.getBalance());
    }

    @Test
    void depositCashInUsdATM() {
        usdAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 10, usdAtm.getBalance());
    }

    @Test
    void withdrawCashRubATM() {
        rubAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        rubAtm.withdrawCash(1000);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 9, rubAtm.getBalance());
    }

    @Test
    void withdrawCashRubATMErr() {
        rubAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertThrows(CashMachineException.class, () -> rubAtm.withdrawCash(1_000_000));
    }

    @Test
    void withdrawCashUsdATM() {
        usdAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        usdAtm.withdrawCash(1000);
        assertEquals(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation() * 9, usdAtm.getBalance());
    }

    @Test
    void withdrawCashUsdATMErr() {
        usdAtm.depositCash(BanknoteDenominationUs.ONE_THOUSAND.getDenomanation(), 10);
        assertThrows(CashMachineException.class, () -> usdAtm.withdrawCash(1_000_000));
    }
}
