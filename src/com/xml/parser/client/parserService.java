package com.xml.parser.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("parserService")
public interface parserService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use parserService.App.getInstance() to access static instance of parserServiceAsync
     */
    public static class App {
        private static parserServiceAsync ourInstance = GWT.create(parserService.class);

        public static synchronized parserServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
