package SE_package;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;

import static java.lang.String.*;

public class mdparser {
    static LinkedList<String> options = new LinkedList<String>();
    static LinkedList<String> mdFiles = new LinkedList<String>();
    static LinkedList<String> htmlName = new LinkedList<String>();

    static String[] commands = {"-pl", "-st", "-sl"};

    public static void main(String args[]) 
    {
    	//options.add("-pl");
        mdparser mdparser_instance = new mdparser();
        List<String> readBuffer = new ArrayList<String>();           //array list to save file
        String[] file;                                              //final string array to convert
        String line;                                                //read line by line


        BufferedReader reader = null;
        BufferedWriter writer = null;
        String result = null;

        Converter converter;

        for (int i = 0; i < args.length; i++) 
        {
            if (args[i].charAt(0) == '-') {
                if (args[i].equals("-h") || args[i].equals("--help")) 
                {
                    System.out.print("This is the help message");
                    return;
                }
				else					
				{
                    //if valid option, add to command list
                    if(mdparser_instance.isValidOption(args[i]))
                    {
                	    options.add(args[i]);                	 
                    }
                    //check if the entered option is valid
                    else 
                    {
                    	System.out.print("mdparser: invalid option.. Try 'mdparser --help' for more information");
                        return;
                    }

				}
            } 
            else if (args[i].substring(args[i].length() - 2).equals("md")) 
            {
                mdFiles.add(args[i]);

                if (i + 1 < args.length && args[i + 1].substring(args[i + 1].length() - 4).equals("html"))
                {
                    htmlName.add(args[i + 1]);
                    i++;
                }
                else
                {
                		htmlName.add(args[i].substring(0, args[i].length() - 2) + "html");
                }
            }
            else
            {
                System.out.print("Please input a file extention of '.md' for conversion");
		        return;
            }
        }

        //Read file and convert
        for (int i = 0; i < mdFiles.size(); i++)
        {
            try
            {
                File directory = new File(mdFiles.get(i));
                reader = new BufferedReader(new FileReader(directory));

                //read file and save to arraylist buffer
                while ((line = reader.readLine()) != null)
                {
                    readBuffer.add(line);
                }
                file = readBuffer.toArray(new String[0]);               //convert arraylist to array of strings


                writer = new BufferedWriter(new FileWriter(htmlName.get(i)));
                converter = new Converter();
                result = converter.convert(file);

                System.out.println(result);
                writer.write(result);

                System.out.println("Created new file(s) at \'../doc/\'!");

            } 
            catch (IOException e) 
            {
                System.out.println("File cannot be found!");
                e.printStackTrace();
            } 
            finally 
            {
                try 
                {
                    if (reader != null)
                    {
                        reader.close();
                    }
                    if (writer != null) 
                    {
                        writer.close();
                    }
                } 
                catch (IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean isValidOption(String option)
    {
        Boolean flag = false;
        for (int j = 0; j < commands.length; j++)
        {
            if (option.equals(commands[j]))
            {
                flag = true;
                //insert option action here
            }
        }
        return flag;
    }
}