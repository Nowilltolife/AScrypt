package ascrypt.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Variable {

    public Type type;
    public String name;
    public Object value;
    public String context;

}
