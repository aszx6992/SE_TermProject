import static org.junit.Assert.*;

import org.junit.Test;

public class ConverterTest {
	Converter converter = new Converter();
	
	@Test
	public void convertTest() {
		// test1 : no file
		// infeasible in here, there is an exception handling in mdparser class.
		
		// test2 : empty
		String[] tc2 = {""};
		assertEquals("\n", converter.convert(tc2));
		
		// test3 : line break
		String[] tc3 = {"- - -"};
		converter.init();
		assertEquals("<hr>\n", converter.convert(tc3));
		
		// test4 : lists
		// normal + normal
		String[] tc4_1 ={"* Markdown supports ordered (numbered) and unordered (bulleted) lists."
				,"* It’s important to note that the actual numbers you use to mark the list have"
		};
		converter.init();
		assertEquals("<ul>\n"
				+ "<li>Markdown supports ordered (numbered) and unordered (bulleted) lists.</li>\n"
				+ "<li>It’s important to note that the actual numbers you use to mark the list have</li>\n"
				+ "</ul>\n\n",converter.convert(tc4_1));
		
		// normal + nested
		String[] tc4_2 ={"* Markdown supports ordered (numbered) and unordered (bulleted) lists."
				,"  * It’s important to note that the actual numbers you use to mark the list have"
		};
		converter.init();
		assertEquals("<ul>\n"
				+ "<li>Markdown supports ordered (numbered) and unordered (bulleted) lists.\n"
				+ "<ul>\n"
				+ "<li>It’s important to note that the actual numbers you use to mark the list have</li>\n"
				+ "</ul>\n"
				+ "</li>\n"
				+ "</ul>\n\n", converter.convert(tc4_2));
		
		// normal + nested + normal
		String[] tc4_3 = {"* Markdown supports ordered (numbered) and unordered (bulleted) lists."
				,"  * It’s important to note that the actual numbers you use to mark the list have"
				,"* If you instead wrote the list in Markdown like this"
		};
		converter.init();
		assertEquals("<ul>\n"
				+"<li>Markdown supports ordered (numbered) and unordered (bulleted) lists.\n"
				+"<ul>\n"
				+"<li>It’s important to note that the actual numbers you use to mark the list have</li>\n"
				+"</ul>\n"
				+"</li>\n"
				+"<li>If you instead wrote the list in Markdown like this</li>\n"
				+"</ul>\n\n", converter.convert(tc4_3));
		
		// normal + indent + normal
		String[] tc4_4 ={"* Markdown supports ordered (numbered) and unordered (bulleted) lists."
				,"  It’s important to note that the actual numbers you use to mark the list have"
				,"* If you instead wrote the list in Markdown like this"
		};
		converter.init();
		assertEquals("<ul>\n" 
				+"<li>Markdown supports ordered (numbered) and unordered (bulleted) lists.\n"
				+"It’s important to note that the actual numbers you use to mark the list have</li>\n"
				+"<li>If you instead wrote the list in Markdown like this</li>\n"
				+"</ul>\n\n", converter.convert(tc4_4));
		
		// normal + nested + indent + normal
		String[] tc4_5 = {"* Markdown supports ordered (numbered) and unordered (bulleted) lists."
				,"* It’s important to note that the actual numbers you use to mark the list have"
				,"  no effect on the HTML output Markdown produces. The HTML Markdown produces"
				,"  from the above list is:"
				,"* If you instead wrote the list in Markdown like this"
				};
		converter.init();
		assertEquals("<ul>" + "\n" +
					"<li>Markdown supports ordered (numbered) and unordered (bulleted) lists.</li>" + "\n" +
					"<li>It’s important to note that the actual numbers you use to mark the list have" + "\n" + 
					"no effect on the HTML output Markdown produces. The HTML Markdown produces" + "\n" +
					"from the above list is:</li>" + "\n" +
					"<li>If you instead wrote the list in Markdown like this</li>" + "\n" +
					"</ul>\n\n", converter.convert(tc4_5));
					
		
		// test5 : block quote
		// '> at the first time and no space after > mark' case 
		String[] tc5_1 = {">Markdown is intended to be as easy-to-read and easy-to-write as is feasible."
				,"Readability, however, is emphasized above all else. A Markdown-formatted document should be publishable"
				,""
		};
		converter.init();
		assertEquals("<blockquote>\n"
				+"  <p>Markdown is intended to be as easy-to-read and easy-to-write as is feasible.\n"
				+"  Readability, however, is emphasized above all else. A Markdown-formatted document should be publishable</p>\n"
				+"</blockquote>\n",converter.convert(tc5_1));
		
		// '> at every line and space after > mark' case
		String[] tc5_2 ={"> Textile, reStructuredText, Grutatext, and EtText – the single biggest source of inspiration for Markdown’s"
				,"> syntax is the format of plain text email."
				,""
		};
		converter.init();
		assertEquals("<blockquote>\n"
				+"  <p>Textile, reStructuredText, Grutatext, and EtText – the single biggest source of inspiration for Markdown’s\n"
				+"  syntax is the format of plain text email.</p>\n"
				+"</blockquote>\n",converter.convert(tc5_2));
		
		// test 6 : Table
		String[] tc6 = {"<table> <tr> <td> Deaedline </td> <td> 23:59, 21 Dec, 2017 </td> </tr>",
				"  <tr> <td> Submission </td> <td> Report, Code, Peer evaluation </td> </tr> </table>",
				""
				};
		converter.init();
		assertEquals("<table>"
		+" <tr> <td> Deaedline </td> <td> 23:59, 21 Dec, 2017 </td> </tr>\n"
		+"  <tr> <td> Submission </td> <td> Report, Code, Peer evaluation </td> </tr>"
		+" </table>\n\n",converter.convert(tc6));
		
		
		// test7 : inlineCode
		String[] tc7 = {"<pre><code> if (x > y)"
				,"  return x ;"
				,"else"
				,"  return y ;"
				,"</code></pre>"
		};
		converter.init();
		assertEquals("<pre><code> if (x &gt; y)\n  return x ;\nelse\n  return y ;\n</code></pre>\n",converter.convert(tc7));
		
		// test 8 : Headline 1
		String[] tc8 = {"Writing Markdown Translator"
				,"========"
		};
		converter.init();
		assertEquals("<h1>Writing Markdown Translator</h1>\n",converter.convert(tc8));
		
		// test 9 : Headline 2
		String[] tc9 = {"Overview"
				,"---------------"
		};
		converter.init();
		assertEquals("<h2>Overview</h2>\n", converter.convert(tc9));
		
		// test 10 : Title 
		String[] tc10 = {"## Ordered list ##"};
		converter.init();
		assertEquals("<h2>Ordered list</h2>\n",converter.convert(tc10));
	
		// test 11 : Edit url
		// '[](url)' case
		String[] tc11_1 = {"Submit the report via [Hisnet](http://hisnet.handong.edu \"Hisnet\")."};
		converter.init();
		assertEquals("<p>Submit the report via <a href=\"http://hisnet.handong.edu\" title=\"Hisnet\">Hisnet</a>.</p>\n",converter.convert(tc11_1));
		
		// '[][1] -> [1]: url' case
		String[] tc11_2 ={"The code of your project should be maintained in [GitHub][1]."
				, "Do not forget to git push once you make git commit."
				,""
				,"[1]: Github.com (GitHub)"
				,""
		};
		converter.init();
		assertEquals("<p>The code of your project should be maintained in <a href=\"Github.com\" title=\"GitHub\">GitHub</a>. Do not forget to git push once you make git commit.</p>"
				+"\n\n",converter.convert(tc11_2));
		
		// image case
		String[] tc11_3={"All copy rights reserved in Handong Global University  ![tag](https://www.handong.edu/site/handong/res/img/logo.png)"
				,""};
		converter.init();
		assertEquals("<p>All copy rights reserved in Handong Global University  <img src=\"https://www.handong.edu/site/handong/res/img/logo.png\" alt=\"tag\"></p>\n",converter.convert(tc11_3));
		
		// test 12 : plain text
		// with *emphasize*
		String[] tc12_1 ={"This assignment askes you to build a program that reads a *Markdown* file and converts it to a HTML file."
				,"You are given a documentation on the Markdown syntax which starts as follows:"
				};
		converter.init();
		assertEquals("<p>This assignment askes you to build a program that reads a <em>Markdown</em> file and converts it to a HTML file. You are given a documentation on the Markdown syntax which starts as follows:</p>\n",converter.convert(tc12_1));
		
		// with \<, \>
		String[] tc12_2={"Markdown wraps a code block in both \\<pre\\> and \\<code\\> tags. For example:"};
		converter.init();
		assertEquals("<p>Markdown wraps a code block in both \\<pre\\> and \\<code\\> tags. For example:</p>\n",converter.convert(tc12_2));
		
		// pure plain text
		String[] tc12_3={"Pre-formatted code blocks are used for writing about programming or markup source code."
				,"Rather than forming normal paragraphs, the lines of a code block are interpreted literally."
				,""
		};
		converter.init();
		assertEquals("<p>Pre-formatted code blocks are used for writing about programming or markup source code. Rather than forming normal paragraphs, the lines of a code block are interpreted literally.</p>\n",converter.convert(tc12_3));
		
	}
}
