package ascrypt.parser.special;

import ascrypt.parser.ParserModule;
import ascrypt.parser.Token;
import ascrypt.parser.TokenList;

public class Function extends ParserModule {
    public Function() {
        super("(");
    }

    @Override
    public String parse(TokenList tokens) {
        tokens.startOfLine();
        if(tokens.get().type == Token.Type.LineEnd)
            tokens.inc();
        String functionName = tokens.getAndInc().content;
        if(tokens.getClean().content.equals("(")) {
            if (tokens.getClean().getContent().equals(")")) {
                if (tokens.getClean().content.equals("{")) {
                    instructions += 4 + argumentSeparator + functionName + lineSeparator;
                }else {
                    instructions += 5 + argumentSeparator + functionName + lineSeparator;
                }
            }
        }
        return instructions;
    }
}
