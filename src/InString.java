public class InString {
    boolean url_flag = false;         //만약 더러운 url이면 true. 아니면 false 유지
    String text_url;
    String number;
    String update_string;

    StringBuffer buffer;
    String[] inputBuffer;
    String resultString;

    //constructor
    public InString(String input) {
        buffer = new StringBuffer();
    }

    public String init(String input) {
        inputBuffer = input.split("\\s");         //단어 단위의 배열

        for (int i = 0; i < inputBuffer.length; i++) {
            if (inputBuffer[i].length() == 0) {
                buffer.append(" ");
            } else if (inputBuffer[i].charAt(0) == '*') {
                //**italicized**
                if (inputBuffer[i].charAt(1) == '*') {
                    convItalicize(inputBuffer[i]);
                }
                //*bold*
                else {
                    convBold(inputBuffer[i]);
                }
            } else if (inputBuffer[i].charAt(0) == '`') {
                convCode(inputBuffer);
            } else if (inputBuffer[i].charAt(0) == '&') {
                convSpecial(inputBuffer[i]);
            } else if (inputBuffer[i].charAt(0) == '!') {
                convImage(inputBuffer[i]);
            } else if (inputBuffer[i].charAt(0) == '[') {
                convUrl(inputBuffer);
            } else {
                buffer.append(inputBuffer[i] + " ");
            }
        }

        resultString = buffer.toString();

        //clear buffer
        buffer.delete(0, buffer.length());

        return resultString;
    }

    private void convBold(String inputBuffer) {
        String word;
        //create new word
        word = "<b>" + inputBuffer.substring(1, inputBuffer.length() - 1) + "</b>";
        //add to StringBuffer buffer
        buffer.append(word + " ");
    }

    private void convItalicize(String inputBuffer) {
        String word;
        //create new word
        word = "<i>" + inputBuffer.substring(2, inputBuffer.length() - 2) + "</i>";
        //add to StringBuffer buffer
        buffer.append(word + " ");
    }

    private void convImage(String inputBuffer) {
        int index = 2;
        String tag, ref, word;

        while (inputBuffer.charAt(index) != ']')
            index++;

        tag = inputBuffer.substring(2, index);
        ref = inputBuffer.substring(index + 2, inputBuffer.length() - 1);

        word = "<img src=\"" + ref + "\" alt=\"" + tag + "\">";

        //add to StringBuffer buffer
        buffer.append(word + " ");
    }

    private void convSpecial(String inputBuffer) {
        String word;
        if(inputBuffer.substring(1, 5).equals("amp;")) {
            word = "&";
        }
        else
            word = inputBuffer;

        buffer.append(word + " ");
    }

    private void convCode(String[] inputBuffer) {
        buffer = new StringBuffer();
        int i = 0;
        int backtick = -1;


        for (i = 0; i < inputBuffer.length; i++) {
            backtick = inputBuffer[i].indexOf("`");

            if (backtick == 0) {
                buffer.append("<code>");
                String word = inputBuffer[i].substring(1);

                buffer.append(word + " ");
            }
            if (backtick == inputBuffer[i].length() - 1) {
                String word = inputBuffer[i].substring(0, inputBuffer[i].length() - 1);

                buffer.append(word);
                buffer.append("</code> ");

            }
            if (backtick != 0 && backtick != inputBuffer[i].length() - 1) {
                if (i == inputBuffer.length - 1) {
                    buffer.append(inputBuffer[i]);
                } else {
                    buffer.append(inputBuffer[i] + " ");
                }
            }
        }
    }

    private void convUrl(String[] inputBuffer) {
        buffer = new StringBuffer();

        boolean parenth_open = false;
        int i = 0;
        int sq_bracket = -1;


        for (i = 0; i < inputBuffer.length; i++) {
            if (inputBuffer[i].contains("[") && inputBuffer[i].contains("("))      //1번 case 경우
            {
                buffer.append("<a href=\"");

                sq_bracket = inputBuffer[i].indexOf("]");
                text_url = inputBuffer[i].substring(1, sq_bracket);         //[Hisnet] <-안의 내용

                int parenth = inputBuffer[i].indexOf("(");
                parenth_open = true;

                String url = inputBuffer[i].substring(parenth + 1, inputBuffer[i].length());   // http://hisnet.handong.edu

                buffer.append(url + "\" ");
            }

            if (inputBuffer[i].contains(")") && parenth_open == true)            //1번 case 경우
            {
                int quote = inputBuffer[i].substring(1, inputBuffer[i].length() - 1).indexOf("\"");

                String title = inputBuffer[i].substring(1, quote + 1);
                String rest = inputBuffer[i].substring(quote + 3);
                buffer.append("title=\"" + title + "\">" + text_url + "</a>" + rest);
            }


            if (inputBuffer[i].substring(1, inputBuffer[i].length() - 1).contains("["))         //2번 case경우
            {
                boolean index_flag = false;
                int sq_bracket1 = 0;
                int sq_bracket2 = 0;

                for (int j = 0; j < inputBuffer[i].length(); j++) {
                    if (inputBuffer[i].charAt(j) == ']') {
                        if (index_flag == false) {
                            sq_bracket1 = j;
                            index_flag = true;
                        }

                        sq_bracket2 = j;

                    }
                }

                text_url = inputBuffer[i].substring(1, sq_bracket1);                           //[Github] <-안의 내용

                //number = inputBuffer[i].substring(sq_bracket1 + 1, sq_bracket2 - 1);            //number는 [1] 안의 1을 저장

                set_url(true);
            }

            if (inputBuffer[i].contains("[") && inputBuffer[i].contains(":") && get_url() == true)      //2번 case경우
            {
                buffer.append("<a href=\"");

                String real_url = inputBuffer[i + 1];                                             // http://www.handong.edu
                String title = inputBuffer[i + 2].substring(1, inputBuffer[i + 2].length() - 1);            // Handong

                buffer.append(real_url + "\" " + "title=\"" + title + "\">" + text_url + "</a>");

                url_flag = false;
            }
        }
    }

    private boolean get_url() {
        return this.url_flag;
    }

    private void set_url(boolean val) {
        this.url_flag = val;
    }

    private String update_string() {
        return this.update_string;
    }
}

