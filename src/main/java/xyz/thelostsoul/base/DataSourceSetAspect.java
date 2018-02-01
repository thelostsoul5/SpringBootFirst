package xyz.thelostsoul.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import xyz.thelostsoul.annotation.DataSourceSetter;

import java.lang.reflect.Method;

/**
 * Created by jamie on 18-1-1.
 */

@Aspect
@Configuration
public class DataSourceSetAspect {

    @Pointcut("execution(* xyz.thelostsoul..dao.*Mapper.*(..))")
    public void setPoint() {}

    @Around("setPoint()")
    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        DataSourceSetter sourceSetter = method.getAnnotation(DataSourceSetter.class);
        if (sourceSetter == null) {
            Class<?> clazz = method.getDeclaringClass();
            sourceSetter = clazz.getAnnotation(DataSourceSetter.class);
        }

        try {
            if (sourceSetter != null) {
                DatabaseContextHolder.setDatabase(sourceSetter.value());
            }
            return joinPoint.proceed();
        } finally {
            DatabaseContextHolder.clearDatabase();
        }
    }
}
