
package first;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "DemoService", targetNamespace = "http://service.fan.cn", wsdlLocation = "http://localhost:8092/demo/api?wsdl")
public class DemoService_Service
    extends Service
{

    private final static URL DEMOSERVICE_WSDL_LOCATION;
    private final static WebServiceException DEMOSERVICE_EXCEPTION;
    private final static QName DEMOSERVICE_QNAME = new QName("http://service.fan.cn", "DemoService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8092/demo/api?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        DEMOSERVICE_WSDL_LOCATION = url;
        DEMOSERVICE_EXCEPTION = e;
    }

    public DemoService_Service() {
        super(__getWsdlLocation(), DEMOSERVICE_QNAME);
    }

    public DemoService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), DEMOSERVICE_QNAME, features);
    }

    public DemoService_Service(URL wsdlLocation) {
        super(wsdlLocation, DEMOSERVICE_QNAME);
    }

    public DemoService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, DEMOSERVICE_QNAME, features);
    }

    public DemoService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DemoService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns DemoService
     */
    @WebEndpoint(name = "DemoServiceImplPort")
    public DemoService getDemoServiceImplPort() {
        return super.getPort(new QName("http://service.fan.cn", "DemoServiceImplPort"), DemoService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DemoService
     */
    @WebEndpoint(name = "DemoServiceImplPort")
    public DemoService getDemoServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.fan.cn", "DemoServiceImplPort"), DemoService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (DEMOSERVICE_EXCEPTION!= null) {
            throw DEMOSERVICE_EXCEPTION;
        }
        return DEMOSERVICE_WSDL_LOCATION;
    }

}
