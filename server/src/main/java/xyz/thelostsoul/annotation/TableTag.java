package xyz.thelostsoul.annotation;

import xyz.thelostsoul.base.parser.inter.IRouteFieldParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TableTag {
    String tableName();
    String separator();
    String shardByField();
    Class<? extends IRouteFieldParser> fieldParser();
}
