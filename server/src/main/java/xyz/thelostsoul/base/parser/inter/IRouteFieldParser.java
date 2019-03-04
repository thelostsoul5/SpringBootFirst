package xyz.thelostsoul.base.parser.inter;

import java.util.List;

public interface IRouteFieldParser {
    /**
     * 处理分表字段，得到分表索引
     * @param var1 分表字段值
     * @return 分表索引
     * @throws Exception 取值错误
     */
    String convert(Object var1) throws Exception;

    /**
     * 当查询条件不存在分表索引的时候，返回默认的索引列表
     * 因为很多情况下不能通过创建视图查全部分表来解决，比如月分表的情况，
     * 所以通过这种方法规定查询的范围
     * @return 索引列表
     */
    List<String> all();
}
