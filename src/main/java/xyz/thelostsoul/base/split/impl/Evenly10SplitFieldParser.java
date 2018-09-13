package xyz.thelostsoul.base.split.impl;

import xyz.thelostsoul.base.split.inter.ISplitFieldParser;

import java.util.Arrays;
import java.util.List;

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
            throw new Exception ("���ڷֱ���ֶηǺϷ����ͣ�");
        }
        return rtn;
    }

    @Override
    public List<String> all() {
        return Arrays.asList("0","1","2","3","4","5","6","7","8","9");
    }
}
