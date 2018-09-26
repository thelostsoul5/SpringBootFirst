package xyz.thelostsoul.base.split.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.thelostsoul.base.split.inter.ISplitFieldParser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 年月分表，默认查三个月
 */
public class YearMonthSplitFieldParser implements ISplitFieldParser {

    private static final Logger LOG = LoggerFactory.getLogger(YearMonthSplitFieldParser.class);

    @Override
    public String convert(Object var1) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String yyyyMM = LocalDate.now().format(formatter);
        if (var1 instanceof Date) {
            LocalDate searchDate =
                    ((Date) var1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            yyyyMM = searchDate.format(formatter);
        } else if (var1 instanceof String) {
            yyyyMM = (String) var1;
        } else {
            LOG.error("取年月分表失败，默认当月");
        }
        return yyyyMM;
    }

    @Override
    public List<String> all() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        LocalDate now = LocalDate.now();
        String thisMonth = now.format(formatter);
        String oneMonthBefore = now.minusMonths(1).format(formatter);
        String twoMonthBefore = now.minusMonths(2).format(formatter);

        List<String> rtn = Arrays.asList(thisMonth, oneMonthBefore, twoMonthBefore);
        return rtn;
    }
}
