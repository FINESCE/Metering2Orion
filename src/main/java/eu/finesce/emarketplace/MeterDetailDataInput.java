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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.finesce.emarketplace.context.MeterDetailContextElement;
import eu.finesce.emarketplace.domain.MeterDetail;
import eu.fiware.ngsi.official.ContextElement;
import eu.fiware.ngsi.official.ContextElementList;
import eu.fiware.ngsi.official.UpdateActionType;
import eu.fiware.ngsi.official.UpdateContextRequest;

/**
 * The Class MeterDetailDataInput.
 */
@Path("/meterDetailDataInput")
public class MeterDetailDataInput {
	
	/** The Constant logger. */
	private static final Log logger = LogFactory.getLog(MeterDetailDataInput.class);

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
	 * Post meter detail data.
	 *
	 * @param meterDetail the meter detail
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response postMeterDetailData(MeterDetail meterDetail) {

		setConfigProperties();
		logger.info("Sending meterDetail data to Orion for meter: "+ meterDetail.getCustormerId());
		
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target(ORION_SERVER_URL).path(
				REGISTER_CONTEXT_PATH);
		
		UpdateContextRequest updContextRequest = new UpdateContextRequest();
		updContextRequest.setUpdateAction(UpdateActionType.APPEND);
		ContextElement element=new MeterDetailContextElement(meterDetail);
		ContextElementList elementList=new ContextElementList();
		elementList.getContextElements().add(element);
		updContextRequest.setContextElementList(elementList);

		Entity<UpdateContextRequest> xml = Entity.xml(updContextRequest);
		Response response = webTarget.request(MediaType.APPLICATION_XML).post(xml);
//		UpdateContextResponse updResponse = response.readEntity(UpdateContextResponse.class);
		return response;
//		return Response.created(URI.create("created")).build();
	}

	/**
	 * Gets the meter detail sample.
	 *
	 * @return the meter detail sample
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public MeterDetail getMeterDetailSample() {
		MeterDetail meterSample = new MeterDetail("meterDetailId1", "1");
		return meterSample;
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
