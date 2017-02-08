package xyz.thelostsoul.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.thelostsoul.bean.DBAcct;

public interface DBAcctMapper {

    int deleteByPrimaryKey(String dbAcctCode);

    int insert(DBAcct record);

    int insertSelective(DBAcct record);

    @Select("select * from base.cfg_db_acct where db_acct_code=#{dbAcctCode}")
    DBAcct selectByPrimaryKey(@Param("dbAcctCode") String dbAcctCode);

    int updateByPrimaryKeySelective(DBAcct record);

    int updateByPrimaryKey(DBAcct record);
}