package app;

import java.util.*;

public class Util {
    public static <T> String join(Collection<T> c, String sep){
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (T x : c){
            if(!first) sb.append(sep);
            first=false;
            sb.append(x);
        }
        return sb.toString();
    }
}
