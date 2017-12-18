import java.io.*;

public class Block_DetectList {
	private boolean stillInList;
	private boolean justNested;
	private boolean nestedWithSymbol;
	private int listOption;
	
	Block_DetectList(){
		stillInList = false;
		justNested = false;
		nestedWithSymbol = false;
		listOption = -1;
	}
	
	public boolean getStillInList(){
		return stillInList;
	}
	
	public boolean getJustNested(){
		return justNested;
	}
	
	public boolean getNestedWithSymbol(){
		return nestedWithSymbol;
	}
	
	public int getListOption(){
		return listOption;
	}
	
	// chk whether 'buffer' is list or not. If 'buffer' is a list, then return true.
	public boolean isList(String buffer){
		
		// start new list
		if(!this.stillInList){
			// list Check
			return this.listCheck(buffer);	
		}
		// continue list
		else if(this.stillInList && this.justNested == false && this.nestedWithSymbol == false){
			// list Check
			return this.listCheck(buffer);
		}
		// nested list check
		else{
			// list Check
			return this.listCheck(buffer);
		}
	}
	
	public boolean listCheck(String buffer){
		// List check
		// return true in case '+'
		if(buffer.charAt(0) == '+'){
			System.out.println("List : " + buffer.charAt(0));
			this.initBoolean();
			this.stillInList = true;
			this.printBoolean();
			return true;
		}
		// return true in case '*'
		else if(buffer.charAt(0) == '*'){
			int starNum = 0;
						
			// chk whether '*' at charAt(0) is bold symbol or list symbol
			for(int i=0; i<buffer.length(); i++){
				if(buffer.charAt(i) == '*')
					starNum++;
			}
			// If starNum is odd, then '*' at charAt(0) is list symbol and so return true.
			if(starNum % 2 == 1){
				System.out.println("List : " + buffer.charAt(0));
				this.initBoolean();
				this.stillInList = true;
				this.printBoolean();
				return true;	
			}
					
			// If starNum is even, then '*' at charAt(0) is bold symbol
			else{
				this.printBoolean();
				return false;
			}
		}
		// return true if case '-'
		else if(buffer.charAt(0) == '-'){
			System.out.println("List : " + buffer.charAt(0));
			this.initBoolean();
			this.stillInList = true;
			this.printBoolean();
			return true;
		}
		System.out.println("No case in list check. So do nestedCheck");
		return this.nestedCheck(buffer);
	}
	
	public boolean nestedCheck(String buffer){
		// nested check
		if(buffer.charAt(0) == ' ' && buffer.charAt(1) == ' '){
			// check having symbol or not
			if(buffer.charAt(2) == '-' || buffer.charAt(2) == '+'){
				// nested with symbol
				System.out.println("nested with symbol : " + buffer.charAt(2));
				this.initNestedBoolean();
				this.stillInList = true;
				this.nestedWithSymbol = true;
				this.printBoolean();
				return true;
			}
			// tag case
			else if(buffer.charAt(2) == '<'){
				System.out.println("tag case.");
				System.out.println("No case in nested test. maybe this is not line or list.");
				this.initBoolean();
				this.printBoolean();
				return false;
			}
			// just nested.
			else{
				System.out.println("just nested");
				this.initNestedBoolean();
				this.stillInList = true;
				this.justNested = true;
				this.printBoolean();
				return true;
			}
		}
		// list end.
		else{
			System.out.println("No case in nested test. maybe this is not line or list.");
			this.initBoolean();
			this.printBoolean();
			return false;
		}
	}
	
	// Do splice. eliminate symbol from buffer and return spliced buffer
	public String spliceBuffer(String buffer){
		// new list
		if(this.getStillInList()  == true
				&& this.getJustNested() == false
				&& this.getNestedWithSymbol() == false){
			System.out.println("Splice buffer in new list case");
			this.listOption = 0;
			return buffer.substring(2);
		}
		// just nested list
		else if(this.getStillInList()  == true
				&& this.getJustNested() == true
				&& this.getNestedWithSymbol() == false){
			System.out.println("Splice buffer in just nested case");
			this.listOption = 1;
			return buffer.substring(2);
		}
		// nested list with symbol
		else if(this.getStillInList()  == true
				&& this.getJustNested() == false
				&& this.getNestedWithSymbol() == true){
			System.out.println("Splice buffer in nested case with symbol");
			this.listOption = 2;
			return buffer.substring(4);
		}
		// not list
		else{
			// do nothing
			System.out.println("This is not list case. Do nothing in spliceBuffer.");
			this.listOption = -1;
			return "Error in spliceBuffer";
		}
	}
	
	// Transform MD to HTML
	public String transformToHTML(String instring_result, int option){
		String result;
		
		switch(option){
		// new list
		case 0 :
			result = "<li>" + instring_result + "</li>";
			return result;
		// just nested list
		case 1 :
			result = "<blockquote><p>" + instring_result + "</p></blockquote>";
			return result;
		// nested list with symbol
		case 2 :
			result = "<ul><li>" + instring_result + "</li></ul>";
			return result;
		}
		
		System.out.println("Error case : transformToHTML method in Detect");
		return "Error in transforming";
	}
	
	public void initBoolean(){
		this.stillInList = false;
		this.justNested = false;
		this.nestedWithSymbol = false;
	}
	
	public void initNestedBoolean(){
		this.justNested = false;
		this.nestedWithSymbol = false;
	}
	
	public void printBoolean(){
		System.out.println("Boolean value : stillInList=" + this.stillInList + ", justNested=" + this.justNested + ", nestedWithSymbol=" + this.nestedWithSymbol);
	}
}
