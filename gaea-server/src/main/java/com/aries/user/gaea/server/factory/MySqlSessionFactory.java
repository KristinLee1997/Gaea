package com.aries.user.gaea.server.factory;

import com.aries.user.gaea.server.constants.SysConstants;
import com.aries.user.gaea.server.utils.DateSourceUrlUtil;
import com.aries.user.gaea.core.utils.PropertiesUtils;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Slf4j
public class MySqlSessionFactory {
    private MySqlSessionFactory() {
    }

    private volatile static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSQLSessionFactory(String companyName) {
        if (sqlSessionFactory == null) {
            synchronized (MySqlSessionFactory.class) {
                if (sqlSessionFactory == null) {
                    try {
                        sqlSessionFactory = getAllDatabaseSQLSessionFactory(companyName);
                    } catch (IOException e) {
                        log.warn("获取" + companyName + "-sqlSessionFactory异常");
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory;
    }

    private static SqlSessionFactory getAllDatabaseSQLSessionFactory(String companyName) throws IOException {
        String databaseName = companyName.equals(SysConstants.DATABASE_USERCENTER) ? SysConstants.DATABASE_USERCENTER
                : SysConstants.DATABASE_USERCENTER + "_" + companyName;

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        Configuration configuration = getConfiguration(databaseName);

        return sqlSessionFactoryBuilder.build(configuration);
    }

    /**
     * 获取mybatis配置，相当于mybatis-config.xml
     *
     * @param databaseName
     * @return
     */
    private static Configuration getConfiguration(String databaseName) {
        Configuration configuration = new Configuration();

        DataSource mysqlDataSource = getHikariCpDatasource(databaseName);

        Environment environment = new Environment(databaseName, new JdbcTransactionFactory(), mysqlDataSource);

        configuration.setEnvironment(environment);

        configuration.addMappers("com.aries.user.gaea.server.mapper");

        return configuration;
    }

    /**
     * 获取 HikariCp 数据源
     *
     * @param databaseName:数据库名
     * @return 数据源
     */
    private static DataSource getHikariCpDatasource(String databaseName) {
        Properties confProperties = new PropertiesUtils(SysConstants.CONF_PROPERTIES).getProperties();
        String url = DateSourceUrlUtil.getDataSourceUrl((String) confProperties.get("datasource.ip"),
                (String) confProperties.get("datasource.port"), databaseName);
        String user = (String) confProperties.get("datasource.username");
        String password = (String) confProperties.get("datasource.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true"); // 设置是否对预编译使用local cache
        config.addDataSourceProperty("prepStmtCacheSize", "250");// 指定了local cache的大小，使用LRU进行逐出
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");  // 超过该长度后不使用预编译
        config.addDataSourceProperty("rewriteBatchedStatements", "true");// batch处理
        config.setValidationTimeout(3000);
        config.setReadOnly(false);//连接只读数据库时配置为true， 保证安全
        config.setAutoCommit(true);//自动提交
        config.setConnectionTimeout(30000);//等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
        config.setIdleTimeout(600000);//一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
        config.setMaxLifetime(1800000);// 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
        config.setMaximumPoolSize(4);//连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
//        config.setMetricRegistry(new MetricRegistry());//用于监控

        return new HikariDataSource(config);
    }

    /**
     * 获取 MySQL 数据源
     *
     * @param databaseName：数据库名
     * @return 数据源
     */
    private static DataSource getMysqlDataSource(String databaseName) {
        Properties confProperties = new PropertiesUtils(SysConstants.CONF_PROPERTIES).getProperties();
        String url = DateSourceUrlUtil.getDataSourceUrl((String) confProperties.get("datasource.ip"),
                (String) confProperties.get("datasource.port"), databaseName);
        String user = (String) confProperties.get("datasource.username");
        String password = (String) confProperties.get("datasource.password");

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(url);
        mysqlDataSource.setUser(user);
        mysqlDataSource.setPassword(password);
        return mysqlDataSource;
    }


}
