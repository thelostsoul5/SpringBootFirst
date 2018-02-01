package xyz.thelostsoul.base;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by jamie on 17-12-30.
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        Database database = DatabaseContextHolder.getDatabase();
        if (database == null) {
            database = Database.primary;
        }
        return database;
    }
}
