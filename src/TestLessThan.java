
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class TestLessThan {
	

	public static void main(String[] args) {
		
		Path test = Paths.get("book.txt");
		
		String result = "";
		String line ="";
		int lineCount = 0;
		
		
		try {
			BufferedReader reader = Files.newBufferedReader(test);
			
			while((line = reader.readLine())!=null) {
				
				if(line.charAt(0)=='>') {
					result += "<blockquote>";
					result += line.substring(1,line.length());
					
					while(lineCount<1) {
						
						
						line = reader.readLine();						
						
						if(line.length() == 0) {
							lineCount++;
							System.out.println("blank line detected");
						}
						else {
							result += "<p>";
							
							if(line.charAt(0)=='>')
								result += line.substring(1,line.length());
							else
								result += line;
							result += "</p>";
						}
						
						
					}
					
				result+="</blockquote>";
				}
				else {
					result += line;
				}
			}
			
			
			
			
		}
		catch(IOException e) {
			System.out.println("Could not read file");
		}
		
		
		System.out.println(result);
		
		
				
	}
}
