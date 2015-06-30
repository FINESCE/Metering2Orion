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
package eu.finesce.emarketplace.context;

import eu.finesce.emarketplace.domain.MeterDetail;
import eu.fiware.ngsi.official.ContextAttribute;
import eu.fiware.ngsi.official.ContextAttributeList;
import eu.fiware.ngsi.official.ContextElement;
import eu.fiware.ngsi.official.EntityId;

/**
 * The Class MeterDetailContextElement.
 */
public class MeterDetailContextElement extends ContextElement {

	/** The Constant METER_DETAIL_TYPE. */
	private static final String METER_DETAIL_TYPE = "MeterDetail";
	
	/** The Constant CUSTOMERS_ID. */
	private static final String CUSTOMERS_ID = "custormerId";
	
	/** The Constant USER_POWER. */
	private static final String USER_POWER = "userPower";
	
	/** The Constant PRODUCER_POWER. */
	private static final String PRODUCER_POWER = "producerPower";
	
	/** The Constant DOMESTIC. */
	private static final String DOMESTIC = "isDomestic";
	
	/** The Constant SECTOR. */
	private static final String SECTOR = "sector";
	
	/** The Constant METER_CODE. */
	private static final String METER_CODE = "meterCode";
	
	/** The Constant NEW_METER_CODE. */
	private static final String NEW_METER_CODE = "newMeterCode";
	
	/** The Constant K_NEW_METERS. */
	private static final String K_NEW_METERS = "kNewMeter";
	
	/** The Constant SIM_NUMBER. */
	private static final String SIM_NUMBER = "simMeter";
	
	/** The Constant NEW_METER. */
	private static final String NEW_METER = "newMeter";
	
	/** The Constant PHASE. */
	private static final String PHASE = "phase";
	
	/** The Constant HOUSE_NUMBER. */
	private static final String HOUSE_NUMBER = "houseNumber";
	
	/** The Constant INSTALL_CODE. */
	private static final String INSTALL_CODE = "installCode";
	
	/** The Constant READING. */
	private static final String READING = "reading";
	
	/** The Constant STREET_NAME. */
	private static final String STREET_NAME = "streetName";
	
	/** The Constant CITY. */
	private static final String CITY = "city";
	
	/** The Constant LATITUDE. */
	private static final String LATITUDE = "latitude";
	
	/** The Constant LONGITUDE. */
	private static final String LONGITUDE = "longitude";
	
	/** The Constant SECONDARY_SUBSTATION_CODE. */
	private static final String SECONDARY_SUBSTATION_CODE = "secondarySubstationCode";
	
	/** The Constant TRAFO_NUMBER. */
	private static final String TRAFO_NUMBER = "trafoNumber";
	
	/** The Constant FEEDER_NAME. */
	private static final String FEEDER_NAME = "feederName";
	
	/** The Constant NODE. */
	private static final String NODE = "node";
	
	/** The Constant REGISTRED. */
	private static final String REGISTRED = "isRegistered";

	/**
	 * Instantiates a new meter detail context element.
	 *
	 * @param meterDetail the meter detail
	 */
	public MeterDetailContextElement(MeterDetail meterDetail) {
		this.contextAttributeList = new ContextAttributeList();

		EntityId id = new EntityId();
		id.setId(meterDetail.getId());
		id.setType(METER_DETAIL_TYPE);
		id.setIsPattern(false);
		this.setEntityId(id);
		
		
		

		// attributes
		ContextAttribute attribute = new ContextAttribute();
		attribute = new ContextAttribute();
		attribute.setName(CUSTOMERS_ID);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getCustormerId());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		
		attribute = new ContextAttribute();
		attribute.setName(USER_POWER);
		attribute.setType("int");
		attribute.setContextValue(meterDetail.getUserPower());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(PRODUCER_POWER);
		attribute.setType("int");
		attribute.setContextValue(meterDetail.getProducerPower());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(DOMESTIC);
		attribute.setType("boolean");
		attribute.setContextValue(meterDetail.isDomestic());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(SECTOR);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getSector());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(METER_CODE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getMeterCode());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(NEW_METER_CODE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getNewMeterCode());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(K_NEW_METERS);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getkNewMeter());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(SIM_NUMBER);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getSimMeter());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(NEW_METER);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getNewMeter());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(PHASE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getPhase());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(HOUSE_NUMBER);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getHouseNumber());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(INSTALL_CODE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getInstallCode());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(READING);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getReading());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(STREET_NAME);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getStreetName());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(CITY);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getCity());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(LATITUDE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getLatitude());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(LONGITUDE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getLongitude());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(SECONDARY_SUBSTATION_CODE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getSecondarySubstationCode());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(TRAFO_NUMBER);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getTrafoNumber());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(FEEDER_NAME);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getFeederName());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(NODE);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.getNode());
		this.getContextAttributeList().getContextAttributes().add(attribute);

		attribute = new ContextAttribute();
		attribute.setName(REGISTRED);
		attribute.setType("string");
		attribute.setContextValue(meterDetail.isRegistered());
		this.getContextAttributeList().getContextAttributes().add(attribute);

	}
}
