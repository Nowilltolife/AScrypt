package ascrypt.parser;


import ascrypt.parser.keyword.Print;
import ascrypt.parser.operator.Set;
import ascrypt.parser.special.Function;
import ascrypt.runtime.Type;
import ascrypt.runtime.Types;
import ascrypt.util.ArrayUtil;
import javafx.util.Pair;
import jdk.nashorn.internal.runtime.ParserException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public String content;
    public TokenList tokens = new TokenList();
    public String[] operators = new String[] {"+", "-", ":"};
    public String[] specialChars = new String[] {"\"", "'", "(", ")", "{", "}"};
    public static String argumentSeparator = "\u0000";
    public static String lineSeparator = "\u001F";
    public List<ParserModule> parserModules = new ArrayList<>();
    public List<ParserModule> operatorModules = new ArrayList<>();

    public Parser(String content) {
        this.content = content;
        parserModules.add(new Print());
        operatorModules.add(new Set());
        operatorModules.add(new Function());
    }

    public void parse() {
        String identifier = "";
        int index = 0;
        char[] chars = content.toCharArray();
        while (index < chars.length) {
            char c = chars[index];
            if(Character.isAlphabetic(c)) {
                while (Character.isAlphabetic(c)) {
                    identifier += c;
                    index++;
                    if(index >= chars.length)
                        break;
                    c = chars[index];
                }
                Token token = new Token(identifier, Token.Type.Identifier);
                tokens.add(token);
                identifier = "";
                continue;
            }
            else if(Character.isDigit(c)) {
                while (Character.isDigit(c)) {
                    identifier += c;
                    index++;
                    if(index >= chars.length) {
                        break;
                    }
                    c = chars[index];
                }
                Token token = new Token(identifier, Token.Type.Number);
                tokens.add(token);
                identifier = "";
                continue;
            }
            else if(Arrays.asList(operators).contains("" + c)) {
                Token token = new Token("" + c, Token.Type.Operator);
                tokens.add(token);
            }
            else if(Arrays.asList(specialChars).contains("" + c)) {
                Token token = new Token("" + c, Token.Type.Special);
                tokens.add(token);
            }
            else if(c == '\n') {
                Token token = new Token(""+c, Token.Type.LineEnd);
                tokens.add(token);
            }
            else if(c == ' ') {
                Token token = new Token(" ", Token.Type.Identifier);
                tokens.add(token);
            }
            index++;
        }
    }


    /**
     * ID TREE
     *
     * 1 - Assert Variable
     * 2 - Print Instruction
     * 3 - Update Variable
     * 4 - Create Function
     * 5 - Call Function
     * 6 - Close Function
     * 7 - Enter into stack
     * 8 - Remove from stack
     *
     */


    public String parseInstructions() {
        String instructions = "";
        int functionDepth = 0;
        boolean inFunction = false;
        while (tokens.internalIndex < tokens.size()) {
            Token token = tokens.get();
            switch (token.type) {
                case Identifier:
                    if(token.content.equals(" ")) {
                        tokens.inc();
                        continue;
                    }
                            boolean b = false;
                            for (ParserModule parserModule : parserModules) {
                                if (token.getContent().equals(parserModule.identifier)) {
                                    parserModule.parse(tokens);
                                    instructions += parserModule.instructions;
                                    parserModule.instructions = "";
                                    b = true;
                                }
                            }
                    break;
                case Special:
                case Operator:
                    for (ParserModule parserModule : operatorModules) {
                        if (token.getContent().equals(parserModule.identifier)) {
                            parserModule.parse(tokens);
                            if(parserModule.instructions.startsWith("4")) {
                                inFunction = true;
                                functionDepth++;
                            }
                            instructions += parserModule.instructions;
                            parserModule.instructions = "";
                            b = true;
                        }
                    }
                    if(inFunction) {
                        if(token.content.equals("}")) {
                            instructions += "6" + argumentSeparator + lineSeparator;
                            functionDepth--;
                            if(functionDepth == 0)
                                inFunction = false;
                        }
                    }
                    break;
            }
            tokens.inc();
        }
        if(inFunction)
            throw new RuntimeException("Error function not closed!");
        return instructions;
    }

    public static class Context {

    }


}
