package ru.deft.homework;

import ru.deft.homework.actions.CashMachineActions;
import ru.deft.homework.constants.BanknoteDenominationUs;
import ru.deft.homework.errors.CashMachineException;
import ru.deft.homework.impl.RubATM;
import ru.deft.homework.impl.UsdATM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static ru.deft.homework.constants.Constants.CHOOSE_AN_OPERATION;
import static ru.deft.homework.constants.Constants.CHOOSE_CURRENCY;
import static ru.deft.homework.constants.Constants.COMMAND_TO_REASKING;
import static ru.deft.homework.constants.Constants.CURRENCY;
import static ru.deft.homework.constants.Constants.DEPOSIT_CASH;
import static ru.deft.homework.constants.Constants.ENTER_DENOMINATION;
import static ru.deft.homework.constants.Constants.ENTER_NUMBER_OF_BILLS;
import static ru.deft.homework.constants.Constants.ENTER_NUMBER_OF_OPERATION;
import static ru.deft.homework.constants.Constants.ENTER_THE_AMOUNT;
import static ru.deft.homework.constants.Constants.EXIT;
import static ru.deft.homework.constants.Constants.FIND_OUT_THE_BALANCE;
import static ru.deft.homework.constants.Constants.HAVE_A_NICE_DAY_BYE;
import static ru.deft.homework.constants.Constants.WELCOME;
import static ru.deft.homework.constants.Constants.WITHDRAW_CASH;

class ATM {

    private static CashMachineActions atm;
    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    static void startWork() throws IOException, InterruptedException {
        System.out.println(WELCOME);
        Thread.sleep(500);

        createCurrencyATM();
        prepareActions();
        int command = Integer.parseInt(reader.readLine());

        while (command != 4) {

            switch (command) {
                case 1:
                    deposit();
                    command = COMMAND_TO_REASKING;
                    break;
                case 2:
                    withdraw();
                    command = COMMAND_TO_REASKING;
                    break;
                case 3:
                    balance();
                    command = COMMAND_TO_REASKING;
                    break;
                case COMMAND_TO_REASKING:
                    prepareActions();
                    command = Integer.parseInt(reader.readLine());
                    break;
                default:
                    command = getCommandReRty();
                    break;
            }

        }
        System.out.println(HAVE_A_NICE_DAY_BYE);
        System.exit(0);
    }

    private static void prepareActions() throws InterruptedException {
        Thread.sleep(500);
        System.out.println(CHOOSE_AN_OPERATION);
        System.out.println(DEPOSIT_CASH);
        System.out.println(WITHDRAW_CASH);
        System.out.println(FIND_OUT_THE_BALANCE);
        System.out.println(EXIT);
        System.out.println(ENTER_NUMBER_OF_OPERATION);
    }

    private static void createCurrencyATM() throws IOException {
        System.out.println(CHOOSE_CURRENCY);
        System.out.println(CURRENCY);
        final int currency = Integer.parseInt(reader.readLine());
        switch (currency) {
            case 1:
                atm = new UsdATM();
                break;
            case 2:
                atm = new RubATM();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currency);
        }
    }

    private static void deposit() throws IOException {
        Integer banknoteDenomination = null;
        while (banknoteDenomination == null) {
            System.out.println(ENTER_DENOMINATION);
            final int denomination = Integer.parseInt(reader.readLine());
            banknoteDenomination = atm.checkDenomination(denomination);
        }
        System.out.println(ENTER_NUMBER_OF_BILLS);
        final int countBills = Integer.parseInt(reader.readLine());
        atm.depositCash(banknoteDenomination, countBills);
    }

    private static void withdraw() {
        try {
            System.out.println(ENTER_THE_AMOUNT);
            final int amount = Integer.parseInt(reader.readLine());
            atm.withdrawCash(amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void balance() {
        atm.getBalance();
    }

    private static int getCommandReRty() throws IOException, InterruptedException {
        int command;
        System.out.println("Unknown command. Try again.");
        prepareActions();
        command = Integer.parseInt(reader.readLine());
        return command;
    }

}
