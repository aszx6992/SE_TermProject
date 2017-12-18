
public class DetectLessthan {
	
	public int lineCount = 0;
	public String result = "";
	public boolean isWhileChecking = false;
	

public String transformToHTML(String line, int n) {
			
		switch(n){
			
			case 0 :
				lineCount++;
				isWhileChecking=false;
				return "</blockquote>";
				
			case 1 :
				return "<p>"+line.substring(1,line.length())+"</p>";
				
			case 2 :
				return "<p>"+line+"</p>";
				
			case 3 :
				isWhileChecking = true;
				return "<blockquote>"+line.substring(1,line.length());
							
		}
		return "NOTHING";
	}
}
