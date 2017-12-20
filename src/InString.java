
public class InString {
    StringBuffer buffer;
    String[] inputBuffer;
    String resultString;
    int i;

    private String URLref;

    //constructor
    public InString() {
        buffer = new StringBuffer();
        URLref = null;
    }

    //receives a line and checks for markdown syntax
    public String convert(String input) {
        inputBuffer = input.split("\\s");
        String word = null;

        for (i = 0; i < inputBuffer.length; i++) {
            //blank space
            if (inputBuffer[i].length() == 0) {
                buffer.append(" ");
                continue;
            } else if (inputBuffer[i].charAt(0) == '*') {
                //**bold**
                if (inputBuffer[i].charAt(1) == '*') {
                    word = convBold(inputBuffer[i]);
                }
                //*emphasis*
                else {
                    word =convItalicize(inputBuffer[i]);
                }
            //<code></code>
            } else if (inputBuffer[i].charAt(0) == '`') {
                word = convCode();
            //image
            } else if (inputBuffer[i].charAt(0) == '!') {
                word = convImage(inputBuffer[i]);
            //URL
            } else if (inputBuffer[i].charAt(0) == '[') {
                int type = whatURL(inputBuffer[i]);
                if (type == 0)
                    word = convUrl(inputBuffer);
                else if (type == -1) {
                    word = convRefURL(inputBuffer);
                    buffer.append(word);
                    break;
                } else
                    word = convMarkerURL(inputBuffer[i]);
			} else if (!Character.isLetter(inputBuffer[i].charAt(0)) && inputBuffer[i].length() == 1) {
                word = convSpecial(inputBuffer[i]);
            }else {
                word = inputBuffer[i];
            }

            buffer.append(word + " ");
        }

        resultString = buffer.toString().trim();

        //clear buffer
        buffer.delete(0, buffer.length());

        return resultString;
    }

    private String convSpecial(String inputBuffer) {
		String word = null;
        if (inputBuffer.charAt(0) == '&') {
            word = inputBuffer + "amp;";
        }
        else if (inputBuffer.charAt(0) == '>') {
            word = "&gt;";
        }
        else if (inputBuffer.charAt(0) == '<') {
            word = "lt;";
        }
        else
            word = inputBuffer;

		return word;
	}

    private String convBold(String inputBuffer) {
        String word;
        //create new word
        word = "<b>" + inputBuffer.substring(2, inputBuffer.length() - 2) + "</b>";

        return word;
    }

    private String convItalicize(String inputBuffer) {
        String word;
        //create new word
        word = "<em>" + inputBuffer.substring(1, inputBuffer.length() - 1) + "</em>";

        return word;
    }

    private String convImage(String inputBuffer) {
        int index = 2;
        String tag, ref, word;

        while (inputBuffer.charAt(index) != ']')
            index++;

        tag = inputBuffer.substring(2, index);
        ref = inputBuffer.substring(index + 2, inputBuffer.length() - 1);

        word = "<img src=\"" + ref + "\" alt=\"" + tag + "\">";

        return word;
    }

    private String convCode() {
        String word = "";
        String rest = "";
        int charIndex = 1;

        //read the text between backticks
        while (inputBuffer[i].charAt(charIndex) != '`') {
            word = word + inputBuffer[i].charAt(charIndex);
            charIndex++;
            if (charIndex >= inputBuffer[i].length()) {
                charIndex = 0;
                word = word + " ";
                i++;
            }
        }

        charIndex++;

        while(charIndex < inputBuffer[i].length()){
            rest = rest + inputBuffer[i].charAt(charIndex);
            charIndex++;
        }

        word = "<code>"+ word + "</code>" + rest;

        return word;
    }

    private String convMarkerURL(String inputBuffer) {
        String word;
        String title ="";
        String ref ="";
        int index =0;
        while(inputBuffer.charAt(index) != ']'){
            title = title + inputBuffer.charAt(index);
            index++;
        }
        title = title + "#";


        index ++;

        while(inputBuffer.charAt(index) != ']') {
            ref = ref + inputBuffer.charAt(index);
            index ++;
        }

        index++;
        while(index < inputBuffer.length()){
            title = title + inputBuffer.charAt(index);
            index++;
        }

        ref = ref + "]";
        word = ref + title;
        return word;
    }

    private String convRefURL(String[] inputBuffer) {
        String add = inputBuffer[1];
        String title = inputBuffer[2].substring(1, inputBuffer[2].length()-1);
        String word = "<a href=\""+add+"\" title=\"" + title + "\">";
        URLref = inputBuffer[0].substring(0, inputBuffer[0].length()-1);
        return word;

    }

    private String convUrl(String[] inputBuffer) {
        int letterIndex = 1;
        String link = "";
        String add = "";
        String title = "";
        String rest = "";
        String word;

        //keep reading the link until until the end of the bracket
        while (letterIndex < inputBuffer[i].length() && inputBuffer[i].charAt(letterIndex) != ']') {
            link = link + inputBuffer[i].charAt(letterIndex);
            letterIndex++;
        }

        //if the pointer has finished iterating through current word, move onto next
        if(letterIndex >= inputBuffer[i].length()){
            i++;
            letterIndex = 1;
        }
        else
            letterIndex = letterIndex + 2;

        //read the hyperlink until space
        while (letterIndex < inputBuffer[i].length() && inputBuffer[i].charAt(letterIndex) != ' ') {
            add = add + inputBuffer[i].charAt(letterIndex);
            letterIndex++;
        }

        //if the pointer has finished iterating through current word, move onto next
        if(letterIndex >= inputBuffer[i].length()) {
           i++;
            letterIndex = 1;
        }
        else
            letterIndex = letterIndex + 2;

        //read the title
        while (letterIndex < inputBuffer[i].length() && inputBuffer[i].charAt(letterIndex) != '"') {
            title = title + inputBuffer[i].charAt(letterIndex);
            letterIndex++;
        }

        letterIndex = letterIndex + 2;

        //get the rest of the sentence
        while (letterIndex < inputBuffer[i].length()) {
            rest = rest + inputBuffer[i].charAt(letterIndex);
            letterIndex++;
        }

        //build hyperlink containing sentence
        word ="<a href=\"" + add + "\" title=\"" + title + "\">" + link + "</a>" + rest;
        return word;
    }

    //checks for what type of URL
    private int whatURL(String word){
        int index =0;

        while(word.charAt(index) != ']')
            index++;
        if(word.charAt(index+1) == '[')
            return 1;                   //URL calling for reference URL
        else if(word.charAt(index+1) == ':')
            return -1;                  //reference URL (at the end of file)

        return 0;                       //regular URL
    }

    public String getRef () {
        return URLref;
    }
    public void resetRef() {
        URLref = null;
    }

}

