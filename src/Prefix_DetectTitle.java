
public class Prefix_DetectTitle {
private int sharpNum;
	
	Prefix_DetectTitle(){
		sharpNum = 0;
	}
	
	public int getSharpNum(){
		return sharpNum;
	}
	
	// check whether 'buffer' is title or not
	public boolean isTitle(String buffer){
		// init sharpNum
		sharpNum =0;
		
		// return true in cases of line, such as series of '#'. 
		if(buffer.charAt(0) == '#'){
			sharpNum++;
			
			// check the number of sharp and update var sharpNum
			for(int i = 1; i < buffer.length()/2; i++){
				if(buffer.charAt(i) == '#')
					sharpNum++;
			}
			
			return true;
		// return false if 'buffer' is not a title.
		}else{
			return false;
		}
	}
	
	// transform to html code
	public String transformToHTML(String buffer, int sharpNum){
		String splice_buffer, result=" ";
		String[] temp;
		
		splice_buffer = buffer.substring(sharpNum);
		
		// split based on whitespace
		temp = splice_buffer.split("\\s");
		
		for(int i = 0; i < temp.length; i++){
			if(temp[i].contains("#")){
				// do nothing
			}
			else {
				result = result + temp[i] + " ";
			}
		}

		return "<h" + sharpNum + ">" + result.substring(1) + "</h" + sharpNum + ">";
	}
}
