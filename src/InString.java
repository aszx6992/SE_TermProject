public class InString {
    private StringBuffer buffer;
    private String[] inputBuffer;
    private String resultString;

    //constructor
    public InString () {
        buffer= new StringBuffer();
    }

    public String init(String input){
        inputBuffer = input.split("\\s");

        for (int i=0; i<inputBuffer.length; i++) {
            if (inputBuffer[i].length() == 0) {
                buffer.append(" ");
            }
            else if (inputBuffer[i].charAt(0) == '*') {
                //**italicized**
                if (inputBuffer[i].charAt(1) == '*')
                    convItalicize(inputBuffer[i]);
                //*bold*
                else
                    convBold(inputBuffer[i]);
            }
            //`code insert`
            else if (inputBuffer[i].charAt(0) == '`') {
                convCode(inputBuffer[i]);
            }
            //&amp special character
            else if (inputBuffer[i].charAt(0) == '&') {
                convSpecial(inputBuffer[i]);
            }
            else if (inputBuffer[i].substring(0, 2).equals("![")) {
                convImage(inputBuffer[i]);
            }
            //그대로 string buffer에 append 하기
            else {
                buffer.append(inputBuffer[i]+" ");
            }
        }


        resultString = buffer.toString();

        //clear buffer
        buffer.delete(0, buffer.length());

        return resultString;
    }

    private void convBold (String inputBuffer) {
        String word;
        //create new word
        word = "<b>" + inputBuffer.substring(1, inputBuffer.length() - 1) + "</b>";
        //add to StringBuffer buffer
        buffer.append(" "+word);
    }
    private void convItalicize(String inputBuffer) {
        String word;
        //create new word
        word = "<i>" + inputBuffer.substring(2, inputBuffer.length() - 2) + "</i>";
        //add to StringBuffer buffer
        buffer.append(" "+word);
    }

    private void convImage(String inputBuffer) {
        int index = 2;
        String tag, ref, word;

        while(inputBuffer.charAt(index) != ']')
            index++;

        tag = inputBuffer.substring(2, index);
        ref = inputBuffer.substring(index+2, inputBuffer.length() -1);

        word = "<img src=\"" + ref +"\" alt=\"" + tag + "\">";

        //add to StringBuffer buffer
        buffer.append(" "+word);
    }

    private void convCode(String inputBuffer) {
        //add to StringBuffer buffer
        System.out.println("convBold");
    }

    private void convSpecial(String inputBuffer) {
        //add to StringBuffer buffer
        System.out.println("convBold");
    }


}
