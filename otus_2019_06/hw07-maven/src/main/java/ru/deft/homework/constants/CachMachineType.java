package ru.deft.homework.constants;

public enum CachMachineType {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR");

    String type;

    CachMachineType(String type) {
        this.type = type;
    }
}
