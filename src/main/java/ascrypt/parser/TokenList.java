package ascrypt.parser;

import java.util.ArrayList;

public class TokenList extends ArrayList<Token> {

    public int internalIndex = 0;

    public void nextLine() {
        for (int i = 0; i < size(); i++)
            if (getAndInc().type.equals(Token.Type.LineEnd))
                return;
    }

    public void previousLine() {
        boolean visitedEnd = false;
        for (int i = internalIndex; i > 0; i--)
            if (getAndInc(-1).type.equals(Token.Type.LineEnd)) {
                if (visitedEnd) {
                    inc();
                    return;
                }
                visitedEnd = true;
            }
    }

    public Token decAndGet() {
        internalIndex--;
        return get(internalIndex);
    }

    public Token getAndGet() {
        internalIndex--;
        return get(internalIndex + 1);
    }

    public void startOfLine() {
        for (int i = internalIndex; i > 0; i--)
            if (getAndInc(-1).type.equals(Token.Type.LineEnd)) {
                inc();
                return;
            }
    }

    public void inc() {
        internalIndex++;
    }

    public void inc(int i) {
        internalIndex += i;
    }

    public Token getAndInc() {
        Token value = get(internalIndex);
        internalIndex++;
        return value;
    }

    public Token getClean() {
        Token token;
        while (true) {
            if(internalIndex >= size())
                return null;
            token = getAndInc();
            if(!token.content.equals(" "))
                return token;
        }
    }

    public Token incAndGet() {
        internalIndex++;
        return get();
    }

    public Token peek() {
        return get(internalIndex + 1);
    }

    public Token getAndInc(int amount) {
        internalIndex += amount;
        return get(internalIndex - amount);
    }

    public Token get() {
        return get(internalIndex);
    }

}
