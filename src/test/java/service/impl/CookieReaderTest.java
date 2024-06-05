package service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ICookieReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CookieReaderTest {
    private static final String COOKIE_FILE_WITH_UNIQUE_RESULT = "test_cookie_unique.csv";
    private static final String COOKIE_FILE_WITH_MULTIPLE_RESULTS = "test_cookie_multiple.csv";
    private static final String COOKIE_FILE_WITH_NO_RESULT = "test_cookie_empty.csv";
    private static final String TEST_DATE = "2018-12-09";

    private static final String EXPECTED_COOKIE_1 = "AtY0laUfhglK3lC7";
    private static final String EXPECTED_COOKIE_2 = "SAZuXPGUrfbcn5UA";

    private static ByteArrayOutputStream outContent;

    private static final ICookieReader COOKIE_READER = new CookieReaderImpl();

    private static String getFilePath(String fileName) {
        final ClassLoader classLoader = CookieReaderTest.class.getClassLoader();
        final URL resource = classLoader.getResource(fileName);

        assertNotNull(resource, "Couldn't find the file: " + fileName);

        final File file = new File(resource.getFile());

        return file.getAbsolutePath();
    }

    private static List<String> getOutput() {
        return List.of(outContent.toString().trim().split("\\r\\n"));
    }

    private static void findAndProcessCookies(String filePath) throws IOException {
        final List<String> mostActiveCookies = COOKIE_READER.findMostActiveCookies(filePath, TEST_DATE);
        COOKIE_READER.processMostActiveCookies(mostActiveCookies);
    }

    @BeforeEach()
    public void setOutContentReader() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testFindMostActiveCookies() {
        try {
            findAndProcessCookies(getFilePath(COOKIE_FILE_WITH_UNIQUE_RESULT));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        final List<String> output = getOutput();
        assertTrue(output.contains(EXPECTED_COOKIE_1));
        assertEquals(1, output.size());
    }

    @Test
    void testFindMostActiveMultipleCookies() {
        try {
            findAndProcessCookies(getFilePath(COOKIE_FILE_WITH_MULTIPLE_RESULTS));
        } catch (IOException e) {
            fail(e.getMessage());
        }

        final List<String> output = getOutput();
        assertTrue(output.contains(EXPECTED_COOKIE_1));
        assertTrue(output.contains(EXPECTED_COOKIE_2));
        assertEquals(2, output.size());
    }

    @Test
    void testFindMostActiveEmptyFile() throws IOException {
        COOKIE_READER.findMostActiveCookies(getFilePath(COOKIE_FILE_WITH_NO_RESULT), TEST_DATE);

        final List<String> output = getOutput();
        assertEquals(1, output.size());
        assertTrue(output.get(0).isEmpty());
    }
}
