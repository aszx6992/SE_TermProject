import static org.junit.Assert.*;

import org.junit.Test;

public class InStringTest {

	@Test
	public void urlTest() {
		InString test = new InString();
		
		String test_result = test.convert("Submit the report via [Hisnet](http://hisnet.handong.edu \"Hisnet\").");
		System.out.println(test_result);
		assertEquals("Submit the report via <a href=\"http://hisnet.handong.edu\" title=\"Hisnet\">Hisnet</a>.", test_result);
		
	}
	
	@Test
	public void ref_urlTest() {
		InString test = new InString();
		
		String test_result = test.convert("[1]: Github.com (GitHub)");
		System.out.println(test_result);
		assertEquals("<a href=\"Github.com\" title=\"GitHub\">", test_result);
		
	}
	
	@Test
	public void marker_urlTest() {
		InString test = new InString();
		
		String test_result = test.convert("You are given a documentation [link][1] on the Markdown syntax which starts as follows:");
		System.out.println(test_result);
		assertEquals("You are given a documentation [1][link# on the Markdown syntax which starts as follows:", test_result);
		
	}
	
	@Test
	public void specialTest() {
		InString test = new InString();
				
		String test_result = test.convert("- Design & Implementation");
		assertEquals("- Design &amp; Implementation", test_result);
		
	}
	
	@Test
	public void codeTest() {
		InString test = new InString();
		
		String test_result = test.convert("Do not forget to `git push` once you make `git commit`.");
		System.out.println(test_result);
		assertEquals("Do not forget to <code>git push</code> once you make <code>git commit</code>.", test_result);
		
	}
	
	@Test
	public void boldTest() {
		InString test = new InString();

		String test_result = test.convert("This assignment askes you to build a program that reads a **Markdown** file and converts it to a HTML file.");
		System.out.println(test_result);
		assertEquals("This assignment askes you to build a program that reads a <b>Markdown</b> file and converts it to a HTML file.", test_result);
		
	}
	
	@Test
	public void imageTest() {
		InString test = new InString();
		
		String test_result = test.convert("All copy rights reserved in Handong Global University  ![tag](https://www.handong.edu/site/handong/res/img/logo.png)");
		assertEquals("All copy rights reserved in Handong Global University  <img src=\"https://www.handong.edu/site/handong/res/img/logo.png\" alt=\"tag\">", test_result);
		
	}
	
	@Test
	public void italicTest() {
		InString test = new InString();
		
		String test_result = test.convert("This assignment askes you to build a program that reads a *Markdown* file and converts it to a HTML file.");
		System.out.println(test_result);
		assertEquals("This assignment askes you to build a program that reads a <em>Markdown</em> file and converts it to a HTML file.", test_result);
	}
	
	@Test
	public void addwordTest() {
		InString test = new InString();
		
		String test_result = test.convert("Pre-formatted code blocks are used for writing about programming or markup source code.");
		System.out.println(test_result);
		assertEquals("Pre-formatted code blocks are used for writing about programming or markup source code.", test_result);
	}
	
//	@Test
//	public void newlineTest() {
//		InString test = new InString();
//		
//		String test_result = test.convert("");
//		System.out.println(test_result);
//		assertEquals(" ", test_result);
//	}

}
