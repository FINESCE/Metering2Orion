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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.fiware.ngsi.official.RegisterContextRequest;

/**
 * The Class TestOrion.
 */
public class TestOrion {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client
				.target("http://130.206.82.78:1026/NGSI9/registerContext");

		// updateCOntext on Orion
		RegisterContextRequest regContextRequest = new RegisterContextRequest();
		
		Response response = webTarget.request(MediaType.APPLICATION_XML)
				.accept(MediaType.APPLICATION_XML)
				.post(Entity.xml(regContextRequest));

		System.out.println(response);

		// Response responseEntity =
		// resourceWebTarget.request(MediaType.APPLICATION_XML).post(Entity.xml(meterSample));
		// System.out.println(responseEntity);
	}

}
