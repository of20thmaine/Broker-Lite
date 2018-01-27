//package brokerlite;
//
//import javafx.concurrent.ScheduledService;
//import javafx.concurrent.Task;
//import javafx.concurrent.WorkerStateEvent;
//import javafx.util.Duration;
//import javax.ws.rs.core.MediaType;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.GenericType;
//import com.sun.jersey.api.client.WebResource;
//
//public class StockController {
//	
//	public static StockQuote getStockQuote(String symbol) {
//        Client c = Client.create();
//        WebResource r = c.resource("http://dev.markitondemand.com/Api/v2/Quote?symbol="+symbol);
//        ClientResponse response = r.accept( MediaType.APPLICATION_XML_TYPE).get(ClientResponse.class);
//        StockQuote stockQuote =  response.getEntity(new GenericType<StockQuote>() {});
//        System.out.println("Result REST call:"+stockQuote);
//        return stockQuote;
//    }   
//
//}
//
//
