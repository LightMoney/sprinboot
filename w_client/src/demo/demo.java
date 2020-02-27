package demo;

import first.DemoService_Service;

/**
 * 这是客户端生成
 * 实现WebService客户端不一定要生成客户端的代码，可以通过地址动态调用
 */
public class demo {

    public static void main(String[] args) {
        DemoService_Service webServiceImpl = new DemoService_Service();
        String result = webServiceImpl.getDemoServiceImplPort().sayHello("没有说");
        System.out.println("===========================================");
        System.out.println(result);
        System.out.println("===========================================");

//        通过地址动态调用
//        //创建动态客户端
//        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//        Client client = factory.createClient("http://localhost:8090/demo/api?wsdl");
//        // 需要密码的情况需要加上用户名和密码
//        //client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME,PASS_WORD));
//        HTTPConduit conduit = (HTTPConduit) client.getConduit();
//        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//        httpClientPolicy.setConnectionTimeout(2000);  //连接超时
//        httpClientPolicy.setAllowChunking(false);    //取消块编码
//        httpClientPolicy.setReceiveTimeout(120000);     //响应超时
//        conduit.setClient(httpClientPolicy);
//        //client.getOutInterceptors().addAll(interceptors);//设置拦截器
//        try{
//            Object[] objects = new Object[0];
//            // invoke("方法名",参数1,参数2,参数3....);
//            objects = client.invoke("sayHello", "sujin");
//            System.out.println("返回数据:" + objects[0]);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


    }


}