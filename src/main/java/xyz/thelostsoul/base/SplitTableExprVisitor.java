package xyz.thelostsoul.base;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SplitTableExprVisitor extends MySqlASTVisitorAdapter {

    private Map<String, String> tableIndexMap;

    public SplitTableExprVisitor(Map<String, String> tableIndexMap) {
        this.tableIndexMap = tableIndexMap;
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        SQLExpr expr = x.getExpr();
        String tableName = expr.toString();
        String tableIndex = tableIndexMap.get(tableName);
        if (!StringUtils.isEmpty(tableIndex)){
            x.setExpr(tableName + tableIndex);
        }
        return true;
    }
}
