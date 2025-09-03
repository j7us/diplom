package org.example.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VehicleUtils {
    private static final String CAR_NUMBERS = "абвгдеёжзийклмнопрстуфхцчшщыэюя";

    public static String getRandomCarNumber() {
        int size = CAR_NUMBERS.length() - 1;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            if (i == 0 || i > 3) {
                builder.append(CAR_NUMBERS.charAt(ThreadLocalRandom.current().nextInt(size)));
            } else {
                builder.append(ThreadLocalRandom.current().nextInt(9));
            }
        }

        return builder.toString().toUpperCase();
    }

    public static LocalDate getRandomDate() {
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        return startDate.plusDays(randomDays);
    }
}
