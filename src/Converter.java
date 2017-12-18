import java.io.*;

public class Converter {
	private String buffer, splice_buffer, instring_buffer, result;
	private StringBuffer return_write;
	boolean doNeedInString, IsPrefixTest;
	
	Converter(){
		buffer = null;
		splice_buffer = null;
		instring_buffer = null;
		result = null;
		doNeedInString = true;
		IsPrefixTest = false;
		return_write = new StringBuffer();
	}
	
	public void initBuffer(){
		this.splice_buffer = null;
		this.instring_buffer = null;
		this.result = null;
		this.doNeedInString = true;
		this.IsPrefixTest = false;
	}
	
	public void PrintBuffer(){
		System.out.println("CURRENT BUFFER = splice:" + this.splice_buffer
				+ ", instring:" + this.instring_buffer
				+ ", result:" + this.result);
	}
	
	public String run(BufferedReader inputReader){
		// Test instance
		Block_DetectList detectList = new Block_DetectList();
		Prefix_DetectTitle detectTitle = new Prefix_DetectTitle();
		Prefix_DetectLine detectLine = new Prefix_DetectLine();
		
		// input reader
	    BufferedReader reader = inputReader;
	    
	   
	    try {
			while ((buffer = reader.readLine()) != null) {
			    System.out.println(buffer);
			    
			    // init buffers except var 'buffer'
			    initBuffer();
			    
			    // Empty line
			    if(buffer.length() == 0){
			    	// here is in Blank Case and do not need to do InString Test
					System.out.println("Here is in Blank Detect");
			    	result = "\n";
			    	
			    	// Append return_write
					return_write.append(result + "\n");
			    }else{
			    	// Prefix TEST -> 바로 result나오는거. instring 필요없고.
			    	// Line Check
			    	if(detectLine.isLine(buffer)){
			    		// here is in Line Case and do not need to do InString Test
			    		System.out.println("Here is in line Detect");
			    		
			    		doNeedInString = false;
			    		IsPrefixTest = true;
			    		
			    		result = detectLine.transformToHTML();
			    		
			    		// Append return_write
						return_write.append(result + "\n");
			    	}
			    	// Title Check
			    	else if(detectTitle.isTitle(buffer) && !IsPrefixTest){
			    		// here is in Title Case and do not need to do InString Test
			    		System.out.println("Here is in Title Detect");
					
			    		doNeedInString = false;
			    		IsPrefixTest = true;
					
			    		result = detectTitle.transformToHTML(buffer, detectTitle.getSharpNum());
			    	}
			    	// BLOCK TEST -> instring test를 거쳐야 되는 거고.
			    	else if(!IsPrefixTest){ 
			    		// List Check
			    		if(detectList.isList(buffer)){
			    			// here is in List Case and need to do InString Test
			    			System.out.println("Here is in list Detect");
					
			    			doNeedInString = true;
					
			    			// splice buffer and send it to InString Test
			    			splice_buffer = detectList.spliceBuffer(buffer);
					
			    			System.out.println("splice_buffer : " + splice_buffer);
			    		}
				
				
			    		// InString Test only when doNeedInString is true
			    		if(doNeedInString == true){
			    			System.out.println("Here is InString Test");
			    			
			    			// instring method is here
			    			// instring_buffer = INSTRING_TEST(splice_buffer)
					
			    			// update result
			    			PrintBuffer();
							// just string without MD syntax
							if(splice_buffer != null && result == null)
								instring_buffer = splice_buffer;
							if(splice_buffer != null && result != null)
								instring_buffer = splice_buffer;
							if(splice_buffer == null && result == null)
								result = buffer;			
			    		}
			
				
			    		// Block Test has to wrap after InString Test.
			    		// List Wrapping
			    		if(detectList.isList(buffer))
			    			result = detectList.transformToHTML(instring_buffer, detectList.getListOption());
			    		
			    		System.out.println("result :" + result);
			    		System.out.println();
				
			    		// Append return_write
			    		return_write.append(result + "\n");
			
			    		System.out.println("End Roop in block-instring-wrap");
				
			    	}else{
			    		System.out.println("prefix result :" + result);
			    		System.out.println();
				
			    		// Append return_write
			    		return_write.append(result + "\n");
			
			    		System.out.println("End Roop in prefix");
			    	}
			    
			    } // blank if condition
			} // while
			
			// return final result, return_write[]
			return return_write.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("This is error case in Converter");
		return null;	
	}
}
