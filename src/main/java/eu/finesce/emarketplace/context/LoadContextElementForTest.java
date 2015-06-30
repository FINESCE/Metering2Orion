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

import eu.finesce.emarketplace.domain.Load;
import eu.fiware.ngsi.official.ContextAttribute;
import eu.fiware.ngsi.official.ContextAttributeList;
import eu.fiware.ngsi.official.ContextElement;
import eu.fiware.ngsi.official.EntityId;

/**
 * The Class LoadContextElementForTest.
 */
public class LoadContextElementForTest extends ContextElement{
	
	/** The Constant LOAD_TYPE. */
	private static final String LOAD_TYPE = "LoadTest";
	
	/** The Constant LOAD_TIME. */
	private static final String LOAD_TIME = "loadTime";
	
	/** The Constant CURRENT_TIME. */
	private static final String CURRENT_TIME = "currentTime";
	
	/** The Constant IS_CONCENTRATOR. */
	private static final String IS_CONCENTRATOR= "isConcentrator";
	
	/** The Constant SAMPLE_NUMBER. */
	private static final String SAMPLE_NUMBER = "sampleNumber";
	
	/** The Constant DOWNSTREAM_ACTIVE_POWER_EEA. */
	private static final String DOWNSTREAM_ACTIVE_POWER_EEA = "downstreamActivePowerEEA";
	
	/** The Constant UPSTREAM_ACTIVE_POWER_EUA. */
	private static final String UPSTREAM_ACTIVE_POWER_EUA = "upstreamActivePowerEUA";
	
	/** The Constant REACTIVE_INDUCTIVE_POWER_EEI. */
	private static final String REACTIVE_INDUCTIVE_POWER_EEI = "reactiveInductivePowerEEI";
	
	/** The Constant REACTIVE_CAPACITIVE_POWER_EEC. */
	private static final String REACTIVE_CAPACITIVE_POWER_EEC = "reactiveCapacitivePowerEEC";
	
	/** The Constant REACTIVE_INDUCTIVE_POWER_EUI. */
	private static final String REACTIVE_INDUCTIVE_POWER_EUI = "reactiveInductivePowerEUI";
	
	/** The Constant REACTIVE_CAPACITIVE_POWER_EUC. */
	private static final String REACTIVE_CAPACITIVE_POWER_EUC = "reactiveCapacitivePowerEUC";
	
	/** The Constant TARIFF_TYPE. */
	private static final String TARIFF_TYPE = "tariffType";
	
	/** The Constant INTEGRATION_PERIOD_REF. */
	private static final String INTEGRATION_PERIOD_REF = "integrationPeriodRef";

	/**
	 * Instantiates a new load context element for test.
	 *
	 * @param load the load
	 */
	public LoadContextElementForTest(Load load){
		this.contextAttributeList=new ContextAttributeList();
		
		EntityId id=new EntityId();
		id.setId(load.getMeterId());
		id.setType(LOAD_TYPE);
		id.setIsPattern(false);
		this.setEntityId(id);
		
		//attributes
		ContextAttribute attribute=new ContextAttribute();		
		attribute=new ContextAttribute();
		attribute.setName(IS_CONCENTRATOR);
		attribute.setType("boolean");
		attribute.setContextValue(load.isConcentrator());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(SAMPLE_NUMBER);
		attribute.setType("int");
		attribute.setContextValue(load.getSampleNumber());
		this.getContextAttributeList().getContextAttributes().add(attribute);
				
		attribute=new ContextAttribute();
		attribute.setName(LOAD_TIME);
		attribute.setType("sec");
		attribute.setContextValue(load.getLoadTime());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		
		attribute=new ContextAttribute();
		attribute.setName(DOWNSTREAM_ACTIVE_POWER_EEA);
		attribute.setType("decawatt");
		attribute.setContextValue(load.getDownstreamActivePowerEEA());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(UPSTREAM_ACTIVE_POWER_EUA);
		attribute.setType("decaVAR");
		attribute.setContextValue(load.getUpstreamActivePowerEUA());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_INDUCTIVE_POWER_EEI);
		attribute.setType("decaVAR");
		attribute.setContextValue(load.getReactiveInductivePowerEEI());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_CAPACITIVE_POWER_EEC);
		attribute.setType("decaVAR");
		attribute.setContextValue(load.getReactiveCapacitivePowerEEC());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_INDUCTIVE_POWER_EUI);
		attribute.setType("decaVAR");
		attribute.setContextValue(load.getReactiveInductivePowerEUI());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(REACTIVE_CAPACITIVE_POWER_EUC);
		attribute.setType("decaVAR");
		attribute.setContextValue(load.getReactiveCapacitivePowerEUC());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(TARIFF_TYPE);
		attribute.setType("string");
		attribute.setContextValue(load.getTariffType());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(INTEGRATION_PERIOD_REF);
		attribute.setType("string");
		attribute.setContextValue(load.getIntegrationPeriodRef());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
		attribute=new ContextAttribute();
		attribute.setName(CURRENT_TIME);
		attribute.setType("sec");
		attribute.setContextValue(load.getCurrentTime());
		this.getContextAttributeList().getContextAttributes().add(attribute);
		
//		
		
	}
}
