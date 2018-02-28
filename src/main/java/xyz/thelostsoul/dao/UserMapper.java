package xyz.thelostsoul.dao;

import org.apache.ibatis.annotations.*;
import xyz.thelostsoul.annotation.DataSourceSetter;
import xyz.thelostsoul.annotation.TableTag;
import xyz.thelostsoul.base.Database;
import xyz.thelostsoul.base.split.impl.Evenly10SplitFieldParser;
import xyz.thelostsoul.bean.User;

import java.util.List;

/**
 * Created by jamie on 17-2-13.
 */
@TableTag(tableName = "user", separator = "", shardByField = "id", fieldParser = Evenly10SplitFieldParser.class)
@Mapper
public interface UserMapper {
    @DataSourceSetter(Database.second)
    @Select("select * from user where id=#{id}")
    User selectByPrimaryKey(@Param("id") int id);

    @Select("select id,name from user")
    List<User> allUsers();

    @Insert("insert into user(name,password) value(#{name},#{password})")
    int insertUser(User user);

    @SelectProvider(type = UserSqlBuilder.class, method = "buildGetUserByIds")
    List<User> selectByIds(@Param("idList") List<Integer> idList);
}
