 mybatis-plus使用jdk8的LocalDateTime
 查询时报错：SQLFeatureNotSupportedException
       ; null; nested exception is java.sql.SQLFeatureNotSupportedException
   解决方案：
 mybatis-plus版本降至3.1.0或以下即可
 也可以参考下面网友提供的其它解决方法
 官方解决方案： 1. 升级druid到1.1.21解决这个问题；2.保持mp版本3.1.0；3.紧跟mp版本，换掉druid数据源
 -----------------------------------------------------
 测试
 5.5.62  mysql无写入问题 LocalDateTime-----datetime
 8.0.22 mysql无写入问题  LocalDateTime-----datetime
 注意8.0.22版本导出的sql在低版本中可能不兼容，如 datetime（0） 低版本不支持
 
 mybatis 版本在3.4.5版本就完全支持LocalDateTime  数据库类型datetime
 
 LocalDateTime在数据操作时可使用，实际前后端数据传递格式变化，都无效（已试过说有方式2020/12/22）
 推荐前后端传递还是用date吧，格式转换有效
