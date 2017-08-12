package com.xml.parser.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xml.parser.client.parserService;

public class parserServiceImpl extends RemoteServiceServlet implements parserService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}