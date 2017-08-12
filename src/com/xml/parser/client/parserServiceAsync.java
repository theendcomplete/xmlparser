package com.xml.parser.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface parserServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
