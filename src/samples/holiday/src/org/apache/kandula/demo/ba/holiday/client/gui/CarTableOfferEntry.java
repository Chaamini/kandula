/*
 * Copyright 2007 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *  @author Hannes Erven, Georg Hicker
 */
package org.apache.kandula.demo.ba.holiday.client.gui;

import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.kandula.demo.ba.holiday.car.LocSpec;
import org.apache.kandula.demo.ba.holiday.car.DateSpec;

public class CarTableOfferEntry extends TableOfferEntry {

	public CarTableOfferEntry(
			final String pBookingReference, 
			final String pSpecification,
			final LocSpec locSpec,
			final DateSpec dateSpec,
			final BigDecimal pPrice,
			final String[] pPayments,
			final Calendar pExpires
	) {
		super(
				pBookingReference, pSpecification, 
				new org.apache.kandula.demo.ba.holiday.LocSpec(locSpec), 
				new org.apache.kandula.demo.ba.holiday.DateSpec(dateSpec),
				pPrice,
				pPayments, 
				pExpires
			);
	}
}
