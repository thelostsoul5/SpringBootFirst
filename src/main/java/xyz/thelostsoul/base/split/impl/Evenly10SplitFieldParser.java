package xyz.thelostsoul.base.split.impl;

import xyz.thelostsoul.base.split.inter.ISplitFieldParser;

public class Evenly10SplitFieldParser implements ISplitFieldParser {

    @Override
    public String convert(Object field) throws Exception {
        String rtn = "";
        if (field instanceof Integer) {
            int i = (int) field;
            rtn = i%10 + "";
        } else if (field instanceof Long) {
            long i = (long) field;
            rtn = i%10 + "";
        }
        else if (field instanceof String) {
            long i = Long.parseLong((String) field);
            rtn = i%10 + "";
        } else {
            throw new Exception ("用于分表的字段非合法类型！");
        }
        return rtn;
    }
}
