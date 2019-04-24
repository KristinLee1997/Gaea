package com.aries.user.gaea.factory;

import com.aries.user.gaea.constants.SysConstants;
import com.aries.user.gaea.utils.DateSourceUrlUtil;
import com.aries.user.gaea.utils.PropertiesProxy;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;


@Slf4j
public class MySqlSessionFactory {
    public static SqlSessionFactory getSQLSessionFactory(String companyName) throws IOException {
        String databaseName = companyName.equals(SysConstants.DATABASE_USERCENTER) ? SysConstants.DATABASE_USERCENTER : SysConstants.DATABASE_USERCENTER + "_" + companyName;
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        SqlSessionFactory sqlSessionFromXML = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream(SysConstants.MYBATIS_CONFIG_XML_PATH));

        Configuration newConfiguration = new Configuration();

        MysqlDataSource mysqlDataSource = getMysqlDataSource(databaseName);

        Environment environment = new Environment(databaseName, new JdbcTransactionFactory(), mysqlDataSource);
        newConfiguration.setEnvironment(environment);

        Optional.ofNullable(sqlSessionFromXML.getConfiguration().getMapperRegistry().getMappers()).get()
                .forEach(newConfiguration::addMapper);

        SqlSessionFactory newSqlSessionFactory = sqlSessionFactoryBuilder.build(newConfiguration);

        return newSqlSessionFactory;
    }

    private static MysqlDataSource getMysqlDataSource(String databaseName) {
        Properties confProperties = new PropertiesProxy(SysConstants.CONF_PROPERTIES).getProperties();
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
