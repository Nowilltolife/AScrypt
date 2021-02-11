package ascrypt.parser.keyword;

import ascrypt.parser.Parser;
import ascrypt.parser.ParserModule;
import ascrypt.parser.Token;
import ascrypt.parser.TokenList;
import ascrypt.util.ArrayUtil;
import javafx.util.Pair;

public class Print extends ParserModule {
    public Print() {
        super("print");
    }

    @Override
    public String parse(TokenList tokens) {
        tokens.inc();
        Token token = tokens.getClean();
        if (token.type == Token.Type.Operator && token.content.equals(":")) {
            String pair = ArrayUtil.tokensToString(tokens, 1);
            instructions += (byte) 2 + Parser.argumentSeparator + pair + lineSeparator;
        }
        return instructions;
    }
}
