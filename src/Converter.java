import java.io.*;
import java.nio.Buffer;

public class Converter {
	private StringBuffer finalResult;
	private InString instring;

	//elements for converting input
	private String[] file;										//input file read as array of strings
	private int lineIndex;										//cur index pointer to line in file
	private int prevIndex;										//starting index of previous append to StringBuffer
	private String line;										//string to read lines
	
	public Converter () {
		finalResult = new StringBuffer();
		instring = new InString();
		lineIndex = 0;
		prevIndex = 0;
	}
	
	public void init(){
		finalResult = new StringBuffer();
		instring = new InString();
		lineIndex = 0;
		prevIndex = 0;
	}
	
	public String convert (String[] file_input) {
		file = file_input;

		//read file line by line
		while (lineIndex < file.length ) {
			//newline
			if(file[lineIndex].length() == 0) {
				finalResult.append("\n");
				lineIndex++;
			}
			//line break
			else if(file[lineIndex].contains("- - -")) {
				finalResult.append("<hr>\n");
				lineIndex++;
			}
			//lists
			else if(file[lineIndex].substring(0, 2).equals("* ") || file[lineIndex].substring(0, 2).equals("- ") || file[lineIndex].substring(0, 2).equals("+ ")){
				line = convertList(0);
				finalResult.append(line + "\n");
			}
			//block quotes
			else if (file[lineIndex].charAt(0)=='>') {
				line = convertBlockQuote();
				finalResult.append(line + "\n");
			}
			//inline codes
			else if (file[lineIndex].charAt(0) == '<') {
				if(file[lineIndex].substring(0, 7).equals("<table>")) {
					line = convertTable();
					finalResult.append(line+"\n");
				}
				else {
					line = convertInlineCode();
					finalResult.append((line+"\n"));
				}
			}
			//line heading H1
			else if (file[lineIndex].contains("===")) {
				line = convertLineHeading(1);
				finalResult.replace(prevIndex, finalResult.length(), line);
			}
			//line heading H2
			else if(file[lineIndex].contains("---")) {
				line = convertLineHeading(2);
				finalResult.replace(prevIndex, finalResult.length(), line);
			}
			//title
			else if(file[lineIndex].charAt(0) == '#') {
				line = convertHeading();
				finalResult.append(line + "\n");
			}
			else if(file[lineIndex].charAt(0) == '[') {
				line = instring.convert(file[lineIndex]);
				EditURL(line);
				lineIndex++;
			}
			//plain text
			else  {
				line = convertPlainText();
				prevIndex = finalResult.length();
				finalResult.append(line + "\n");
			}
		}

		return finalResult.toString();
	}

	private String convertList(int spaceNum) {
		StringBuffer listBuffer = new StringBuffer();
		String word = null;
		listBuffer.append("<ul>\n");

		word = file[lineIndex].substring(spaceNum+2);
		listBuffer.append("<li>"+word);

		lineIndex++;
		while (lineIndex < file.length && file[lineIndex].length() > 0) {

			//when the list starts with -, *, +, wrap with </li>
			if (file[lineIndex].substring(spaceNum, spaceNum + 2).equals("- ") || file[lineIndex].substring(spaceNum, spaceNum + 2).equals("+ ") || file[lineIndex].substring(spaceNum, spaceNum + 2).equals("* ")) {
				listBuffer.append("</li>\n");
				word = "<li>" + file[lineIndex].substring(spaceNum + 2);
			}
			else if (file[lineIndex].substring(0, spaceNum).equals("  ") && Character.isLetter(file[lineIndex].substring(spaceNum).charAt(0))) {
				word = "\n" + file[lineIndex].substring(spaceNum);
			}
			else if (file[lineIndex].substring(spaceNum, spaceNum + 2).equals("  ")) {
				//when the list is just a continuing sentence, append to the previous sentence
				if(Character.isLetter(file[lineIndex].substring(spaceNum+2).charAt(0))) {
					word = "\n" + file[lineIndex].substring(spaceNum+2);
				}
				//recursively wrap lists
				else if (file[lineIndex].substring(spaceNum+2, spaceNum+4).equals("- ") || file[lineIndex].substring(spaceNum+2, spaceNum+4).equals("* ") || file[lineIndex].substring(spaceNum+2, spaceNum+4).equals("+ ")){
					word = "\n" + convertList(spaceNum+2);
					lineIndex--;
				}
			}
			else {
				break;
			}
			listBuffer.append(word);
			lineIndex++;
		}
		listBuffer.append("</li>\n</ul>\n");
		return listBuffer.toString();
	}

