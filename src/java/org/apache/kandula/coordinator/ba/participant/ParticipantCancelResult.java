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
package org.apache.kandula.coordinator.ba.participant;

/**
 * This interface marks all classes that may be returned from the
 * onCancel event handler.
 * 
 * @author Hannes Erven, Georg Hicker (C) 2006
 *
 */
public interface ParticipantCancelResult {

	/**
	 * Automatically report CANCELED
	 */
	public static final ParticipantResultCanceled CANCELED = ParticipantResultCanceled.instance ;
	
	/**
	 * No automatic reporting to the coordinator. You need to call
	 * this.tellXXX() yourself!
	 */
	public static final ParticipantResultHandledByApplication HANDLED_BY_APPLICATION = ParticipantResultHandledByApplication.instance;
}
