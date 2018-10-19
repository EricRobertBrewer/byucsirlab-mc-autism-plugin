package edu.byu.cs.autism;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;

public class CommunicationHandler extends HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String name = (String) exchange.getAttribute("name");
    }
}
