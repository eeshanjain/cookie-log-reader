package service;

import java.io.IOException;
import java.util.List;

public interface ICookieReader {
    /**
     * Method to parse through the cookie file and find out the most active cookies.
     *
     * @param filePath - Absolute path of the cookie file.
     * @param dateStr - The date for which we need to find the most active cookies.
     * @return A list of most active cookies.
     * @throws IOException in case the file is not found.
     */
    List<String> findMostActiveCookies(String filePath, String dateStr) throws IOException;

    /**
     * Method to process the most active cookies.
     *
     * @param mostActiveCookies - Most active cookies to be processed.
     */
    void processMostActiveCookies(List<String> mostActiveCookies);
}
