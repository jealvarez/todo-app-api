package org.jealvarez.todoapp.api.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MySqlDataSourceConfiguration {

    @Bean
    public DataSourceTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(final DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        final PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:mybatis/*.xml"));

        return sqlSessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(final SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
