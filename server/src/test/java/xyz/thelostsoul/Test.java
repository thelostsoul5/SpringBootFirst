package xyz.thelostsoul;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import ognl.MemberAccess;
import ognl.OgnlException;
import org.springframework.util.StringUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 * @author zhouzm5
 *         Modification History:<br>
 *         Date Author Version Description<br>
 *         ---------------------------------------------------------*<br>
 *         2017/3/15 zhouzm5 v1.0.0 <br>
 * @version v1.0.0
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class Test {

    public static void main(String[] args) throws InterruptedException, OgnlException {

        String sql = "select id, name from user limit ?";
        String dbType = "mysql";
        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, dbType);
        for (SQLStatement sqlStatement : statementList) {
            System.out.println(sqlStatement);
        }

//        int i = 1;
//        iiff(i);

//        String dbType = JdbcConstants.MYSQL;
//        String sql = "select * from user a,offer b where a.id=b.id and name=?";
//        List<SQLStatement> statementList = SQLUtils.parseStatements(sql, dbType);
//        System.out.println(SQLUtils.toSQLString(statementList, dbType));
//        Map<String, String> tableIndexMap = new HashMap<>();
//        tableIndexMap.put("user", "_0");
//        RouteTableExprVisitor visitor = new RouteTableExprVisitor(tableIndexMap);
//        for (SQLStatement sqlStatement : statementList) {
//            sqlStatement.accept(visitor);
//        }
//        System.out.println(SQLUtils.toSQLString(statementList, dbType));

        //JOOR
//        R r = new R();
//        Reflect.on(r).set("hello", "hello world");
//        System.out.println(r.getHello());


//        Flowable.fromCallable(() -> {
//            Thread.sleep(1000); //  imitate expensive computation
////            throw new Exception("Test");
//            return "Done";
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .subscribe(s-> System.out.println(s+" hello world!"), Throwable::printStackTrace);

//        LocalDateTime now = LocalDateTime.now();
//        now = now.minusDays(2);
//        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//
//        Map<String, String> c = new HashMap<>();
//        c.put("a", "1");
//        c.put("b", "2");
//        c.put("c", "3");
//        List<Map<String, String>> b = new ArrayList<>();
//        b.add(c);
//        b.add(c);
//        b.add(c);
//        Map<String, List<Map<String, String>>> a = new HashMap();
//        a.put("all", b);
//
//        Map<String, Map> d = new HashMap<>();
//        d.put("dep", a);

        //3.2.4
        //OgnlContext context = new OgnlContext(new DefaultMemberAccess(false, true, true), null, null, d);

        //Object o = Ognl.getValue("#dep.all[0].a", context, context.getRoot());

        //3.2.1
//        Object o = Ognl.getValue("dep.all[2].a", d);
//        System.out.println(o);
//        Ognl.setValue("dep.all[2].a", d, "0");
//        o = Ognl.getValue("dep.all[0].a", d);
//        System.out.println(o);

//
//        //Stream API
//        int[] i = new int[]{1,2,3};
//        IntStream stream = Arrays.stream(i);
//        System.out.println(stream.map(ii->ii*3).sum());


//        Thread.sleep(2000);
    }

    public static void iiff(Object i) {
        if (i instanceof Integer) {
            System.out.println("true");
        }
    }

}
class R {
    private String hello;
    public String getHello(){
        return this.hello;
    }
}

class SplitTableExprVisitor extends MySqlASTVisitorAdapter {

    private Map<String, String> tableIndexMap;

    public SplitTableExprVisitor(Map<String, String> tableIndexMap) {
        this.tableIndexMap = tableIndexMap;
    }

    public boolean visit(SQLExprTableSource x) {
        SQLExpr expr = x.getExpr();
        String tablename = expr.toString();
        String tableIndex = tableIndexMap.get(tablename);
        if (!StringUtils.isEmpty(tableIndex)){
            x.setExpr(tablename + tableIndex);
        }
        return true;
    }
}

class DefaultMemberAccess implements MemberAccess {
    public boolean allowPrivateAccess;
    public boolean allowProtectedAccess;
    public boolean allowPackageProtectedAccess;

    public DefaultMemberAccess(boolean allowAllAccess) {
        this(allowAllAccess, allowAllAccess, allowAllAccess);
    }

    public DefaultMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess, boolean allowPackageProtectedAccess) {
        this.allowPrivateAccess = false;
        this.allowProtectedAccess = false;
        this.allowPackageProtectedAccess = false;
        this.allowPrivateAccess = allowPrivateAccess;
        this.allowProtectedAccess = allowProtectedAccess;
        this.allowPackageProtectedAccess = allowPackageProtectedAccess;
    }

    public boolean getAllowPrivateAccess() {
        return this.allowPrivateAccess;
    }

    public void setAllowPrivateAccess(boolean value) {
        this.allowPrivateAccess = value;
    }

    public boolean getAllowProtectedAccess() {
        return this.allowProtectedAccess;
    }

    public void setAllowProtectedAccess(boolean value) {
        this.allowProtectedAccess = value;
    }

    public boolean getAllowPackageProtectedAccess() {
        return this.allowPackageProtectedAccess;
    }

    public void setAllowPackageProtectedAccess(boolean value) {
        this.allowPackageProtectedAccess = value;
    }

    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;
        if (this.isAccessible(context, target, member, propertyName)) {
            AccessibleObject accessible = (AccessibleObject)member;
            if (!accessible.isAccessible()) {
                result = Boolean.TRUE;
                accessible.setAccessible(true);
            }
        }

        return result;
    }

    public void restore(Map context, Object target, Member member, String propertyName, Object state) {
        if (state != null) {
            ((AccessibleObject)member).setAccessible((Boolean)state);
        }

    }

    public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
        int modifiers = member.getModifiers();
        boolean result = Modifier.isPublic(modifiers);
        if (!result) {
            if (Modifier.isPrivate(modifiers)) {
                result = this.getAllowPrivateAccess();
            } else if (Modifier.isProtected(modifiers)) {
                result = this.getAllowProtectedAccess();
            } else {
                result = this.getAllowPackageProtectedAccess();
            }
        }

        return result;
    }
}