/* 
 * Copyright (c) 2008 Stiftung Deutsches Elektronen-Synchrotron, 
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS. 
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND 
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE 
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR 
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. 
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, 
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION, 
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS 
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY 
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */
 package org.csstudio.platform.internal.simpledal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.csstudio.platform.internal.simpledal.ProcessVariableConnectionServiceTest.ProcessVariableListener;
import org.csstudio.platform.model.pvs.IProcessVariableAddress;
import org.csstudio.platform.simpledal.ValueType;

/**
 * Test implementation for {@link ProcessVariableConnectionService} which tests
 * the asynchronous single get operations.
 * 
 * @author swende
 * 
 */
public class ProcessVariableConnectionServiceTestForSyncOperations extends
		AbstractProcessVariableConnectionServiceTest {


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doConnectionTest(
			IProcessVariableAddress processVariableAddress, ValueType valueType)
			throws InterruptedException {
		
		// we get the values synchronously
		Object value = "";
		String error = "";
		try {
			value = _service.getValue(processVariableAddress, valueType);
		} catch (Throwable e) {
			error = e.getMessage();
		}

		// print the values and errors to the console
		printReceivedValue(processVariableAddress, valueType, value, error);

		// we must receive a value
		assertNotNull(value);

		// the value must fit the expected type
		Class expectedJavaType = valueType.getJavaType();
		Class type = value.getClass();

		assertTrue("Values needs to be assignable from [" + expectedJavaType
				+ "]", expectedJavaType.isAssignableFrom(type));

		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected long getSleepTime() {
		return 10000;
	}

}
