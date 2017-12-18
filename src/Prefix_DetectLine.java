
public class Prefix_DetectLine {
	// chk whether 'buffer' is line or not. If 'buffer' is a line, then return true.
	public boolean isLine(String buffer){
		// return true in cases of line, such as '=====' or '- - -' or '----'. 
		if(buffer.charAt(0) == '=' 
				|| (buffer.charAt(0) == '-' && buffer.charAt(2) == '-')
				|| (buffer.charAt(0) == '-' && buffer.charAt(1) == '-')){
			return true;
		// return false if 'buffer' is not a line.
		}else{
			return false;
		}
	}
	
	// transform to html code
	public String transformToHTML(){
		return "<hr>";
	}
}
