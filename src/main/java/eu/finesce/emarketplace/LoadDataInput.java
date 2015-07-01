/*
 * (C) Copyright 2014 FINESCE-WP4.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package eu.finesce.emarketplace;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import eu.finesce.emarketplace.context.LoadContextElement;
import eu.finesce.emarketplace.domain.Load;
import eu.finesce.emarketplace.domain.LoadList;
import eu.fiware.ngsi.official.ContextElement;
import eu.fiware.ngsi.official.ContextElementList;
import eu.fiware.ngsi.official.UpdateActionType;
import eu.fiware.ngsi.official.UpdateContextRequest;

/**
 * The Class LoadDataInput.
 */
@Path("/loadDataInput")
public class LoadDataInput {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(LoadDataInput.class);

	/** The register context path. */
	private static  String REGISTER_CONTEXT_PATH = "";
	
	/** The orion server url. */
	private static  String ORION_SERVER_URL = "";
	
	/** The meter detail context path. */
	private static  String METER_DETAIL_CONTEXT_PATH = "";
	
	/** The meter detail svr url. */
	private static  String METER_DETAIL_SVR_URL = "";
	
	
	/** The prop. */
	Properties prop;
	
	/** The Constant REG_CTX_PATH. */
	private static final String REG_CTX_PATH = "meteringInput.registerContexPath";
	
	/** The Constant ORION_SVR_URL. */
	private static final String ORION_SVR_URL = "meteringInput.orionServerUrl";
	
	/** The Constant METER_DETAIL_PATH. */
	private static final String METER_DETAIL_PATH = "meteringInput.meterDetailPath";
	
	/** The Constant METER_DETAIL_SERVER_URL. */
	private static final String METER_DETAIL_SERVER_URL = "meteringInput.meterDetailServerUrl";
	
	/** The Constant propertyPath. */
	private static final String propertyPath = "config.properties";
	
