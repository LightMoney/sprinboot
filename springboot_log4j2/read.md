相比与其他的日志系统，log4j2丢数据这种情况少；disruptor技术，在多线程环境下，性能高于logback等10倍以上；利用jdk1.5并发的特性，减少了死锁的发生；

如果自定义了文件名，需要在application.yml中配置
logging:
  config: xxxx.xml
  level:
    cn.jay.repository: trace
    
默认名log4j2-spring.xml，就省下了在application.yml中配置
