package ascrypt.parser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {

    public enum Type {
        Identifier,
        Number,
        Operator,
        LineEnd,
        Special
    }

    public String content;
    public Type type;

}
