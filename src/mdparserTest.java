import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import java.io.*;

public class mdparserTest {
        mdparser tester = new mdparser();

        public mdparserTest() {
                tester = new mdparser();
        }

        @Test
        public void testForHelp () {

                final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));
                tester.main(new String[] {"--help"});
                assertEquals("This is the help message", outContent.toString());
        }

        public static void main(String[] args) {
                mdparserTest tester = new mdparserTest();
                System.out.println("Test");
                tester.testForHelp();
        }
}

