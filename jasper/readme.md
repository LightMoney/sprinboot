jasper 实例
  设计    执行    输出
jrxml   ----> jasper  ----->  jprint   -----> pdf 等


模板设计可通过图形化工具jasper  studio   
https://community.jaspersoft.com/community-download

该工具可以生成jrxml   编译生成 jasper

输出中文无法显示解决：
        引入jasperreports_extension.properties  文件
        stsong文件夹下fonts.xml中配置使用的中文字体
        stsong.ttf文件已经有所有字体了