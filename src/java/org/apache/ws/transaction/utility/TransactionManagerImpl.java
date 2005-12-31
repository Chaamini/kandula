/*
 * Copyright 2004 The Apache Software Foundation.
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
 */
package org.apache.ws.transaction.utility;

import java.rmi.RemoteException;

import org.apache.axis.message.addressing.EndpointReference;
import org.apache.ws.transaction.coordinator.ActivationStub;
import org.apache.ws.transaction.coordinator.CoordinationContext;
import org.apache.ws.transaction.coordinator.CoordinationService;
import org.apache.ws.transaction.coordinator.at.ATCoordinator;

public class TransactionManagerImpl {
	private static TransactionManagerImpl instance = new TransactionManagerImpl();

	private static ThreadLocal threadInfo = new ThreadLocal();

	public static TransactionManagerImpl getInstance() {
		return instance;
	}

	private TransactionManagerImpl() {
	}

	public void begin() throws RemoteException {
		begin(CoordinationService.getInstance().getActivationService());
	}

	public void begin(EndpointReference epr) throws RemoteException {
		if (threadInfo.get() != null)
			throw new IllegalStateException();
		CoordinationContext ctx;
		try {
			ctx = new ActivationStub(epr).createCoordinationContext(ATCoordinator.COORDINATION_TYPE_ID);
			threadInfo.set(new TransactionImpl(ctx));
		} catch (RemoteException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void commit() throws RemoteException {
		TransactionImpl tx = getTransaction();
		if (tx == null)
			throw new IllegalStateException();
		forget();
		tx.commit();
	}

	public void rollback() throws RemoteException {
		TransactionImpl tx = getTransaction();
		if (tx == null)
			throw new IllegalStateException();
		forget();
		tx.rollback();
	}

	public TransactionImpl suspend() {
		TransactionImpl tx = getTransaction();
		forget();
		return tx;
	}

	public void resume(TransactionImpl tx) {
		if (threadInfo.get() != null)
			throw new IllegalStateException();
		threadInfo.set(tx);
	}

	public void forget() {
		threadInfo.set(null);
	}

	public TransactionImpl getTransaction() {
		return (TransactionImpl) threadInfo.get();
	}
}