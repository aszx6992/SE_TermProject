package SE_package;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

import javax.management.OperationsException;

import java.io.*;

public class mdparserTest {

    @Test
    public void testForOption_false() 
    {
    	mdparser tester = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tester.main(new String[] {"-wrong"});
        String result = outContent.toString();
        assertEquals("mdparser: invalid option..", outContent.toString());
    }
    
    @Test
    public void testForHelp() 
    {
    	mdparser tester = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tester.main(new String[] {"--help"});
        assertEquals("This is the help message", outContent.toString());
        
    }
    

    @Test
    public void testForOption_true() 
    {
    	System.out.println("hello");
    	mdparser tester = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    	tester.main(new String[] {"-pl doc1.md"});
        assertEquals("-pl", tester.options.get(0));
    }
    
    
    
    
    

}
