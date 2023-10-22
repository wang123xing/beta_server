package cn.beta.platform.config;

import cn.beta.platform.enums.DataSourceType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Description: 数据源配置
 * @version: 1.0
 */
@Configuration
public class DataSourceConfiguration {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DataSourceConfiguration.class) ;
    @Value("${jdbc.type}")
    private Class<? extends DataSource> dataSourceType;
    @Value("${mybatis-plus.mapperLocations:null}")
    private String path;
    @Value("${mybatis-plus.typeAliasesPackage:null}")
    private String aliasesPackage;

    @Bean(name="writeDataSourceSettings")
    @ConfigurationProperties(prefix = "write.jdbc")
    public DataSourceSettings writeDataSource(){
        return new DataSourceSettings();
    }
    @Bean(name="readDataSourceSettings")
    @ConfigurationProperties(prefix = "read.jdbc")
    public DataSourceSettings readDataSource(){
        return new DataSourceSettings();
    }
    @Bean(name="writeDataSource")
    @Primary
    public DataSource masterDataSource(@Qualifier("writeDataSourceSettings") DataSourceSettings prop){
        DataSource masterDataSource = DataSourceBuilder.create()
                .driverClassName(prop.getDriverClassName())
                .username(prop.getUsername())
                .password(prop.getPassword())
                .url(prop.getUrl())
                .type(dataSourceType)
                .build();
        LOGGER.info("Registered WRITE DataSource:{} ",masterDataSource);
        return masterDataSource;
    }

    @Bean(name="readDataSource")
    @ConditionalOnProperty(value = "common.dataSource.enable", matchIfMissing = true)
    public DataSource slaveDataSource(@Qualifier("readDataSourceSettings") DataSourceSettings prop){
        DataSource slaveDataSource = DataSourceBuilder.create().
                driverClassName(prop.getDriverClassName())
                .username(prop.getUsername())
                .password(prop.getPassword())
                .url(prop.getUrl())
                .type(dataSourceType)
                .build();
        LOGGER.info("Registered READ DataSource:{} ",slaveDataSource);
        return slaveDataSource;
    }
    /**
     * 根据名称进行注入，通常是在具有相同的多个类型的实力的一个注入
     */
    @Bean
    public DynamicDataSource dataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                        @Qualifier("readDataSource") DataSource readDataSource){
        java.util.Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.WRITE, writeDataSource);
        targetDataSources.put(DataSourceType.READ, readDataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(readDataSource);
        return dataSource;
    }
    /**
     * <descption> 根据数据源创建sqlSessionFactory </descption>
     * @param
     * @return
     * @throws Exception
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(ds);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(path));
        sqlSessionFactory.setTypeAliasesPackage(aliasesPackage);
        MybatisConfiguration configuration = new MybatisConfiguration();
        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{ //PerformanceInterceptor(),OptimisticLockerInterceptor()
                paginationInterceptor() //添加分页功能
        });
        sqlSessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        //sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }
    /*
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

    @Bean(name = "MasterTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("writeDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Getter
    @Setter
    @Configuration
    class DataSourceSettings {
        protected String driverClassName;
        protected String url;
        protected String username;
        protected String password;
    }
}
