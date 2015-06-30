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

import eu.finesce.emarketplace.domain.Meter;
import eu.fiware.ngsi.official.ContextAttribute;
import eu.fiware.ngsi.official.ContextAttributeList;
import eu.fiware.ngsi.official.ContextElement;
import eu.fiware.ngsi.official.EntityId;

/**
 * The Class MeterContextElement.
 */
public class MeterContextElement extends ContextElement{
	
	/** The Constant METER_TYPE. */
	private static final String METER_TYPE = "Meter";
	
	/** The Constant CURRENT_TIME. */
	private static final String CURRENT_TIME = "currentTime";
	
	/** The Constant IS_CONCENTRATOR. */
	private static final String IS_CONCENTRATOR= "isConcentrator";
	
	/** The Constant UPSTREAM_ACTIVE_POWER. */
	private static final String UPSTREAM_ACTIVE_POWER = "upsteamActivePower";
	
	/** The Constant DOWNSTREAM_ACTIVE_POWER. */
	private static final String DOWNSTREAM_ACTIVE_POWER = "downstreamActivePower";
	
	/** The Constant REACTIVE_POWER_Q1. */
	private static final String REACTIVE_POWER_Q1 = "RP_Q1";
	
	/** The Constant REACTIVE_POWER_Q2. */
	private static final String REACTIVE_POWER_Q2 = "RP_Q2";
	
	/** The Constant REACTIVE_POWER_Q3. */
	private static final String REACTIVE_POWER_Q3 = "RP_Q3";
	
	/** The Constant REACTIVE_POWER_Q4. */
	private static final String REACTIVE_POWER_Q4 = "RP_Q4";
	
	/** The Constant ACTIVE_POWER_TYPE. */
	private static final String ACTIVE_POWER_TYPE = "kW";
	
	/** The Constant REACTIVE_POWER_TYPE. */
	private static final String REACTIVE_POWER_TYPE = "kVAR";

	/**
	 * Instantiates a new meter context element.
	 *
	 * @param meter the meter
	 */
	public MeterContextElement(Meter meter){
		this.contextAttributeList=new ContextAttributeList();
		
		EntityId id=new EntityId();
		id.setId(meter.getMeterId());
		id.setType(METER_TYPE);
		id.setIsPattern(false);
		this.setEntityId(id);
		
		//attributes
		ContextAttribute attribute=new ContextAttribute();		
		attribute=new ContextAttribute();
		attribute.setName(IS_CONCENTRATOR);
		attribute.setType("boolean");
		attribute.setContextValue(meter.isConcentrator());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(CURRENT_TIME);
		attribute.setType("sec");
		attribute.setContextValue(meter.getCurrentTime());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(UPSTREAM_ACTIVE_POWER);
		attribute.setType(ACTIVE_POWER_TYPE);
		attribute.setContextValue(meter.getUpsteamActivePower());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(DOWNSTREAM_ACTIVE_POWER);
		attribute.setType(ACTIVE_POWER_TYPE);
		attribute.setContextValue(meter.getDownstreamActivePower());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_POWER_Q1);
		attribute.setType(REACTIVE_POWER_TYPE);
		attribute.setContextValue(meter.getReactivePowerQ1());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_POWER_Q2);
		attribute.setType(REACTIVE_POWER_TYPE);
		attribute.setContextValue(meter.getReactivePowerQ2());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_POWER_Q3);
		attribute.setType(REACTIVE_POWER_TYPE);
		attribute.setContextValue(meter.getReactivePowerQ3());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_POWER_Q4);
		attribute.setType(REACTIVE_POWER_TYPE);
		attribute.setContextValue(meter.getReactivePowerQ4());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		
		
	}
}
