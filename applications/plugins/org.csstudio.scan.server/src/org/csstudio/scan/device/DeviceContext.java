/*******************************************************************************
 * Copyright (c) 2011 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The scan engine idea is based on the "ScanEngine" developed
 * by the Software Services Group (SSG),  Advanced Photon Source,
 * Argonne National Laboratory,
 * Copyright (c) 2011 , UChicago Argonne, LLC.
 *
 * This implementation, however, contains no SSG "ScanEngine" source code
 * and is not endorsed by the SSG authors.
 ******************************************************************************/
package org.csstudio.scan.device;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.csstudio.scan.ScanSystemPreferences;
import org.csstudio.scan.server.internal.PathStreamTool;

/** Context that maintains {@link Device}s: Create, start, get, stop.
 *
 *  <p>This is currently handling PV devices.
 *  Fundamentally, another type of device might
 *  be added in the future.
 *
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class DeviceContext
{
    /** Map of device names to {@link Device} based on alias or real name */
    final private Map<String, Device> device_by_alias = new HashMap<>(),
                                      device_by_name = new HashMap<>();
    
    /** @return {@link DeviceInfo}s for aliased devices, initialized from preferences */
    public static DeviceInfo[] getDeviceAliases() throws Exception
    {
        final String path = ScanSystemPreferences.getScanConfigPath();
        final InputStream config_stream = PathStreamTool.openStream(path);
        return new ScanConfig(config_stream).getDevices();
    }
    
    /** @return Default {@link DeviceContext}, initialized from preferences */
    public static DeviceContext getDefault() throws Exception
    {
        final DeviceInfo[] aliases = getDeviceAliases();

        // Create context with those devices
        final DeviceContext context = new DeviceContext();
        for (DeviceInfo alias : aliases)
            context.addPVDevice(alias);
		return context;
    }

    /** Initialize empty device context
     *  @see #getDefault()
     */
    public DeviceContext()
    {
        // NOP
    }

    /** Add a PV-based {@link Device} to the context.
     *  When adding a device with a name
     *  that is already in the context,
     *  the original device will be replaced.
     *
     *  @param info {@link DeviceInfo}
     *  @return Device that was added
     */
    public synchronized Device addPVDevice(final DeviceInfo info) throws Exception
    {
        final PVDevice device = new PVDevice(info);
        device_by_alias.put(device.getAlias(), device);
        if (! device.getAlias().equals(device.getName()))
            device_by_name.put(device.getName(), device);
        return device;
    }

    /** Get a device by alias or real name
     *  @param alias_or_name
     *  @return {@link Device} with that name
     *  @throws Exception when device name not known
     */
    public synchronized Device getDevice(final String alias_or_name) throws Exception
    {
        // Attempt lookup by alias
        Device device = device_by_alias.get(alias_or_name);
        // If not found, try real name
        if (device == null)
            device = device_by_name.get(alias_or_name);
        if (device != null)
            return device;
        
        // Name could be 'alias {"putCallback":true}' where
        // the basic alias is known, but without the annotation.
        // Silently add that as a new device, since PVManager treats it as
        // separate channel
        if (alias_or_name.endsWith(PVDevice.PUT_CALLBACK_ANNOTATION))
        {
            device = device_by_alias.get(alias_or_name.substring(0, alias_or_name.length() - PVDevice.PUT_CALLBACK_ANNOTATION.length()));
            if (device != null)
                return addPVDevice(new DeviceInfo(device.getName() + PVDevice.PUT_CALLBACK_ANNOTATION, alias_or_name));
        }
        // Not a known alias, nor magically added
        throw new Exception("Unknown device '" + alias_or_name + "'");
    }
    
    /** @return All Devices */
    public synchronized Device[] getDevices()
    {
        final Collection<Device> devs = device_by_alias.values();
        return devs.toArray(new Device[devs.size()]);
    }

    /** Start all devices
     *  @throws Exception on error with a device
     */
    public synchronized void startDevices() throws Exception
    {
        for (Device device : device_by_alias.values())
            device.start();
    }

    /** Stop all devices */
    public synchronized void stopDevices()
    {
        for (Device device : device_by_alias.values())
            device.stop();
    }
}
