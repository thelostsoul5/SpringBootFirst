package xyz.thelostsoul.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jamie on 17-3-1.
 */
public class UserSqlBuilder {
    public String buildGetUserByIds(@Param("idList") final List<Integer> idList){
        return new SQL(){{
            SELECT("id","name");
            FROM("user");
            if (idList!=null){
                String ids = idList.stream().map(Object::toString).collect(Collectors.joining(","));
                WHERE("id in ("+ids+")");
            }
        }}.toString();
    }
}
