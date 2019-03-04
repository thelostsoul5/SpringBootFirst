package xyz.thelostsoul.base;

/**
 * Created by jamie on 17-12-30.
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<Database> contextHolder = new ThreadLocal<>();

    public static Database getDatabase() {
        return contextHolder.get();
    }

    public static void setDatabase(Database database) {
        contextHolder.set(database);
    }

    public static void clearDatabase() {
        contextHolder.remove();
    }
}
