
public class Prefix_DetectLessthan {
	private String result;
	private boolean isWhileChecking;
	private int opt;
	  
	Prefix_DetectLessthan(){
		result = "";
		opt = -1;
	}
	
	public int getOpt(){
		return opt;
	}
	
	public void setIsWhileChecking(boolean bool){
		isWhileChecking = bool;
	}
	
	public boolean getIsWhileChecking(){
		return isWhileChecking;
	}
	
	public boolean isLessthan(String buffer){
		System.out.println("Checking state:"+this.isWhileChecking);
		// recognize '>' and continuing tag is on
		if(this.isWhileChecking==false && buffer.charAt(0) == '>'){
			this.opt = 3;
			this.isWhileChecking = true;
			System.out.println("isWhileChecking on");
			return true;
		}
		// if continuing tag is on, check whether it still in lessthan state or not. 
		else if(this.isWhileChecking==true && buffer.charAt(0) == '>'){
			this.opt = 1;
			return true;
		}else if(this.isWhileChecking==true && buffer.charAt(0) != '>'){
			this.opt = 2;
			return true;
		}
		
		System.out.println("Not Lessthan case");
		return false;
	}
	
	public String transformToHTML(String buffer, int n) {
	         
	      switch(n){       
	         case 1 :
	            return "<p>"+buffer.substring(1,buffer.length())+"</p>";
	            
	         case 2 :
	            return "<p>"+buffer+"</p>";
	            
	         case 3 :
	            return "<blockquote>"+buffer.substring(1,buffer.length());
	                     
	      }
	      return "NOTHING";
	   }
}
