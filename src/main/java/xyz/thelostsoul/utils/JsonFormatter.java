package xyz.thelostsoul.utils;

import com.google.gson.Gson;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ∏Ò ΩªØJSON
 */
public class JsonFormatter {

    private static final String RETURN = "\r\n";
    private static final String TAB = "    ";
    private static final String COMMAS = ",";
    private static final String COLONS = ": ";
    private static final String QUOTE = "\"";
    private static final String LCURLY = "{";
    private static final String RCURLY = "}";
    private static final String LBRACKET = "[";
    private static final String RBRACKET = "]";
    private static boolean color = true;

    public static String formatJson(String json) throws Exception {
        if (StringUtils.isBlank(json)) {
            return json;
        }
        Gson g = new Gson();
        Map<String, Object> jsonMap = g.fromJson(json, Map.class);
        return format(jsonMap, 1);
    }

    public static String formatJson(String json, boolean color) throws Exception {
        JsonFormatter.color = color;
        return formatJson(json);
    }

    public static String format(Map<String, Object> jsonMap, boolean color) throws Exception {
        JsonFormatter.color = color;
        return format(jsonMap, 1);
    }

    public static String unformat(String prettyJson) throws Exception {
        if (StringUtils.isBlank(prettyJson)) {
            return prettyJson;
        }

        return prettyJson.replaceAll("\\r?\\n\\s*", "");
    }


    private static String format(Map<String, Object> jsonMap, int level) throws Exception {
        if (MapUtils.isEmpty(jsonMap)) {
            return "";
        }
        StringBuilder prettyJson = new StringBuilder();
        prettyJson.append(LCURLY).append(RETURN);
        Set<String> keys = jsonMap.keySet();
        for (String key : keys) {
            prettyJson.append(manyString(TAB, level)).append(QUOTE).append(blue(key)).append(QUOTE).append(COLONS);
            Object value = jsonMap.get(key);
            if (value instanceof Map) {
                prettyJson.append(format((Map<String, Object>) value, level + 1)).append(COMMAS).append(RETURN);
            } else if (value instanceof List) {
                prettyJson.append(LBRACKET);
                List tmp = (List) value;
                if (tmp.size() > 0) {
                    prettyJson.append(RETURN);
                }
                for (int i = 0, j = tmp.size(); i < j; i++) {
                    Object o = tmp.get(i);
                    if (o instanceof Map) {
                        prettyJson.append(manyString(TAB, level+1)).append(format((Map<String, Object>) o, level + 2));
                        if (i != j - 1) {
                            prettyJson.append(COMMAS);
                        }
                        prettyJson.append(RETURN);
                    } else if (o instanceof String) {
                        prettyJson.append(QUOTE).append(red((String) o)).append(QUOTE);
                        if (i != j - 1) {
                            prettyJson.append(COMMAS);
                        }
                    } else {
                        prettyJson.append(o);
                        if (i != j - 1) {
                            prettyJson.append(COMMAS);
                        }
                    }
                }
                prettyJson.append(manyString(TAB, level)).append(RBRACKET).append(COMMAS).append(RETURN);
            } else if (value instanceof String) {
                prettyJson.append(QUOTE).append(red((String) value)).append(QUOTE).append(COMMAS).append(RETURN);
            } else {
                prettyJson.append(value).append(COMMAS).append(RETURN);
            }
        }
        prettyJson.delete(prettyJson.length()-3, prettyJson.length()-2);
        prettyJson.append(manyString(TAB, level-1)).append(RCURLY);
        return prettyJson.toString();
    }

    private static String manyString(String s, int l) throws Exception {
        if (s == null) {
            return s;
        }
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < l; i++) {
            r.append(s);
        }
        return r.toString();
    }

    private static String blue(String s) {
        if (color) {
            return "\033[34m" + s + "\033[39m";
        } else {
            return s;
        }
    }

    private static String red(String s) {
        if (color) {
            return "\033[31m" + s + "\033[39m";
        } else {
            return s;
        }
    }
}

