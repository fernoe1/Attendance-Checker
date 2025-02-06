package utilities;

import java.time.LocalDate;
import java.util.Map;

public class WeekUtils {
    private static final Map<String, Integer> TRIMESTER_STARTS_MONTH_DAY = Map.of(
            "Autumn trimester", 9 * 100 + 2,
            "Winter trimester", 12 * 100 + 9,
            "Spring trimester", 3 * 100 + 17
    );

    private static LocalDate getDate() {
        long unixTime = System.currentTimeMillis() / 1000;
        return LocalDate.ofEpochDay(unixTime / 86400);
    }

    private static LocalDate getTrimesterStart(String trimester, int year, LocalDate today) {
        int monthDay = TRIMESTER_STARTS_MONTH_DAY.get(trimester);
        int month = monthDay / 100;
        int day = monthDay % 100;

        if (month == 12 && today.getMonthValue() < 3) {
            year--;
        }

        return LocalDate.of(year, month, day);
    }

    public static String getCurrentTrimester() {
        LocalDate today = getDate();
        int currentYear = today.getYear();

        for (String trimester : TRIMESTER_STARTS_MONTH_DAY.keySet()) {
            LocalDate start = getTrimesterStart(trimester, currentYear, today);
            LocalDate end = start.plusWeeks(10).minusDays(1);

            if (!today.isBefore(start) && !today.isAfter(end)) {
                return trimester;
            }
        }

        return "Holidays or Summer trimester";
    }

    public static int getCurrentWeek() {
        String trimester = getCurrentTrimester();
        if (trimester.equals("Holidays or Summer trimester")) {
            return -1;
        }

        LocalDate today = getDate();
        int currentYear = today.getYear();
        LocalDate start = getTrimesterStart(trimester, currentYear, today);

        long daysBetween = today.toEpochDay() - start.toEpochDay();
        return (int) (daysBetween / 7) + 1;
    }
}