	@Context ServletContext context;

			
	/**
	 * Post load data.
	 *
	 * @param load the load
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/singleLoad")
	public Response postLoadData(Load load) {

		setConfigProperties();
		//call the method to update power value with a costant different by meterId
		Load finalLoad = new Load();
		
		//if costant is present, the initial load data become updated 
		finalLoad = updateLoadPowerValue(load);
		
		logger.info("Sending Load data to Orion for Load: " + load.toString());
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(ORION_SERVER_URL).path(
				REGISTER_CONTEXT_PATH);
		
		UpdateContextRequest updContextRequest = new UpdateContextRequest();
		updContextRequest.setUpdateAction(UpdateActionType.APPEND);
		ContextElement element=new LoadContextElement(finalLoad);
		ContextElementList elementList=new ContextElementList();
		elementList.getContextElements().add(element);
		updContextRequest.setContextElementList(elementList);

		Entity<UpdateContextRequest> xml = Entity.xml(updContextRequest);
		Response response = webTarget.request(MediaType.APPLICATION_XML).post(xml);
			
		return response;
	}
	
	/**
	 * Post load data list.
	 *
	 * @param loadList the load list
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/loadList")
	public Response postLoadDataList(LoadList loadList) {

		setConfigProperties();
		
		Response responseEntity = null;
		HashMap<String, Integer> retCodes = new HashMap<String, Integer>();
		
		ResponseBuilder builder = Response.status(Response.Status.OK);
		Response respExtend = builder.build() ;
		for (Load load : loadList.getLoadList()) {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(ORION_SERVER_URL).path(
					REGISTER_CONTEXT_PATH);
			UpdateContextRequest updContextRequest = new UpdateContextRequest();
			updContextRequest.setUpdateAction(UpdateActionType.APPEND);
			ContextElement element=new LoadContextElement(load);
			ContextElementList elementList=new ContextElementList();
			elementList.getContextElements().add(element);
			updContextRequest.setContextElementList(elementList);
	
			Entity<UpdateContextRequest> xml = Entity.xml(updContextRequest);
			responseEntity = webTarget.request(MediaType.APPLICATION_XML).post(xml);
			retCodes.put("Sample:" + load.getSampleNumber(), responseEntity.getStatus());
		}
		respExtend.getMetadata().add("LoadSampleReponse", retCodes);
		return respExtend;
		
	}

	
	/**
	 * Gets the load sample.
	 *
	 * @return the load sample
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Load getLoadSample() {
		Load loadSample = new Load("loadId1",false);
		return loadSample;
	}

		
	/**
	 * Update load power value.
	 *
	 * @param load the load
	 * @return the load
	 */
	public Load updateLoadPowerValue(Load load){
		int costant = 1;
		
		@SuppressWarnings("unchecked")
		Map<String,String> map = (Map<String,String>) context.getAttribute("KwCostantsMapps");
				 
		try {
			if (map.get(load.getMeterId()) != null){
				costant = Integer.valueOf(map.get(load.getMeterId()));
			}	
		} catch (Exception e1) {
			logger.error("Problem reading the map of costants for each load" + e1);
		}
//		try {
//			Response rs = getMeterDetailList(load.getMeterId());
//			String fileResponse = new String(rs.readEntity(String.class));
//			SAXBuilder builder = new SAXBuilder();
//			
//			Document document = builder.build(new StringReader(fileResponse));
//			// Get xml root
//			Element rootElement = document.getRootElement();
//			// get root's childs
//			List<?> children = rootElement.getChildren();
//			Iterator<?> iterator = children.iterator();
//			while (iterator.hasNext()) {
//				Element item = (Element) iterator.next();
//				if ("MeterDetailsList".equals(item.getName())) {
//					if (item.getChildText("newMeterCode").equals(load.getMeterId())){
//						costant = Integer.valueOf(item.getChildText("kNewMeter"));
//					}
//				}
//			}
//		} catch (JDOMException e) {
//			logger.error("Error reading response xml file :",e);
//		} catch (IOException e) {
//			logger.error("Error openinig xml (Rest) file :",e);
//		}catch (Exception e) {
//			logger.error("Error updateLoadPowerValue :",e);
//			logger.info("***URI is not absolute*** error is a normal error if the load object does not need a costant, you can ignore it!");
//		}

		double downstreamActivePowerEEA = load.getDownstreamActivePowerEEA();
		double reactiveInductivePowerEEI = load.getReactiveInductivePowerEEI();
		double reactiveCapacitivePowerEEC = load.getReactiveCapacitivePowerEEC();				
		double upstreamActivePowerEUA     = load.getUpstreamActivePowerEUA() ;	
		double reactiveInductivePowerEUI  = load.getReactiveInductivePowerEUI();	
		double reactiveCapacitivePowerEUC = load.getReactiveCapacitivePowerEUC();
		
		load.setDownstreamActivePowerEEA(downstreamActivePowerEEA * costant);
		load.setReactiveInductivePowerEEI(reactiveInductivePowerEEI * costant);
		load.setReactiveCapacitivePowerEEC(reactiveCapacitivePowerEEC * costant);
		load.setUpstreamActivePowerEUA(upstreamActivePowerEUA * costant);
		load.setReactiveInductivePowerEUI(reactiveInductivePowerEUI * costant);
		load.setReactiveCapacitivePowerEUC(reactiveCapacitivePowerEUC * costant);
		return load;
	}
	
	/**
	 * Gets the meter detail list.
	 *
	 * @param meterId the meter id
	 * @return the meter detail list
	 */
	public Response getMeterDetailList(String meterId) {
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
	protected void setConfigProperties() {
		prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream(propertyPath));
			REGISTER_CONTEXT_PATH = prop.getProperty(REG_CTX_PATH);
			ORION_SERVER_URL = prop.getProperty(ORION_SVR_URL);
			METER_DETAIL_CONTEXT_PATH = prop.getProperty(METER_DETAIL_PATH);
			METER_DETAIL_SVR_URL = prop.getProperty(METER_DETAIL_SERVER_URL);
			
		} catch (IOException e) {
			logger.error("Error during getProperty ", e);
		}
	}
}
