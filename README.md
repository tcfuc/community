## Libra论坛
## 资料
[Spring 文档](https://spring.io/guides)\
[Spring Web](https://spring.io/guides/gs/serving-web-content/)\
[es](https://elasticsearch.cn/)\
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)\
[Bootstrap](https://v3.bootcss.com/getting-started/)
## 工具
[Git](https://git-scm.com/download)\
[Visual Paradigm](https://www.visual-paradigm.com)\
[Flyway](https://flywaydb.org/getstarted/)
## 脚本
`sql:`\
`create table USER (`

     ID           INT auto_increment primary key not null ,
     ACCOUNT_ID   VARCHAR(100),
     NAME         VARCHAR(50),
     TOKEN        CHAR(36),
     GMT_CREATE   BIGINT,
     GMT_MODIFIED BIGINT,

 `);`
 
 `alter table USER add bio varchar(256) null ;`
 
`bash:`\
`mvn flyway:migrate`\
`mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate`
