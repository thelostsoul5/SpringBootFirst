package xyz.thelostsoul.base;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.db2.visitor.DB2ASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.h2.visitor.H2ASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.odps.visitor.OdpsASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerASTVisitorAdapter;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author THELOSTSOUL
 */
public class VisitorFactory {

    public static SQLASTVisitorAdapter createASTVisitor(String dbType, Map<String, String> tableNameMap) {
        if (JdbcConstants.ORACLE.equals(dbType) || JdbcConstants.ALI_ORACLE.equals(dbType)) {
            return new OracleASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.MYSQL.equals(dbType) || JdbcConstants.MARIADB.equals(dbType)) {
            return new MySqlASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.POSTGRESQL.equals(dbType)) {
            return new PGASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.SQL_SERVER.equals(dbType) || JdbcConstants.JTDS.equals(dbType)) {
            return new SQLServerASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.DB2.equals(dbType)) {
            return new DB2ASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.ODPS.equals(dbType)) {
            return new OdpsASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.H2.equals(dbType)) {
            return new H2ASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.HIVE.equals(dbType)) {
            return new HiveASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        if (JdbcConstants.ELASTIC_SEARCH.equals(dbType)) {
            return new MySqlASTVisitorAdapter() {
                @Override
                public boolean visit(SQLExprTableSource x) {
                    return innerVisit(x, tableNameMap);
                }
            };
        }

        return new SQLASTVisitorAdapter() {
            @Override
            public boolean visit(SQLExprTableSource x) {
                return innerVisit(x, tableNameMap);
            }
        };
    }

    private static boolean innerVisit(SQLExprTableSource x, Map<String, String> tableNameMap) {
        SQLExpr expr = x.getExpr();
        String tableName = expr.toString();
        String finalTableName = tableNameMap.get(tableName);
        if (!StringUtils.isEmpty(finalTableName)) {
            x.setExpr(finalTableName);
        }
        return true;
    }
}
