import java.io.*;

public class Converter {
	private String buffer, splice_buffer, instring_buffer, result, convertResult;
	private StringBuffer return_write;
	boolean doNeedInString, IsPrefixTest;
	InString instring;
	
	// Test instance
	Block_DetectList detectList;
	Prefix_DetectTitle detectTitle;
	Prefix_DetectLine detectLine;
	Prefix_DetectLessthan detectLessthan;
	
	Converter(){
		buffer = null;
		splice_buffer = null;
		instring_buffer = null;
		result = null;
		convertResult = null;
		doNeedInString = true;
		IsPrefixTest = false;
		return_write = new StringBuffer();
		instring = new InString();
		
		detectList = new Block_DetectList();
		detectTitle = new Prefix_DetectTitle();
		detectLine = new Prefix_DetectLine();
		detectLessthan = new Prefix_DetectLessthan();
	}
	
	public void initBuffer(){
		this.splice_buffer = null;
		this.instring_buffer = null;
		this.result = null;
		this.doNeedInString = true;
		this.IsPrefixTest = false;
	}
	
	public void setBuffer(String testBuffer){
		buffer = testBuffer;
	}
	
	public void PrintBuffer(){
		System.out.println("CURRENT BUFFER = splice:" + this.splice_buffer
				+ ", instring:" + this.instring_buffer
				+ ", result:" + this.result);
	}

	//finds the place where the Marker URL is and replaces it with correct html code
	private void EditURL (String ref) {
		String word, link="";
		int index, start, end;

		index = return_write.lastIndexOf(instring.getRef());
		start = index;
		end = index+4;

		while(return_write.charAt(end) != '#') {
			link = link + return_write.charAt(end);
			end++;
		}

		word = ref + link + "</a>";

		return_write.replace(start, end+1, word);

		instring.resetRef();
	}
	
	public String run(BufferedReader inputReader){	
		// input reader
	    BufferedReader reader = inputReader;
	    
	   
	    try {
			while ((buffer = reader.readLine()) != null) {
			    System.out.println(buffer);
			    
			    // init buffers except var 'buffer'
			    initBuffer();
			    
			    convertResult = Convert(buffer);
			    System.out.println("convertResult :"+convertResult+"\n");

			} // while
			
			// return final result, return_write[]
			return return_write.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //System.out.println("This is error case in Converter");
		return null;	
	}
	
	public String Convert(String buffer){
	    // Empty line
	    if(buffer.length() == 0){
	    	// check still lessthan state
			if(detectLessthan.getIsWhileChecking()){
				result = "</blockquote>" + "\n";
				
				// end signal of lessthan case
				detectLessthan.setIsWhileChecking(false);
				//System.out.println("IsWhileChecking off");
			}
			// just empty line
			else{
				//System.out.println("deal with Enter");
				result = "\n";
			}
	    	
	    	// Append return_write
			return_write.append(result);
			return result;
	    }else{
	    	// Prefix TEST -> get result now, do not need InString Test
	    	// Line Check
	    	if(detectLine.isLine(buffer)){
	    		// here is in Line Case and do not need to do InString Test
	    		//System.out.println("Here is in line Detect");
	    		
	    		doNeedInString = false;
	    		IsPrefixTest = true;
	    		
	    		result = detectLine.transformToHTML();
	    		
	    		// Append return_write
				return_write.append(result + "\n");
				
				return result;
	    	}
	    	// Title Check
	    	else if(detectTitle.isTitle(buffer) && !IsPrefixTest){
	    		// here is in Title Case and do not need to do InString Test
	    		//System.out.println("Here is in Title Detect");
			
	    		doNeedInString = false;
	    		IsPrefixTest = true;
			
	    		result = detectTitle.transformToHTML(buffer, detectTitle.getSharpNum());
	    		
	    		// Append return_write
				return_write.append(result + "\n");
				
				return result;
	    	}
	    	// Lessthan Check
			else if(detectLessthan.isLessthan(buffer) && !IsPrefixTest){
				// here is in Lessthan Case and do not need to do InString Test
				//System.out.println("Here is in Lessthan Detect");
			
				doNeedInString = false;
				IsPrefixTest = true;
			
				result = detectLessthan.transformToHTML(buffer, detectLessthan.getOpt());
				
				// Append return_write
				return_write.append(result + "\n");
				
				return result;
			}
	    	// BLOCK TEST -> need InString Test
	    	else if(!IsPrefixTest){ 
	    		// List Check
	    		if(detectList.isList(buffer)){
	    			// here is in List Case and need to do InString Test
	    			//System.out.println("Here is in list Detect");
			
	    			doNeedInString = true;
			
	    			// splice buffer and send it to InString Test
	    			splice_buffer = detectList.spliceBuffer(buffer);
	    			
	    			// transform to html
	    			splice_buffer = detectList.transformToHTML(splice_buffer, detectList.getListOption());
			
	    			System.out.println("splice_buffer : " + splice_buffer);
	    		}
		
		
	    		// InString Test only when doNeedInString is true
	    		if(doNeedInString == true){
	    			//System.out.println("Here is InString Test");
	    			// update result
	    			//PrintBuffer();
	    			
					// just string without MD syntax
					if(splice_buffer != null){
						System.out.println("STRING TEST 1");
						result = instring.init(splice_buffer);
					}
					if(splice_buffer == null && result == null){
						System.out.println("STRING TEST 2");
						result = instring.init(buffer);			
					}
	    		}
	
	    		//System.out.println("result :" + result);
	    		//System.out.println();
		
			if(instring.getRef() == null) {
				// Append return_write
				return_write.append(result + "\n");
			}
			else {
			    	EditURL(result);
			}	
	    		//System.out.println("End Roop in block-instring-wrap");
	    		
	    		return result;
	    	// prefix case
	    	}else{
	    		//System.out.println("prefix result :" + result);
	    		//System.out.println();
		
	    		// Append return_write
	    		return_write.append(result + "\n");
	
	    		//System.out.println("End Roop in prefix");
	    		
	    		return result;
	    	}
	    
	    } // blank if condition
	}
}
