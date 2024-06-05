package service.impl;

import service.ICookieReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Implementation of {@link ICookieReader}.
 */
public class CookieReaderImpl implements ICookieReader {
    private static final String DELIMITER = ",";

    @Override
    public List<String> findMostActiveCookies(final String filePath, final String dateStr) throws IOException {
        final Map<String, Integer> cookieCount = new HashMap<>();
        final LocalDate targetDate = LocalDate.parse(dateStr);

        try (final BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(DELIMITER);
                if (parts.length != 2) continue;

                String cookie = parts[0];
                ZonedDateTime timestamp = ZonedDateTime.parse(parts[1], DateTimeFormatter.ISO_ZONED_DATE_TIME);

                if (timestamp.toLocalDate().equals(targetDate)) {
                    cookieCount.put(cookie, cookieCount.getOrDefault(cookie, 0) + 1);
                }
            }
        }

        if (cookieCount.isEmpty()) {
            return Collections.emptyList();
        }

        final int maxCount = Collections.max(cookieCount.values());

        final Predicate<Map.Entry<String, Integer>> filterPredicate = entry -> entry.getValue() == maxCount;

        return cookieCount
                .entrySet()
                .stream()
                .filter(filterPredicate)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public void processMostActiveCookies(final List<String> mostActiveCookies) {
        mostActiveCookies.forEach(System.out::println);
    }
}
