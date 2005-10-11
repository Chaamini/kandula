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
package org.apache.kandula.wscoor;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.om.OMAbstractFactory;
import org.apache.axis2.om.OMElement;
import org.apache.axis2.om.OMNamespace;
import org.apache.axis2.soap.SOAPFactory;
import org.apache.kandula.Constants;
import org.apache.kandula.coordinator.Coordinator;
import org.apache.kandula.faults.AbstractKandulaException;
import org.apache.kandula.utility.KandulaUtils;

/**
 * @author <a href="mailto:thilina@apache.org"> Thilina Gunarathne </a>
 */

public class RegistrationPortTypeRawXMLSkeleton {
    private MessageContext msgContext;

    public void init(MessageContext msgContext) {
        this.msgContext = msgContext;
    }

    public OMElement registerOperation(OMElement request) throws AxisFault {

        String protocolIdentifier;
        EndpointReference participantEPR;
        String activityId;

        /*
         * Extracting data from the received message
         */
        protocolIdentifier = request.getFirstChildWithName(
                new QName("ProtocolIdentifier")).getText();
        OMElement participantEPRElement = request
                .getFirstChildWithName(new QName("ParticipantProtocolService"));
        //Extracting the participant EPR
        participantEPR = KandulaUtils.endpointFromOM(participantEPRElement);

        //have to extract the reference parameter "id". Axis2 does not support
        ArrayList list = msgContext.getMessageInformationHeaders()
                .getReferenceParameters();
        //TODO :Have to use the Activity ID came with EPR as a reference
        // property
        activityId = Coordinator.ACTIVITY_ID;

        /*
         * Registering the participant for the activity for the given protocol
         */
        try {
            Coordinator coordinator = new Coordinator();
            EndpointReference epr = coordinator.registerParticipant(activityId,
                    protocolIdentifier, participantEPR);
            SOAPFactory factory = OMAbstractFactory.getSOAP12Factory();
            OMNamespace wsCoor = factory.createOMNamespace(Constants.WS_COOR,
                    "wscoor");
            OMElement responseEle = factory.createOMElement("RegisterResponse",
                    wsCoor);
            responseEle.addChild(toOM(epr));
            return responseEle;
        } catch (AbstractKandulaException e) {
            AxisFault fault = new AxisFault(e);
            fault.setFaultCode(e.getFaultCode());
            throw fault;
        }
    }

    /**
     * Serializes an EndpointRefrence to OM Nodes
     */
    private OMElement toOM(EndpointReference epr) {
        SOAPFactory factory = OMAbstractFactory.getSOAP12Factory();
        OMNamespace wsCoor = factory.createOMNamespace(
                org.apache.kandula.Constants.WS_COOR, "wscoor");
        OMElement protocolService = factory.createOMElement(
                "CoordinatorProtocolService", wsCoor);
        OMElement coordinatorProtocolService = factory.createOMElement(
                "CoordinatorProtocolService", wsCoor);
        KandulaUtils.endpointToOM(epr, coordinatorProtocolService, factory);
        protocolService.addChild(coordinatorProtocolService);
        return protocolService;
    }
}