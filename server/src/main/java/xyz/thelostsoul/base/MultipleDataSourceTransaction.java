package xyz.thelostsoul.base;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jamie on 18-1-1.
 */
public class MultipleDataSourceTransaction implements Transaction {

    private final DataSource dataSource;
    private ConcurrentMap<Database, Connection> connectionMap;

    public MultipleDataSourceTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
        this.connectionMap = new ConcurrentHashMap<>();
    }

    @Override
    public Connection getConnection() throws SQLException {
        //这里取出Database只是为了缓存connection，不是最终的获取connection逻辑
        Database currentDatabase = DatabaseContextHolder.getDatabase();
        Connection theConn = null;
        if (connectionMap.containsKey(currentDatabase)) {
            theConn = connectionMap.get(currentDatabase);
        }
        if (theConn == null) {
            theConn = DataSourceUtils.getConnection(this.dataSource);
            this.connectionMap.put(currentDatabase, theConn);
        }
        return theConn;
    }

    @Override
    public void commit() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            if (isNeedTransactionCtrl(connection)) {
                connection.commit();
            }
        }
    }

    @Override
    public void rollback() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            if (isNeedTransactionCtrl(connection)) {
                connection.rollback();
            }
        }
    }

    @Override
    public void close() throws SQLException {
        for (Connection connection : connectionMap.values()) {
            DataSourceUtils.releaseConnection(connection, this.dataSource);
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(this.dataSource);
        return holder != null && holder.hasTimeout()?Integer.valueOf(holder.getTimeToLiveInSeconds()):null;
    }

    private boolean isNeedTransactionCtrl(Connection connection) throws SQLException {
        boolean isConnectionTransactional =
                DataSourceUtils.isConnectionTransactional(connection, this.dataSource);
        boolean isAutoCommit = connection.getAutoCommit();
        if (connection != null && !isConnectionTransactional && !isAutoCommit) {
            return true;
        }
        return false;
    }
}
