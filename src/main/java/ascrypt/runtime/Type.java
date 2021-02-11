package ascrypt.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Type {

    String name;
    String[] identifier;
    Class<?> internalType;

    public String toByteIntern() {
        return name.substring(0, 1);
    }

    public Object attemptCast(String value) {
        switch (internalType.getSimpleName()) {
            case "Integer":
            case "int":
                return Integer.parseInt(value);
            default:
                return null;
        }
    }

}
