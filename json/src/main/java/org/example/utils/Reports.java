package org.example.utils;

public enum Reports {
    CAR_MILEAGE_REPORT("Пробег автомобиля за период", "mileage"),
    NEW_CARS_REPORT("Новых автомобилей", "cars");

    private String name;
    private String requestValue;

    public String getName() {
        return name;
    }

    public String getRequestValue() {
        return requestValue;
    }

    Reports(String name, String requestValue) {
        this.name = name;
        this.requestValue = requestValue;
    }
}
