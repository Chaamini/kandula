/*
 * Copyright  2004 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.kandula.coordinator;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.kandula.context.AbstractContext;
import org.apache.kandula.context.ContextFactory;
import org.apache.kandula.context.CoordinationContext;
import org.apache.kandula.faults.AbstractKandulaException;
import org.apache.kandula.storage.StorageFactory;
import org.apache.kandula.storage.Store;

/**
 * @author <a href="mailto:thilina@opensource.lk"> Thilina Gunarathne </a>
 */

public class Coordinator {
	private Store store;

	public Coordinator() {
		StorageFactory storageFactory = StorageFactory.getInstance();
		store = storageFactory.getStore();
	}

	/**
	 * @param coorContext
	 * @return the interposed Coordination Context created <p/>Participants
	 *         decided to use this Coordinator as a interposed
	 *         sub-coordinator.The newly created CoordinationContext will
	 *         contain the same ActivityIdentifier & Protocol type. Registration
	 *         EPR of the earlier CoordinationContext will be replaced by the
	 *         RegistrationEPR of this Coordinator.
	 */
	public AbstractContext createCoordinationContext(
			CoordinationContext coorContext) throws AbstractKandulaException {
		ContextFactory factory = ContextFactory.getInstance();
		AbstractContext context = factory.createActivity(coorContext);
		store.put(context.getCoordinationContext().getActivityID(), context);
		return context;
	}

	/**
	 * @param coordinationType
	 * @return the Coordination Context created <p/>Initiators can use this to
	 *         Create new Distributed transactions.This will take in the
	 *         Coordination Type and will create an instance of the reapective
	 *         ActivityContext. The Coordination Context created by this can be
	 *         used to convey the information about the transaction between
	 *         initiator,Participants and coordinator
	 * @throws AbstractKandulaException
	 */
	public AbstractContext createCoordinationContext(String coordinationType,
			long expires) throws AbstractKandulaException {
		ContextFactory factory = ContextFactory.getInstance();
		AbstractContext context = factory.createActivity(coordinationType);
		context.getCoordinationContext().setExpires(expires);
		store.put(context.getCoordinationContext().getActivityID(), context);
		return context;
	}

	private AbstractContext getCoordinationContext(String id) {
		return (AbstractContext) store.get(id);
	}

	/**
	 * @param protocol
	 * @param participantEPR
	 * @param id
	 * @return Should return the particular Coordiators End Point Reference <p/>
	 *         This method provides the functional logic for participants to
	 *         register for a particular transaction activity which was created
	 *         by a initiator. Registration request will be forwarded to
	 *         repective protocol coordinators.
	 * @throws AbstractKandulaException
	 */
	public EndpointReference registerParticipant(String id, String protocol,
			EndpointReference participantEPR) throws AbstractKandulaException {

		AbstractContext context = getCoordinationContext(id);
		if (context == null) {
			throw new IllegalStateException(
					"No Activity Found for this Activity ID");
		}

		Registerable registerableCoordinator = Registerable.Factory
				.newRegisterable(context.getCoordinationType());
		return registerableCoordinator.register(context, protocol,
				participantEPR);
	}
}