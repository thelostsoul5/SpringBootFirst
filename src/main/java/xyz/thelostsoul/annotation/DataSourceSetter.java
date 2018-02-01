package xyz.thelostsoul.annotation;

import xyz.thelostsoul.base.Database;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jamie on 17-12-31.
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface DataSourceSetter {
    Database value();
}
