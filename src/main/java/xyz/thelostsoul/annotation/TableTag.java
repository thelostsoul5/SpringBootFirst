package xyz.thelostsoul.annotation;

import xyz.thelostsoul.base.split.inter.ISplitFieldParser;

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
    Class<? extends ISplitFieldParser> fieldParser();
}
