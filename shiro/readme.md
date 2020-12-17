该工程不是分前后端分离的
 前后端分离
要实现前后端分离，需要考虑以下2个问题： 1. 项目不再基于session了，如何知道访问者是谁？ 2. 如何确认访问者的权限？


直接使用token

前后端分离，一般都是通过token实现，本项目也是一样；用户登录时，生成token及 token过期时间，token与用户是一一对应关系，调用接口的时候，把token放到header或 请求参数中，服务端就知道是谁在调用接口。
https://blog.csdn.net/weixin_42236404/article/details/89319359