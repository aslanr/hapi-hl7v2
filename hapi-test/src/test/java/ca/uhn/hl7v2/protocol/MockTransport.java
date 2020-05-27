/*
 * Created on 17-May-2004
 */
package ca.uhn.hl7v2.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uhn.hl7v2.protocol.impl.AbstractTransport;

/**
 * A mock <code>TransportLayer</code> with which you ca specify messages 
 * that the caller receives, and check messages the caller sends.  
 * 
 * @author <a href="mailto:bryan.tripp@uhn.on.ca">Bryan Tripp</a>
 * @version $Revision: 1.1 $ updated on $Date: 2007-02-19 02:24:38 $ by $Author: jamesagnew $
 */
public class MockTransport extends AbstractTransport implements TransportLayer {

    private final List<Transportable> mySentMessages;
    //private Transportable myNextReceived;
    private final List<Transportable> myNextReceivedMsgs;
    private final Map<String, Object> myMetadata;

    public MockTransport() {
        mySentMessages = new ArrayList<>();
        myNextReceivedMsgs = new ArrayList<>();
        myMetadata = new HashMap<>();
        myMetadata.put("MOCK", "This is a mock transport layer");
    }

    /*
     * Stores message for subsequent availability via getSentMessages()
     */
    public void doSend(Transportable arg0) {
        mySentMessages.add(arg0);
    }
    
    public void setNextReceived(Transportable toBeReceived) {
        //myNextReceived = toBeReceived;
        myNextReceivedMsgs.add(toBeReceived);
    }
    
    public List<Transportable> getSentMessages() {
        return mySentMessages;
    }
    
    public Transportable getLastSent() {
        return mySentMessages.get(mySentMessages.size()-1);
    }

    /**
     * Returns the next message in the to be received list (as provided in 
     * the constructor). 
     */
    public synchronized Transportable doReceive() {
        Transportable next = null;
        if (myNextReceivedMsgs.size() > 0) {
            next = myNextReceivedMsgs.remove(0);
        } else {
        	try {
				Thread.sleep(100);
			} catch (InterruptedException ignored) {
			}
        }
        return next;
        //return (Transportable) myMessagesToBeReceived.get(myNumberReceived++);
    }

    /** 
     * Returns a map with an entry saying this is a mock transport layer.  
     */
    public Map<String, Object> getCommonMetadata() {
        return myMetadata;
    }

    /** 
     * Does nothing
     */
    public void doConnect() {
    }

    /**
     * Does nothing
     */
    public void doDisconnect() {
    }

}
