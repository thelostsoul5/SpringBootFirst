import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.Properties;


public class MybatisGeneratorComment implements CommentGenerator {

    @Override
    public void addConfigurationProperties(Properties properties) {}

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        //判断数据库中该字段注释是否为空
        if(introspectedColumn.getRemarks()==null||introspectedColumn.getRemarks().equals("")) {
            return;
        }
        field.addJavaDocLine("/**"+introspectedColumn.getRemarks()+"*/");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {}

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {}

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {}

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {}

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {}

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if(introspectedColumn.getRemarks()==null||introspectedColumn.getRemarks()=="") {
            return;
        }
        method.addJavaDocLine("/**获取"+introspectedColumn.getRemarks()+"*/");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if(introspectedColumn.getRemarks()==null||introspectedColumn.getRemarks().equals("")) {
            return;
        }
        method.addJavaDocLine("/**设置"+introspectedColumn.getRemarks()+"*/");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {}

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {}

    @Override
    public void addComment(XmlElement xmlElement) {}

    @Override
    public void addRootComment(XmlElement rootElement) {}
}
