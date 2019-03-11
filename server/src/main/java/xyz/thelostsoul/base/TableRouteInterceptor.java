package xyz.thelostsoul.base;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.thelostsoul.annotation.TableTag;
import xyz.thelostsoul.base.parser.inter.IRouteFieldParser;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.*;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class TableRouteInterceptor implements Interceptor {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
        Object[] args = invocation.getArgs();

        Connection currentConnection = (Connection) args[0];
        DatabaseMetaData metaData = currentConnection.getMetaData();
        String dbType = metaData.getDatabaseProductName();
        dbType = dbType.toLowerCase();

        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        LOG.info("原SQL：" + sql);

        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        String id = mappedStatement.getId();
        String mapperName = id.substring(0, id.lastIndexOf("."));
        Class mapper = Class.forName(mapperName);

        Annotation[] annotations = mapper.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Map<String, String> tableIndexMap = new HashMap<>(3);
            Object obj = boundSql.getParameterObject();
            Map<String, Object> params = new HashMap<>();
            if (obj instanceof Map) {
                params = (Map<String, Object>) obj;
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof TableTag) {
                    String tableName = ((TableTag) annotation).tableName();
                    String separator = ((TableTag) annotation).separator();
                    String shardByField = ((TableTag) annotation).shardByField();
                    Class<? extends IRouteFieldParser> fieldParserClass = ((TableTag) annotation).fieldParser();

                    IRouteFieldParser fieldParser = fieldParserClass.newInstance();
                    Object field = params.get(shardByField);
                    if (field != null) {
                        String tableIndex = fieldParser.convert(field);
                        tableIndexMap.put(tableName, tableName + separator + tableIndex);
                    } else {
                        List<String> tableIndexs = fieldParser.all();
                        String unionTableSql = unionTable(tableName, separator, tableIndexs);
                        tableIndexMap.put(tableName, unionTableSql);
                    }
                }
            }

            List<SQLStatement> statementList = SQLUtils.parseStatements(sql, dbType);
            RouteTableExprVisitor visitor = new RouteTableExprVisitor(tableIndexMap);
            for (SQLStatement sqlStatement : statementList) {
                sqlStatement.accept(visitor);
            }
            sql = SQLUtils.toSQLString(statementList, dbType);
            LOG.info("执行分表规则后的SQL：" + sql);
            metaStatementHandler.setValue("delegate.boundSql.sql", sql);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private static String unionTable(String tableName, String separator, List<String> tableIndexs) {
        StringBuilder builder = new StringBuilder();
        if (tableIndexs != null && tableIndexs.size() > 0) {
            for (int i = 0, s = tableIndexs.size(); i < s; i++) {
                String tableIndex = tableIndexs.get(i);
                builder.append(" select * from ")
                        .append(tableName).append(separator).append(tableIndex);
                if (i < s-1) {
                    builder.append(" union all ");
                }
            }
        }
        return "(" + builder.toString() + ") all_"+tableName;
    }

}
