package xyz.thelostsoul.base.parser.impl;

import xyz.thelostsoul.base.parser.inter.IRouteFieldParser;

import java.util.Arrays;
import java.util.List;

/**
 * 分十张表
 * @author thelostsoul
 */
public class Evenly10RouteFieldParser implements IRouteFieldParser {

    @Override
    public String convert(Object field) throws Exception {
        String rtn = "";
        long i = 0;
        if (field instanceof Integer) {
            i = new Long((Integer)field);
        } else if (field instanceof Long) {
            i = (Long) field;
        }
        else if (field instanceof String) {
            i = Long.parseLong((String) field);
        } else {
            throw new Exception ("用于分表的字段非合法类型！");
        }
        rtn = i%10 + "";
        return rtn;
    }

    @Override
    public List<String> all() {
        return Arrays.asList("0","1","2","3","4","5","6","7","8","9");
    }
}
