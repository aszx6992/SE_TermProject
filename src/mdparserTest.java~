package SE_package;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

import javax.management.OperationsException;

import java.io.*;

public class mdparserTest {

    @Test
    public void testFor_optionFalse() 
    {
    	mdparser tester1 = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tester1.main(new String[] {"-wrong"});
        assertEquals("mdparser: invalid option.. Try 'mdparser --help' for more information", outContent.toString());
        tester1 = null;
    }
    
    @Test
    public void testFor_optionHelp() 
    {
    	mdparser tester2 = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        tester2.main(new String[] {"--help"});
        assertEquals("This is the help message", outContent.toString());
        tester2 = null;
        
    }
    

    @Test
    public void testFor_optionTrue() 
    {
    	mdparser tester3 = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    	tester3.main(new String[] {"-pl", "doc1.md"});
        assertEquals("-pl", tester3.options.get(0));
        tester3 = null;
    }
    
    @Test
    public void testFor_defaultName() 
    {
    	mdparser tester4 = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    	tester4.main(new String[] {"-pl", "doc2.md"});
        assertEquals("doc2.html", tester4.htmlName.get(0));
        tester4 = null;
    }
    
    
    @Test
    public void testFor_mdName() 
    {
    	mdparser tester5 = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    	tester5.main(new String[] {"-pl", "doc1.md", "hello_world.html"});
        assertEquals("hello_world.html", tester5.htmlName.get(1));
        tester5 = null;
    }
    
    
    @Test
    public void testFor_wrong() 
    {
    	mdparser tester5 = new mdparser();
    	final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    	tester5.main(new String[] {"doaieja"});
        assertEquals("Please input a file extention of '.md' for conversion", outContent.toString());
        tester5 = null;
    }
    
}
