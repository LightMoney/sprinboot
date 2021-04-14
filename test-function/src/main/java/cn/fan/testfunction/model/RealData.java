package cn.fan.testfunction.model;

import java.util.concurrent.Callable;

/**
 * future模式核心类  指定futuretask实际工作内容和返回对象
 */
public class RealData implements Callable {
    private String para;

    public RealData(String para) {
        this.para = para;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(para);
            System.out.println(sb);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }
}
