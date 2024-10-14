//package com.mock.service;
//
//
//import com.mock.spring.ComponentScan;
//
//
//@ComponentScan("com.mock.service")
//@EnableTransactionManagement
//@Configuration
//public class SpringTransacAppConfig {
//
//    @Bean
//    public DataSource dataSource()
//    {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl();
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        return dataSource;
//    }
//
////    @Bean
//    public JdbcTemplate jdbcTemplate()
//    {
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager()
//    {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        return transactionManager();
//    }
//
//}
