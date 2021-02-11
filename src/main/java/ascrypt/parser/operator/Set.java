package ascrypt.parser.operator;

import ascrypt.parser.Token;
import ascrypt.parser.TokenList;
import ascrypt.parser.ParserModule;
import ascrypt.runtime.Type;
import ascrypt.runtime.Types;
import ascrypt.util.ArrayUtil;
import javafx.util.Pair;

public class Set extends ParserModule {
    public Set() {
        super(":");
    }

    @Override
    public String parse(TokenList tokens) {
        tokens.startOfLine();
        if(tokens.get().type == Token.Type.LineEnd)
            tokens.inc();
        Token token = tokens.getAndInc();
        if (Types.getType(token.content) == null) {
            String varName = token.content;
            token = tokens.getClean();
            if(token.content.equals(":")) {
                if (tokens.peek().content.equals(" "))
                    tokens.inc();
                String args = ArrayUtil.tokensToString(tokens, 1);
                instructions += 3 + argumentSeparator + varName + argumentSeparator + args + lineSeparator;
            }
        }else {
            Type type = Types.getType(token.content);
            token = tokens.getClean();
            String varName = token.content;
            token = tokens.getClean();
            if(token.content.equals(":")) {
                if (tokens.peek().content.equals(" "))
                    tokens.inc();
                String args = ArrayUtil.tokensToString(tokens, 1);
                instructions += 1 + argumentSeparator + varName + argumentSeparator + type.toByteIntern() + argumentSeparator + args+ lineSeparator;
            }
        }
        return instructions;
    }
}
