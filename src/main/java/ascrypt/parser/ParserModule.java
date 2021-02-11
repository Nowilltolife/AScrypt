package ascrypt.parser;

import ascrypt.parser.TokenList;
import lombok.Data;

@Data
public abstract class ParserModule {

   public String identifier;
   public String instructions = "";
   public String argumentSeparator = "\u0000";
   public String lineSeparator = "\u001F";

   public ParserModule(String identifier) {
       this.identifier = identifier;
   }

   public abstract String parse(TokenList tokens);


}