	private void EditURL (String ref) {
		String word, link="";
		int index, start, end;

		index = finalResult.lastIndexOf(instring.getRef());

		start = index;
		end = index+4;


		while(finalResult.charAt(end) != '#') {
			link = link +finalResult.charAt(end);
			end++;
		}

		word = ref + link + "</a>";

		finalResult.replace(start, end+1, word);

		instring.resetRef();
	}

	private String convertInlineCode() {
		String word;
		StringBuffer inlineCodeBuffer = new StringBuffer();
		int index = file[lineIndex].lastIndexOf("<code>");
		index = index + 6;

		//split inline code from tags
		word = file[lineIndex].substring(0,index+1);
		inlineCodeBuffer.append(word);

		//convert with instring markdown syntax
		word = instring.convert(file[lineIndex].substring(index+1));
		inlineCodeBuffer.append(word+"\n");

		lineIndex++;

		while (!file[lineIndex].contains("code")) {
			inlineCodeBuffer.append(file[lineIndex] + "\n");
			lineIndex++;
		}

		inlineCodeBuffer.append(file[lineIndex]);
		lineIndex++;

		return inlineCodeBuffer.toString();
	}

	private String convertBlockQuote() {
		StringBuffer blockQuoteBuffer = new StringBuffer();
		String word;
		int index = 0;

		//splice indentation symbol
		while  (!Character.isLetter(file[lineIndex].charAt(index))) {
			index++;
		}
		word = file[lineIndex].substring(index);

		//wrap with blockquote
		blockQuoteBuffer.append("<blockquote>\n  <p>" + word);
		lineIndex++;

		//add line until newline appears
		while(file[lineIndex].length() != 0) {
			//splice indentation if needed
			if(file[lineIndex].charAt(0) == '>')
				word = "  " + file[lineIndex].substring(2);
			else
				word = "  " + file[lineIndex];
			blockQuoteBuffer.append("\n"+ word);
			lineIndex++;
		}

		//wrap with </blockquote>
		blockQuoteBuffer.append("</p>\n</blockquote>");
		lineIndex++;

		return blockQuoteBuffer.toString();
	}

	private String convertTable() {
		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer.append(file[lineIndex] + "\n");

		lineIndex++;

		//keep reading line and append until table ends
		while(file[lineIndex].length() > 0) {
			tableBuffer.append(file[lineIndex] + "\n");
			lineIndex++;
		}

		tableBuffer.append(file[lineIndex]);
		lineIndex++;

		return tableBuffer.toString();
	}

	private String convertHeading() {
		String word = "";
		//number of # (size of heading)
		int numOfSharp = 0;
		int index = 0;

		//count number of # to find size of heading
		while(file[lineIndex].charAt(index) != ' ') {
			numOfSharp++;
			index++;
		}
		index++;

		//get the text between the #'s
		while(file[lineIndex].charAt(index) != '#') {
			word = word + file[lineIndex].charAt(index);
			index++;
		}

		//rebuild word
		word = "<h"+numOfSharp+">" + word.trim() + "</h"+numOfSharp+">";
		lineIndex++;

		return word;
	}

	private String convertLineHeading(int heading_size) {
		String word;

		//convert according to markdown syntax
		word = instring.convert(file[lineIndex-1]);
		word = "<h"+heading_size+">" + word + "</h"+heading_size+">\n";
		lineIndex++;
		return word;
	}

	//receives a plain text string, convert markdown syntax, wraps in <p> </p>
	private String convertPlainText () {
		StringBuffer plainTextBuffer = new StringBuffer();
		String buffer;

		//check for inline markdown syntax
		buffer = instring.convert(file[lineIndex]);

		//wrap front part of plain text with <p>
		plainTextBuffer.append("<p>" + buffer);

		lineIndex++;

		//continue reading lines until not a letter
		while (lineIndex < file.length && file[lineIndex].length() > 0 && Character.isLetter(file[lineIndex].charAt(0))) {
			//check for inline markdown syntax
			buffer = instring.convert(file[lineIndex]);
			plainTextBuffer.append(" " + buffer);
			lineIndex++;
		}

		if(lineIndex < file.length && file[lineIndex].length() == 0){
			lineIndex++;
		}

		plainTextBuffer.append("</p>");                            //finish wrapping with </p>

		return plainTextBuffer.toString();
	}
}
