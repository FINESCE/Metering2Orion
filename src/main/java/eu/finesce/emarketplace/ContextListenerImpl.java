package eu.finesce.emarketplace;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.annotation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

@WebListener
public class ContextListenerImpl implements ServletContextListener {

	/** The meter detail context path. */
	protected static  String METER_DETAIL_CONTEXT_PATH = "";
	/** The prop. */
	Properties prop;
	/** The Constant propertyPath. */
	protected static final String propertyPath = "config.properties";
	/** The meter detail svr url. */
	protected static  String METER_DETAIL_SVR_URL = "";
	/** The Constant METER_DETAIL_PATH. */
	protected static final String METER_DETAIL_PATH = "meteringInput.meterDetailPath";
	
	/** The Constant METER_DETAIL_SERVER_URL. */
	protected static final String METER_DETAIL_SERVER_URL = "meteringInput.meterDetailServerUrl";
	
	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(ContextListenerImpl.class);
	
	protected static final String CIAO = "meteringInput.meterDetailServerUrl";
	
   
	private ServletContext sc = null;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//ServletContextEvent ctx = (ServletContextEvent) arg0.getServletContext();
		sce.getServletContext().removeAttribute("XKFINKwCostantsMapps");
		logger.info("REMOVE CONTEXT");
		
	}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       
    	sc = sce.getServletContext();
        
        HashMap<String, String> map = new HashMap<String, String>();
        //... here you can create and initialize your HashMap
        try {
			Response rs = getMeterDetailList();
			String fileResponse = new String(rs.readEntity(String.class));
			SAXBuilder builder = new SAXBuilder();
			
			Document document = builder.build(new StringReader(fileResponse));
			// Get xml root
			Element rootElement = document.getRootElement();
			// get root's childs
			List<?> children = rootElement.getChildren();
			Iterator<?> iterator = children.iterator();
			while (iterator.hasNext()) {
				Element item = (Element) iterator.next();
				if ("MeterDetailsList".equals(item.getName())) {
                    map.put(item.getChildText("newMeterCode"), item.getChildText("kNewMeter"));
				}
			}
		} catch (JDOMException e) {
			logger.error("Error reading response xml file :",e);
		} catch (IOException e) {
			logger.error("Error openinig xml (Rest) file :",e);
		}catch (Exception e) {
			logger.error("Error updateLoadPowerValue :",e);
			logger.info("***URI is not absolute*** error is a normal error if the load object does not need a costant, you can ignore it!");
		}
        logger.info("Map created ? " + map.toString());
//
//      
		//when map is ready add it as attribute to servlet context 
        sc.setAttribute("XKFINKwCostantsMapps", map);
        logger.info(sc.getAttribute("XKFINKwCostantsMapps").toString());
    }
//    
//    
    /**
	 * Gets the meter detail list.
	 *
	 * @param meterId the meter id
	 * @return the meter detail list
	 */
	public Response getMeterDetailList() {
		setConfigProperties();
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(METER_DETAIL_SVR_URL);
		WebTarget resourceWebTarget = webTarget.path(METER_DETAIL_CONTEXT_PATH);
		Response responseEntity = resourceWebTarget.request(
				MediaType.APPLICATION_XML).get();
		return responseEntity;
	}
	
	/**
	 * Sets the config properties.
	 */
	public void setConfigProperties() {
		prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream(propertyPath));			
			METER_DETAIL_CONTEXT_PATH = prop.getProperty(METER_DETAIL_PATH);
			METER_DETAIL_SVR_URL = prop.getProperty(METER_DETAIL_SERVER_URL);
			
		} catch (IOException e) {
			logger.error("Error during getProperty ", e);
		}
	}
    
}
