import java.util.ArrayList;

/**
 * @author Tyler Conley
 * @date 2020.02.18
 * Right now, I want to make this so that the interface has a global value for the length of the line,
 * which will be based off of the pane/stage size in the DMTool class
 */

public interface Autowrap {

    default String autoWrap(String s) {
        char[] chars = s.toCharArray();
        ArrayList<Character> characters = new ArrayList<>();
        for(char c : chars) {
            characters.add(c);
        }

        int charCount = 0;
        for(int i = 0; i<characters.size(); i++) {
            charCount++;
            if(characters.get(i) == '\n'){
                charCount = 0;
            }
            if(charCount>=DMTool.LINE_LENGTH && characters.get(i) == ' ') {
                characters.add(i+1, '\n');
                charCount = 0;
            }
        }
        StringBuilder charString = new StringBuilder();

        for(char c : characters) {
            charString.append(c);
        }

        return charString.toString();
    }
}


