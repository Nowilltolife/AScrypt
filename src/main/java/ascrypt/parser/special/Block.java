package ascrypt.parser.special;

import ascrypt.parser.ParserModule;
import ascrypt.parser.TokenList;

public class Block extends ParserModule {

    public Block() {
        super("{");
    }

    @Override
    public String parse(TokenList tokens) {
        return null;
    }
}
