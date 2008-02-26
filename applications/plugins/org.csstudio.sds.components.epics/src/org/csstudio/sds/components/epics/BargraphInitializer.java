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
 package org.csstudio.sds.components.epics;

import org.csstudio.sds.components.model.BargraphModel;
import org.csstudio.sds.model.initializers.AbstractControlSystemSchema;
import org.csstudio.sds.model.initializers.AbstractWidgetModelInitializer;

/**
 * Initializes a bargraph with EPICS specific property values.
 * 
 * @author Kai Meyer
 * 
 */
public final class BargraphInitializer extends AbstractWidgetModelInitializer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initialize(final AbstractControlSystemSchema schema) {
		initializeDynamicProperty(BargraphModel.PROP_MIN, "$channel$.LOPR");
		initializeDynamicProperty(BargraphModel.PROP_MAX, "$channel$.HOPR");
		initializeDynamicProperty(BargraphModel.PROP_HIHI_LEVEL,
				"$channel$.HIHI");
		initializeDynamicProperty(BargraphModel.PROP_HI_LEVEL, "$channel$.HIGH");
		initializeDynamicProperty(BargraphModel.PROP_LOLO_LEVEL,
				"$channel$.LOLO");
		initializeDynamicProperty(BargraphModel.PROP_LO_LEVEL, "$channel$.LOW");
		initializeDynamicProperty(BargraphModel.PROP_FILL, "$channel$");
		// initializeDynamicProperty(BargraphModel.PROP_FILL, "$channel$.VAL");
	}
}
