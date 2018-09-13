package xyz.thelostsoul.base.split.inter;

import java.util.List;

public interface ISplitFieldParser {
    String convert(Object var1) throws Exception;
    List<String> all();
}
