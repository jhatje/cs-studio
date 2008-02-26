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
 package org.csstudio.utility.adlconverter.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.csstudio.platform.logging.CentralLogger;

/**
 * 
 * Class which provides methods for parsing ADL files into a nested Map
 * with almost exactly the same structure as original ADL text file.
 * @author Tomaz Hocevar
 * 
 * @author $Author$
 * @version $Revision$
 * @since 08.10.2007
 */
public final class ParserADL {

    /**
     * Main method of class ParserADL.<br/>
     * Reads form an adl file and creates a structure of ADLWidget.
     * @return the root Object with contain the structure of the Widget.
     */
    public static ADLWidget getNextElement(File file) {
        int lineNr=0;
        ADLWidget root = new ADLWidget(file.getAbsolutePath(),null,lineNr++);
        ADLWidget children= root;
        BufferedReader buffRead = null;
        try {        

            buffRead = new BufferedReader(new FileReader(file));
            String line;

            while((line = buffRead.readLine()) != null){
                if(line.trim().length()>0){
                    if(line.contains("{")){ //$NON-NLS-1$
                        children = new ADLWidget(line,children, lineNr++);
                    }else if (line.contains("}")){ //$NON-NLS-1$
                        children.getParent().addObject(children);
                        children = children.getParent();
                    }else{
                        children.addBody(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            CentralLogger.getInstance().error(ParserADL.class, e);
        } catch (IOException e) {
            CentralLogger.getInstance().error(ParserADL.class, e);
        } finally{
            try {
                if(buffRead!=null){
                    buffRead.close();
                }
            } catch (IOException e) {
                CentralLogger.getInstance().error(ParserADL.class,e);
            }
        }
        return root;
    }
}
