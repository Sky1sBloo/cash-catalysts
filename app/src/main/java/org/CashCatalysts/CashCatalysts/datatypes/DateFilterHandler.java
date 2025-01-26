package org.CashCatalysts.CashCatalysts.datatypes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Handles date time filter
 */
public class DateFilterHandler {
    /**
     * Returns a date range from a filter type
     * Returns the minimum and maximum range if invalid
     */
    public static DateRange getDateRangeFromFilterType(DateFilterType filter) {
        LocalDate begin;
        LocalDate end = switch (filter) {
            case DateFilterType.DAY -> {
                begin = LocalDate.from(LocalDate.now().atStartOfDay());
                yield LocalDate.from(LocalDate.now().atTime(23, 59, 59));
            }
            case DateFilterType.WEEK -> {
                begin = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                yield LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            }
            case DateFilterType.MONTH -> {
                begin = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                yield LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            }
            case DateFilterType.YEAR -> {
                begin = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
                yield LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
            }
            case DateFilterType.ALL -> {
                begin = LocalDate.MIN;
                yield LocalDate.MAX;
            }
        };

        return new DateRange(begin, end);
    }
}
