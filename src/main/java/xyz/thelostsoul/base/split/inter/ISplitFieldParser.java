package xyz.thelostsoul.base.split.inter;

import java.util.List;

public interface ISplitFieldParser {
    /**
     * 处理分表字段，得到分表索引
     * @param var1 分表字段值
     * @return 分表索引
     * @throws Exception 取值错误
     */
    String convert(Object var1) throws Exception;

    /**
     * 当查询条件不存在分表索引的时候，返回默认的索引列表
     * @return 索引列表
     */
    List<String> all();
}
