package com.github.asufana.undertow;

import io.undertow.*;
import io.undertow.server.*;
import io.undertow.util.*;

public class Server {
    
    public Stoppable start() {
        final Undertow server = Undertow.builder()
                                        .addHttpListener(8080, "localhost")
                                        .setHandler(new HttpHandler() {
                                            @Override
                                            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                                                exchange.getResponseHeaders()
                                                        .put(Headers.CONTENT_TYPE,
                                                             "text/plain");
                                                exchange.getResponseSender()
                                                        .send("Hello World");
                                            }
                                        })
                                        .build();
        server.start();
        return new Stoppable() {
            @Override
            public void stop() {
                server.stop();
            }
        };
    }
}
