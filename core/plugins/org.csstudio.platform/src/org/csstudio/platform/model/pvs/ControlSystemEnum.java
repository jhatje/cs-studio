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
 /**
 * 
 */
package org.csstudio.platform.model.pvs;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;

/**
 * An enumeration for all available control system prefixes.
 * 
 * @author Sven Wende
 * 
 */
public enum ControlSystemEnum implements IAdaptable {
	SDS_SIMULATOR("local", null, false),

	DAL_SIMULATOR("simulator", "Simulator", true),

	DAL_EPICS("dal-epics", "EPICS", true),

	DAL_TINE("dal-tine", "TINE", true),

	DAL_TANGO("dal-tango", null, false),

	TINE("tine", "TINE", true),

	EPICS("epics", "EPICS", true),

	TANGO("tango", null, false),

	LOCAL("local", null, false),
	
	UNKNOWN("", null, false);

	private String _prefix;

	private String _dalName;

	private boolean _supportedByDAL;

	ControlSystemEnum(String prefix, String dalName, boolean supportedByDAL) {
		assert prefix != null;
		_prefix = prefix;
		_dalName = dalName;
		_supportedByDAL = supportedByDAL;
	}

	public String getPrefix() {
		return _prefix;
	}

	public String getResponsibleDalPlugId() {
		return _dalName;
	}

	public boolean isSupportedByDAL() {
		return _supportedByDAL;
	}

	@Override
	public String toString() {
		return name();
	}

	public static ControlSystemEnum findByPrefix(String prefix) {
		ControlSystemEnum result = UNKNOWN;
		for (ControlSystemEnum e : values()) {
			if (e.getPrefix().equalsIgnoreCase(prefix)) {
				result = e;
			}
		}

		return result;
	}

	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
}