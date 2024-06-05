import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;

/**
 * Unit test cases of validating behaviour of the main cookie analyzer
 * with respect to how it works with the file path and different command line options.
 */
class CookieAnalyzerTest {

    private static final String COOKIE_FILE_WITH_UNIQUE_RESULT = "test_cookie_unique.csv";
    private static final String COOKIE_FILE_WITH_MULTIPLE_RESULTS = "test_cookie_multiple.csv";
    private static final String COOKIE_FILE_WITH_INVALID_TYPE = "test_cookie_invalid_type.txt";
    private static final String TEST_DATE = "2018-12-09";
    private static final String EXPECTED_COOKIE_1 = "AtY0laUfhglK3lC7";
    private static final String UNRECOGNIZED_OPTION = "Unrecognized option: -g";
    private static final String INVALID_FILE_PATH = "invalid-file-path.csv";
    private static final String ERROR_FILE_NOT_FOUND = "Error reading file: " + INVALID_FILE_PATH + " (The system cannot find the file specified)";
    private static final String ERROR_INVALID_FILE = "Error reading file: Invalid file format, expected CSV";
    private static final String FILE_OPTION = "-f";
    private static final String DATE_OPTION = "-d";
    private static final String INVALID_OPTION = "-g";

    private static ByteArrayOutputStream outContent;

    private static String getFilePath(final String fileName) {
        final ClassLoader classLoader = CookieAnalyzerTest.class.getClassLoader();
        final URL resource = classLoader.getResource(fileName);

        assertNotNull(resource, "Couldn't find the file: " + fileName);

        final File file = new File(resource.getFile());

        return file.getAbsolutePath();
    }

    private static List<String> getOutput() {
        return List.of(outContent.toString().trim().split("\\r\\n"));
    }


    @BeforeEach()
    public void setOutContentReader() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }


    /*
     * Test to validate happy path with a valid CSV file and valid command line options.
     */
    @Test
    void testFindMostActiveCookies() {
        final String filePath = getFilePath(COOKIE_FILE_WITH_UNIQUE_RESULT);
        CookieAnalyzer.analyzeCookies(new String[]{FILE_OPTION, filePath, DATE_OPTION, TEST_DATE});
        final List<String> output = getOutput();
        assertTrue(output.contains(EXPECTED_COOKIE_1));
        assertEquals(1, output.size());
    }

    /*
     * Test to validate behaviour with invalid command line options.
     */
    @Test
    void testInvalidOptions() {
        final String filePath = getFilePath(COOKIE_FILE_WITH_MULTIPLE_RESULTS);
        CookieAnalyzer.analyzeCookies(new String[]{INVALID_OPTION, filePath, DATE_OPTION, TEST_DATE});
        final List<String> output = getOutput();
        assertTrue(output.contains(UNRECOGNIZED_OPTION));
    }

    /*
     * Test to validate behaviour with invalid file type.
     */
    @Test
    void testInvalidFileType() {
        final String filePath = getFilePath(COOKIE_FILE_WITH_INVALID_TYPE);
        CookieAnalyzer.analyzeCookies(new String[]{FILE_OPTION, filePath, DATE_OPTION, TEST_DATE});
        final List<String> output = getOutput();
        assertTrue(output.contains(ERROR_INVALID_FILE));
    }

    /*
     * Test to validate behaviour in case file is not found.
     */
    @Test
    void testFileNotFound() {
        CookieAnalyzer.analyzeCookies(new String[]{FILE_OPTION, INVALID_FILE_PATH, DATE_OPTION, TEST_DATE});
        final List<String> output = getOutput();
        assertEquals(1, output.size());
        assertTrue(output.get(0).contains(ERROR_FILE_NOT_FOUND));
    }
}
