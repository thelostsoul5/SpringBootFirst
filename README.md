# SpringBootFirst
更改Bean org.apache.ibatis.session.SqlSessionFactory 的构建的时候，要注意不影响Mybatis配置的加载，详见 xyz.thelostsoul.config.MybatisAutoConfig ，
因之前没有注意，导致Mybatis拦截器的JavaConfig一直没生效。
