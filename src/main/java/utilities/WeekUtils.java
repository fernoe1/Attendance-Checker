package utilities;

import java.time.LocalDate;

import java.util.Map;

public class WeekUtils {
    private static long unixTime;
    private static LocalDate today = getDate();
    private static int currentYear = today.getYear();

    private static final Map<String, Integer> TRIMESTER_STARTS_MONTH_DAY = Map.of(
            "Autumn trimester", 9 * 100 + 2,
            "Winter trimester", 12 * 100 + 9,
            "Spring trimester", 3 * 100 + 17
    );

    public WeekUtils(long unixTime) {
        this.unixTime = unixTime;
    }

    public static LocalDate getDate() {
        return LocalDate.ofEpochDay(unixTime / 86400);
    }

    private static LocalDate getTrimesterStart(String trimester, int year) {
        int monthDay = TRIMESTER_STARTS_MONTH_DAY.get(trimester);
        int month = monthDay / 100;
        int day = monthDay % 100;
        return LocalDate.of(year, month, day);
    }

    public static String getCurrentTrimester() {
        for (Map.Entry<String, Integer> entry : TRIMESTER_STARTS_MONTH_DAY.entrySet()) {
            String trimester = entry.getKey();
            LocalDate start = getTrimesterStart(trimester, currentYear);
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

        LocalDate start = getTrimesterStart(trimester, currentYear);
        long daysBetween = today.toEpochDay() - start.toEpochDay();
        return (int) (daysBetween / 7) + 1;
    }
}
