package cn.fan.testfunction.web;

import cn.fan.testfunction.service.TestService;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/16 13:46
 */
public class TestColntroller {

    private TestService service;

    public TestService getService() {
        return service;
    }

    public void setService(TestService service) {
        this.service = service;
    }
}
