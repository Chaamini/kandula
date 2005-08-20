
	
    package org.apache.kandula.wsat;

    /**
     *  Auto generated message receiver
     */

    public class ParticipantPortTypeRPCMessageReceiver extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver{
    
		public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
		throws org.apache.axis2.AxisFault{
    
     try {

            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);
           
            ParticipantPortTypeRPCSkeleton skel = (ParticipantPortTypeRPCSkeleton)obj;
            //Out Envelop
             org.apache.axis2.soap.SOAPEnvelope envelope = null;
             //Find the operation that has been set by the Dispatch phase.
            org.apache.axis2.description.OperationDescription op = msgContext.getOperationContext().getAxisOperation();
            if (op == null) {
                throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }
            
            String methodName;
            if(op.getName() != null & (methodName = op.getName().getLocalPart()) != null){
            
				
					
					
					if(methodName.equals("CommitOperation")){
											
				
			org.xmlsoap.schemas.ws.x2003.x09.wsat.CommittedDocument param45 = null;
						
				//doc style
					param45 = skel.CommitOperation((org.xmlsoap.schemas.ws.x2003.x09.wsat.CommitDocument)codegen.databinding.CommitOperationDatabindingSupporter.fromOM((org.apache.axis2.om.OMElement)msgContext.getEnvelope().getBody().getFirstChild().detach(), org.xmlsoap.schemas.ws.x2003.x09.wsat.CommitDocument.class));
						
					//Create a default envelop
					envelope = getSOAPFactory().getDefaultEnvelope();
					//Create a Omelement of the result if a result exist
					
					envelope.getBody().setFirstChild(codegen.databinding.CommitOperationDatabindingSupporter.toOM(param45));		
					
						
						
						
					}
			   
					
					
					if(methodName.equals("RollbackOperation")){
											
				
			org.xmlsoap.schemas.ws.x2003.x09.wsat.AbortedDocument param47 = null;
						
				//doc style
					param47 = skel.RollbackOperation((org.xmlsoap.schemas.ws.x2003.x09.wsat.RollbackDocument)codegen.databinding.RollbackOperationDatabindingSupporter.fromOM((org.apache.axis2.om.OMElement)msgContext.getEnvelope().getBody().getFirstChild().detach(), org.xmlsoap.schemas.ws.x2003.x09.wsat.RollbackDocument.class));
						
					//Create a default envelop
					envelope = getSOAPFactory().getDefaultEnvelope();
					//Create a Omelement of the result if a result exist
					
					envelope.getBody().setFirstChild(codegen.databinding.RollbackOperationDatabindingSupporter.toOM(param47));		
					
						
						
						
					}
			   
					
					
					if(methodName.equals("PrepareOperation")){
											
				
			org.xmlsoap.schemas.ws.x2003.x09.wsat.PrepareResponseDocument param49 = null;
						
				//doc style
					param49 = skel.PrepareOperation((org.xmlsoap.schemas.ws.x2003.x09.wsat.PrepareDocument)codegen.databinding.PrepareOperationDatabindingSupporter.fromOM((org.apache.axis2.om.OMElement)msgContext.getEnvelope().getBody().getFirstChild().detach(), org.xmlsoap.schemas.ws.x2003.x09.wsat.PrepareDocument.class));
						
					//Create a default envelop
					envelope = getSOAPFactory().getDefaultEnvelope();
					//Create a Omelement of the result if a result exist
					
					envelope.getBody().setFirstChild(codegen.databinding.PrepareOperationDatabindingSupporter.toOM(param49));		
					
						
						
						
					}
			   
			   
			   newMsgContext.setEnvelope(envelope);
            }
           
            

        } catch (Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
     
		 }
	
    }
    