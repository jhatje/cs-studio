/*
 * Copyright (c) 2011 Stiftung Deutsches Elektronen-Synchrotron,
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
package org.csstudio.archive.common.engine.model;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the cached constructor performance of the DBR types and their newInstance factory method.
 * 
 * @author bknerr
 * @since 05.05.2011
 */
public class CachedConstructorPerformanceUnitTest {
    
    private static final int ITERATIONS = 100000;

    
    /**
     * Essential parts copied out of {@link gov.aps.jca.dbr.DBRType.DBRType(String, int, Class)}
     * 
     * @author bknerr
     * @since 05.05.2011
     */
    public static class MyDBRType {
        Class<DBRTest> _class;
        Constructor<DBRTest> _ctor;

        MyDBRType(/* final String name, final int value, */final Class<DBRTest> clazz) {
            _class=clazz;
            try {
                _ctor = _class.getConstructor( new Class[] {Integer.TYPE} );
            } catch( Exception ex ) {
                // Empty
            }
        }
        public DBRTest newInstance( int count ) {
            try {
              return (DBRTest)_ctor.newInstance( new Object[] {new Integer( count )} );
            } catch( Exception ex ) {
                // Empty
            }
            return null;
        }
        public DBRTest newInstanceImproved( int count ) {
            try {
                // better use valueOf when x is expected to be very often one out of 0,-1, 1, 2 (that is 'common' cases) -   
                return (DBRTest)_ctor.newInstance( new Object[] {Integer.valueOf( count )} ); 
            } catch( Exception ex ) {
                // Empty
            }
            return null;
        }
    }
    
    /**
     * Essential parts copied out of {@link gov.aps.jca.dbr.DBR_CTRL_Double}.
     * Left out supertypes.
     * 
     * @author bknerr
     * @since 05.05.2011
     */
    public static final class DBRTest {
        int _count;
        /**
         * Constructor.
         */
        public DBRTest(final int count) {
            _count = count;
        }
        public int getCount() {
            return _count;
        }
    }
    
    @Test
    public void testCachedConstructorPerformance() {
        
       
        long cachedConstructorPerf = runCachedConstructorIterations();

        long improvedCachedConstructorPerf = runCachedImprovedConstructorIterations();
        Assert.assertTrue(cachedConstructorPerf > improvedCachedConstructorPerf);
        System.out.println(cachedConstructorPerf*1.0/improvedCachedConstructorPerf + " times longer than cached constructor creation with Integer.valueOf.");

        long normalPerf = runCompletelyNormalConstructorIterations();

        Assert.assertTrue(cachedConstructorPerf > normalPerf);
        Assert.assertTrue(cachedConstructorPerf > 5*normalPerf);
        System.out.println("And more than " + cachedConstructorPerf/normalPerf + " times longer than simple non cached creation with new .");
    }
    
    

    private long runCachedImprovedConstructorIterations() {
        long cachedConstructorPerf = 0;
        
        Integer r = 0;
        long start;
        try {
            MyDBRType type = new MyDBRType(DBRTest.class); // the map lookup in _cached forValue
            int count = 1;

            start = System.nanoTime();                               // measure only creations
            for (int i = 0; i < ITERATIONS; i++) {
                DBRTest instance = type.newInstanceImproved(count);
                r += instance.getCount();                            // avoid compiler optimization for non used/referred to objects
            }
            cachedConstructorPerf = System.nanoTime() - start;
        } catch (final Exception e) {
            //EMPTY
        }
        return cachedConstructorPerf;
    }

    private long runCachedConstructorIterations() {
        long cachedConstructorPerf = 0;
        
        Integer r = 0;
        long start;
        try {
            MyDBRType type = new MyDBRType(DBRTest.class);
            int count = 1;

            start = System.nanoTime();                              // measure many creations
            for (int i = 0; i < ITERATIONS; i++) {
                DBRTest instance = type.newInstance(count);
                r += instance.getCount();                           // avoid compiler optimization for non used/referred to objects
            }
            cachedConstructorPerf = System.nanoTime() - start;
            
        } catch (final Exception e) {
            //EMPTY
        }
        return cachedConstructorPerf;
    }

    
    private long runCompletelyNormalConstructorIterations() {
        Integer r;
        long start;
        r=0;
        
        start = System.nanoTime();                                  // measure many creations    
        for (int i = 0; i < 100000; i++) {                      
            final DBRTest myDBR = new DBRTest(1);
            r += myDBR.getCount();                                  // avoid compiler optimization for non used/referred to objects
        }
        long normalPerf = System.nanoTime()-start;
        
        return normalPerf;
    }
}