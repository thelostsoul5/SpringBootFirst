package xyz.thelostsoul.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.thelostsoul.bean.DBAcct;
import xyz.thelostsoul.bean.DBAcctExample;

public interface DBAcctMapper {
    long countByExample(DBAcctExample example);

    int deleteByExample(DBAcctExample example);

    int deleteByPrimaryKey(String dbAcctCode);

    int insert(DBAcct record);

    int insertSelective(DBAcct record);

    List<DBAcct> selectByExample(DBAcctExample example);

    DBAcct selectByPrimaryKey(String dbAcctCode);

    int updateByExampleSelective(@Param("record") DBAcct record, @Param("example") DBAcctExample example);

    int updateByExample(@Param("record") DBAcct record, @Param("example") DBAcctExample example);

    int updateByPrimaryKeySelective(DBAcct record);

    int updateByPrimaryKey(DBAcct record);
}