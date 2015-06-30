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
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.finesce.emarketplace.context.LoadContextElement;
import eu.finesce.emarketplace.context.LoadContextElementForTest;
import eu.finesce.emarketplace.domain.Load;
import eu.finesce.emarketplace.domain.LoadList;
import eu.fiware.ngsi.official.ContextElement;
import eu.fiware.ngsi.official.ContextElementList;
import eu.fiware.ngsi.official.UpdateActionType;
import eu.fiware.ngsi.official.UpdateContextRequest;

/**
 * The Class LoadDataInputForTest.
 */
@Path("/loadDataInputForTest")
public class LoadDataInputForTest {
	
	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(LoadDataInputForTest.class);

	/** The register context path. */
	private static  String REGISTER_CONTEXT_PATH = "";
	
	/** The orion server url. */
	private static  String ORION_SERVER_URL = "";
	
	/** The prop. */
	Properties prop;
	
	/** The Constant REG_CTX_PATH. */
	private static final String REG_CTX_PATH = "meteringInput.registerContexPath";
	
	/** The Constant ORION_SVR_URL. */
	private static final String ORION_SVR_URL = "meteringInput.orionServerUrl";
	
	/** The Constant propertyPath. */
	private static final String propertyPath = "config.properties";

	
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

		logger.info("Sending Load data to Orion for Load: "+ load.getMeterId()+"...of Sample: "+load.getSampleNumber()+
				" of sample Date: " + new Date(load.getLoadSampleDate())  + " upstream: " + load.getUpstreamActivePowerEUA() 
				+ " downStream: " + load.getDownstreamActivePowerEEA());
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(ORION_SERVER_URL).path(
				REGISTER_CONTEXT_PATH);
		
		UpdateContextRequest updContextRequest = new UpdateContextRequest();
		updContextRequest.setUpdateAction(UpdateActionType.APPEND);
		ContextElement element=new LoadContextElementForTest(load);
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
//			logger.info("Sending laod data to Orion for meter: "+ load.getMeterId()+"...");
//			logger.info("Sending laod data to Orion for LOAD (toString): "+ load.toString());
			Client client = ClientBuilder.newClient();
	
			WebTarget webTarget = client.target(ORION_SERVER_URL).path(
					REGISTER_CONTEXT_PATH);
			
						
			UpdateContextRequest updContextRequest = new UpdateContextRequest();
			updContextRequest.setUpdateAction(UpdateActionType.APPEND);
			ContextElement element=new LoadContextElementForTest(load);
			ContextElementList elementList=new ContextElementList();
			elementList.getContextElements().add(element);
			updContextRequest.setContextElementList(elementList);
	
			Entity<UpdateContextRequest> xml = Entity.xml(updContextRequest);
			responseEntity = webTarget.request(MediaType.APPLICATION_XML).post(xml);
		//	System.out.println(responseEntity.readEntity(String.class));
			
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
	 * Sets the config properties.
	 */
	protected void setConfigProperties() {
		prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream(propertyPath));
			REGISTER_CONTEXT_PATH = prop.getProperty(REG_CTX_PATH);
			ORION_SERVER_URL = prop.getProperty(ORION_SVR_URL);   
		} catch (IOException e) {
			logger.error("Error during getProperty ", e);
		}
	}
	
}
