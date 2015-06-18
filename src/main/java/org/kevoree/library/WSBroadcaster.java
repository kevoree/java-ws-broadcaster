package org.kevoree.library;

import org.kevoree.annotation.*;
import org.kevoree.api.Context;

import java.net.URI;

/**
 *
 * Created by leiko on 18/06/15.
 */
@ComponentType(description = "Broadcast messages from a client to every other connected clients (without echoing to the sender)")
public class WSBroadcaster {

    @KevoreeInject
    private Context context;

    @Param(optional = false, defaultValue = "0.0.0.0")
    private String host;

    @Param(defaultValue = "9050")
    private int port;

    private fr.braindead.websocket.WSBroadcaster broadcaster;

    @Start
    public void start() throws Exception {
        if (host == null) {
            throw new Exception("Attribute \"host\" in "+context.getInstanceName()+"\" must be set");
        }
        broadcaster = new fr.braindead.websocket.WSBroadcaster(URI.create("ws://"+host+":"+port));
        broadcaster.start();
    }

    @Stop
    public void stop() {
        if (broadcaster != null) {
            broadcaster.stop();
            broadcaster = null;
        }
    }

    @Update
    public void update() throws Exception {
        stop();
        start();
    }
}




