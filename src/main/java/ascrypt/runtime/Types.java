package ascrypt.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

public class Types {

    public static Type integer = new Type("Integer", new String[]{"int", "integer"}, int.class);
    public static Type string = new Type("String", new String[]{"string", "str"}, String.class);
    public static Type nullType = new Type("Null", new String[]{"nul"}, null);

    public static Type getType(String input) {
        if(Arrays.asList(integer.identifier).contains(input))
            return integer;
        return null;
    }

    public static Type getByByte(String b) {
        if(integer.toByteIntern().equals(b))
            return integer;
        return null;
    }

}
