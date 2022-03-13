package programmer.lp.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import programmer.lp.converter.DateConverter;

import javax.sql.DataSource;
import java.util.LinkedHashSet;
import java.util.Set;

@PropertySource("classpath:db.properties")
@MapperScan("${mybatis.mapperScanPackage}")
public class DaoConfiguration {
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;
    @Value("${mybatis.configLocation}")
    private String configLocation;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        Resource location = new ClassPathResource(configLocation);
        sqlSessionFactoryBean.setConfigLocation(location);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DateConverter dateConverter() {
        DateConverter dateConverter = new DateConverter();
        Set<String> set = new LinkedHashSet<>();
        set.add("yyyy-MM-dd");
        set.add("yyyy/MM/dd");
        set.add("yyyy年MM月dd日");
        dateConverter.setFormats(set);
        return dateConverter;
    }
}
