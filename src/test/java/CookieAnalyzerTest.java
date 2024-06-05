import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ICookieReader;
import service.impl.CookieReaderImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;

class CookieAnalyzerTest {

    private static final String COOKIE_FILE_WITH_UNIQUE_RESULT = "test_cookie_unique.csv";
    private static final String COOKIE_FILE_WITH_MULTIPLE_RESULTS = "test_cookie_multiple.csv";
    private static final String TEST_DATE = "2018-12-09";
    private static final String EXPECTED_COOKIE_1 = "AtY0laUfhglK3lC7";
    private static final String UNRECOGNIZED_OPTION = "Unrecognized option: -g";
    public static final String ERROR_READING_FILE = "Error reading file";

    private static final ICookieReader COOKIE_READER = new CookieReaderImpl();

    private static ByteArrayOutputStream outContent;

    private static String getFilePath(String fileName) {
        final ClassLoader classLoader = CookieAnalyzerTest.class.getClassLoader();
        final URL resource = classLoader.getResource(fileName);

        assertNotNull(resource, "Couldn't find the file: " + fileName);

        final File file = new File(resource.getFile());

        return file.getAbsolutePath();
    }

    private static List<String> getOutput() {
        return List.of(outContent.toString().trim().split("\\r\\n"));
    }

    private static void findAndProcessCookies(String filePath) throws IOException {
        final List<String> mostActiveCookies = COOKIE_READER.findMostActiveCookies(filePath, CookieAnalyzerTest.TEST_DATE);
        COOKIE_READER.processMostActiveCookies(mostActiveCookies);
    }

    @BeforeEach()
    public void setOutContentReader() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }


    @Test
    void testFindMostActiveCookies() {
        final String filePath = getFilePath(COOKIE_FILE_WITH_UNIQUE_RESULT);
        CookieAnalyzer.analyzeCookies(new String[]{"-f", filePath, "-d", TEST_DATE});
        final List<String> output = getOutput();
        assertTrue(output.contains(EXPECTED_COOKIE_1));
        assertEquals(1, output.size());
    }

    @Test
    void testInvalidOptions() {
        final String filePath = getFilePath(COOKIE_FILE_WITH_MULTIPLE_RESULTS);
        CookieAnalyzer.analyzeCookies(new String[]{"-g", filePath, "-d", TEST_DATE});
        final List<String> output = getOutput();
        assertTrue(output.contains(UNRECOGNIZED_OPTION));
    }

    @Test
    void testFileNotFound() {
        CookieAnalyzer.analyzeCookies(new String[]{"-f", "filePath", "-d", TEST_DATE});
        final List<String> output = getOutput();
        assertEquals(1, output.size());
        assertTrue(output.get(0).contains(ERROR_READING_FILE));
    }
}
