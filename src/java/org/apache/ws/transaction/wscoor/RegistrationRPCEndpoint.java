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
package org.apache.ws.transaction.wscoor;

import java.rmi.RemoteException;

import org.apache.axis.MessageContext;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.AddressingHeaders;
import org.apache.axis.message.addressing.Constants;
import org.apache.axis.message.addressing.EndpointReference;
import org.apache.axis.message.addressing.PortType;
import org.apache.axis.message.addressing.ReferencePropertiesType;
import org.apache.ws.transaction.coordinator.CoordinationService;
import org.apache.ws.transaction.coordinator.Coordinator;
import org.apache.ws.transaction.coordinator.Identifier;


public class RegistrationRPCEndpoint implements RegistrationPortTypeRPC {
	public static PortType PORT_TYPE= new PortType("http://schemas.xmlsoap.org/ws/2003/09/wscoor", "RegistrationPortTypeRPC"); 
	
	public RegisterResponseType registerOperation(RegisterType parameters) throws RemoteException {
		try {
			Coordinator coordinator= CoordinationService.getInstance().getCoordinator(getActivityId());
			EndpointReference epr= null;
			if (parameters.getParticipantProtocolService() != null)
				epr= new EndpointReference(parameters.getParticipantProtocolService());
			EndpointReference coordinatorProtocolService=
				coordinator.registerParticipant(parameters.getProtocolIdentifier(), epr);
			RegisterResponseType response= new RegisterResponseType();
			response.setCoordinatorProtocolService(coordinatorProtocolService);
			return response;
		}
		catch (Exception e) {
			if (e instanceof RemoteException)
				throw (RemoteException)e;
			else
				throw new RemoteException(e.toString());
		}
	}

	private Identifier getActivityId() throws RemoteException {
		AddressingHeaders header=
			(AddressingHeaders)MessageContext.getCurrentContext().getProperty(
				Constants.ENV_ADDRESSING_REQUEST_HEADERS);
		ReferencePropertiesType refprop= header.getReferenceProperties();
		MessageElement e= refprop.get(Identifier.QNAME_IDENTIFIER);
		if (e != null)
			return new Identifier(e);
		else
			throw new RemoteException();
	}
}