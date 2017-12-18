import java.io.*;

public class Converter {
	private String buffer, splice_buffer, instring_buffer, result;
	private String[] return_write;
	private int index;
	boolean doNeedInString;
	
	public Converter(){
		buffer = null;
		splice_buffer = null;
		instring_buffer = null;
		result = null;
		doNeedInString = true;
		index = 0;
	}
	
	public int getIndexInClass(){
		return this.index;
	}
	
	public void initBuffer(){
		this.splice_buffer = null;
		this.instring_buffer = null;
		this.result = null;
	}
	
	public String[] run(BufferedReader inputReader){
		// Test instance
		Block_DetectList detectList = new Block_DetectList();
		Prefix_DetectTitle detectTitle = new Prefix_DetectTitle();
		Prefix_DetectLine detectLine = new Prefix_DetectLine();
		
		// input reader
		String curLine;
	    BufferedReader reader = inputReader;

	    try {
			while ((buffer = reader.readLine()) != null) {
			    System.out.println(buffer);
			    
			    // init buffers except var 'buffer'
			    initBuffer();
			    
			    // Prefix TEST -> 바로 result나오는거. instring 필요없고.
				// Line Check
				if(detectLine.isLine(buffer)){
					// here is in Line Case and do not need to do InString Test
					System.out.println("Here is in line Detect");

					doNeedInString = false;
					
					result = detectLine.transformToHTML();
				}
				// Title Check
				else if(detectTitle.isTitle(buffer)){
					// here is in Title Case and do not need to do InString Test
					System.out.println("Here is in Title Detect");
					
					doNeedInString = false;
					
					result = detectTitle.transformToHTML(buffer, detectTitle.getSharpNum());
				}
				// BLOCK TEST -> instring test를 거쳐야 되는 거고.
				// List Check
				else if(detectList.isList(buffer)){
					// here is in List Case and need to do InString Test
					System.out.println("Here is in list Detect");
					
					doNeedInString = true;
					
					// splice buffer and send it to InString Test
					splice_buffer = detectList.spliceBuffer(buffer);
					
					System.out.println("splice_buffer : " + splice_buffer);
				}
				
				
				// InString Test only when doNeedInString is true
				if(doNeedInString == true){
					// instring method is here
					
					// update result
					instring_buffer = splice_buffer;
				}
			
				
				// Block Test has to wrap after InString Test.
				// List Wrapping
				if(detectList.isList(buffer))
					result = detectList.transformToHTML(instring_buffer, detectList.getListOption());
				
			
				System.out.println("result :" + result);
				System.out.println();
				
				// Append return_write
				return_write[getIndexInClass()] = result;
				this.index++;
			}
			
			// return final result, return_write[]
			return return_write;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("This is error case in Converter");
		return null;	
	}
}
