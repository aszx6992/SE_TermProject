package SE_package;

import static org.junit.Assert.*;

import org.junit.Test;

public class InStringTest {

	@Test
	public void urlTest() {
		InString test = new InString();
		
		String test_result = test.init("Submit the report via [Hisnet](http://hisnet.handong.edu \"Hisnet\").");
		assertEquals("Submit the report via <a href=\"http://hisnet.handong.edu\" title=\"Hisnet\">Hisnet</a>.", test_result);
		
	}
	
	@Test
	public void specialTest() {
		InString test = new InString();
				
		String test_result = test.init("- Design & Implementation");
		assertEquals("- Design &amp; Implementation", test_result);
		
	}
	
	@Test
	public void codeTest() {
		InString test = new InString();
		
		String test_result = test.init("Do not forget to `git push` once you make `git commit`.");
		assertEquals("Do not forget to <code>git push</code> once you make <code>git commit</code>.", test_result);
		
	}
	
	@Test
	public void boldTest() {
		InString test = new InString();

		String test_result = test.init("This assignment askes you to build a program that reads a *Markdown* file and converts it to a HTML file.");
		assertEquals("This assignment askes you to build a program that reads a <b>Markdown</b> file and converts it to a HTML file.", test_result);
		
	}
	
	@Test
	public void imageTest() {
		InString test = new InString();
		
		String test_result = test.init("All copy rights reserved in Handong Global University  ![tag](https://www.handong.edu/site/handong/res/img/logo.png)");
		assertEquals("All copy rights reserved in Handong Global University  <img src=\"https://www.handong.edu/site/handong/res/img/logo.png\" alt=\"tag\">", test_result);
		
	}
	
	@Test
	public void italicTest() {
		InString test = new InString();
		
		String test_result = test.init("This assignment askes you to build a program that reads a **Markdown** file and converts it to a HTML file.");
		assertEquals("This assignment askes you to build a program that reads a <i>Markdown</i> file and converts it to a HTML file.", test_result);
	}
//		
//	@Test
//	public void flag_falseTest() {
//		InString test = new InString();
//		
//		String test_result = test.init("Pre-formatted code blocks are used for writing about programming or markup source code.");
//		System.out.println(test_result);
//		assertEquals("Pre-formatted code blocks are used for writing about programming or markup source code.", test_result);
//	}
//	
//	@Test
//	public void flag_trueTest() {
//		InString test = new InString();
//		
//		String test_result = test.init("Buy books at [Aladin](www.aladin.co.kr \"Go to Aladin\") please!");
//		System.out.println(test_result);
//		assertEquals("Buy books at <a href=\"www.aladin.co.kr\" title=\"Go to Aladin\">Aladin</a> please!", test_result);
//	}
//	
//	
	
}
