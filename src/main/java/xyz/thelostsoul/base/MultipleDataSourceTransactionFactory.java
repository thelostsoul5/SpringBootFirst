package xyz.thelostsoul.base;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * Created by jamie on 18-1-1.
 */
public class MultipleDataSourceTransactionFactory extends SpringManagedTransactionFactory {

    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel transactionIsolationLevel) {
        return new MultipleDataSourceTransaction(dataSource);
    }
}
