package ascrypt.util;

import ascrypt.parser.Token;
import ascrypt.parser.TokenList;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayUtil {

    public static String arrayToString(Object[] array, String delimiter, int offset, int end) {
        StringBuilder retrn = new StringBuilder();
        for(int i=offset;i< end;i++) {
            retrn.append(array[i]).append(delimiter);
        }
        return retrn.substring(0, retrn.length()-delimiter.length());
    }

    public static String arrayToString(Object[] array) {
       return arrayToString(array, " ", 0, array.length);
    }

    public static String arrayToString(Object[] array, String delimiter, int offset) {
        return arrayToString(array, delimiter,offset, array.length);
    }

    public static String arrayToString(Object[] array, String delimiter) {
        return arrayToString(array, delimiter,0, array.length);
    }

    public static <T> T chooseRandomElement(T[] array) {
        if(array.length == 1)
            return array[0];
        Random random = new Random();
        int index = random.nextInt(array.length-1);
        return array[index];
    }

    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        Iterator<K> keyIter = keys.iterator();
        Iterator<V> valIter = values.iterator();
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(_i -> keyIter.next(), _i -> valIter.next()));
    }

    public static String tokensToString(TokenList tokens, int offset) {
        String cache = "";
        int i=tokens.internalIndex + offset;
        for (; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if(token.getContent().equals("\n") || token.getContent().equals("\r")) {
                tokens.internalIndex = i;
                return cache;
            }
            cache += token.getContent();
        }
        tokens.internalIndex = i;
        return cache;
    }
}
