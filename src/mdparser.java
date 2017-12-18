import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class mdparser {
    public static void main(String args[]) {
        LinkedList<String> options = new LinkedList<String>();
        LinkedList<String> mdFiles = new LinkedList<String>();
        LinkedList<String> htmlName = new LinkedList<String>();
        Converter converter;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String[] result;

        String[] commands = {"-pl", "-st", "-sl", "-h", "--help"};

        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i].equals("-h") || args[i].equals("--help")) {
                    System.out.println("This is the help message");
                    System.exit(0);
                }
                options.add(args[i]);
            } else if (args[i].substring(args[i].length() - 2).equals("md")) {
                mdFiles.add(args[i]);

                if (i + 1 < args.length && args[i + 1].substring(args[i + 1].length() - 4).equals("html")) {
                    htmlName.add(args[i + 1]);
                    i++;
                } else {
                    htmlName.add(args[i].substring(0, args[i].length() - 2) + "html");
                }
            } else {
                System.out.println("Please input a file extention of '.md' for conversion");
                System.exit(0);
            }
        }

        for (int i = 0; i < options.size(); i++) {
            Boolean flag = false;
            for (int j = 0; j < commands.length; j++) {
                if (options.get(i).equals(commands[j])) {
                    flag = true;
                    //insert option action here
                }
            }
            if (!flag) {
                System.out.println("ConvSystem: invalid option -- " + "'" + options.get(i) + "'");
                System.out.println("Try 'ConvSystem --help' for more information");
                System.exit(0);
            }
        }


        //Read file and convert
        for (int i = 0; i < mdFiles.size(); i++) {
            try {
                File directory = new File(mdFiles.get(i));
                reader = new BufferedReader(new FileReader(directory));
                writer = new BufferedWriter(new FileWriter("../doc/" + htmlName.get(i)));

                converter = new Converter(reader);
                result = converter.run();

                for (int j = 0; j < result.length(); j++) {
                    writer.write(result[j]);
                    writer.newline();
                }
                System.out.println("Created new file(s) at \'../doc/\'!");

            } catch (IOException e) {
                System.out.println("File cannot be found!");
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
