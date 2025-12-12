package com.zeyad.app;
import java.util.List;
import java.util.ArrayList;
public class ArgumentTokenizer {
    private String command;
    private List<String> args;
    private int currentIndex;
    public ArgumentTokenizer(String command)
    {
        this.command = command;
        args = new ArrayList<>();
    }

    public String[] split()
    {
        while (currentIndex < command.length())
        {
            if (command.charAt(currentIndex) == '"')
            {
                String strLiteral = consumeStringLiteral();
                args.add(strLiteral);
            }
            else if (!Character.isWhitespace(command.charAt(currentIndex)))
            {
                String word = consumeWord();
                args.add(word);
            }
            else
                currentIndex++;
        }
        return args.toArray(new String[0]);
    }

    private String consumeStringLiteral() {
        int startIndex = ++currentIndex;

        while(currentIndex < command.length() && command.charAt(currentIndex) != '"')
            currentIndex++;
        String substring = command.substring(startIndex, currentIndex); 
        if(currentIndex < command.length() && command.charAt(currentIndex) == '"')
            currentIndex++;
        return substring;
    }

    private String consumeWord() {
        int startIndex = currentIndex;
        while (currentIndex < command.length() && !Character.isWhitespace(command.charAt(currentIndex)))
            currentIndex++;
        return command.substring(startIndex, currentIndex);
    }
}
