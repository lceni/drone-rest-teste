package br.com.lceni.drone.resource;

import javax.ws.rs.core.Cookie;

import static org.junit.Assert.*;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Testa o serviço de movimentação e status do drone.
 * @author lceni
 *
 */
public class DroneTest {
    @Test public void testDrone() {
    	Client c = Client.create();
    	
    	WebResource status = c.resource("http://localhost:8080/drone/api/status");
    	WebResource move = c.resource("http://localhost:8080/drone/api/move");
    	WebResource left = c.resource("http://localhost:8080/drone/api/left");
    	WebResource right = c.resource("http://localhost:8080/drone/api/right");
    	
    	// faz o primeiro request e recupera o session id.
    	ClientResponse response = status.get(ClientResponse.class);
    	Cookie JSESSIONID = response.getCookies().toArray(new Cookie[0])[0];
    	
    	// testa se o estado inicial do drone está correto.
    	assertTrue("{'x':15,'y':15,'facing':'N'}".equals(response.getEntity(String.class)));
    	
    	// realiza algumas movimentações e checa novamente o estado.
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	assertTrue("{'x':15,'y':12,'facing':'N'}".equals(status.cookie(JSESSIONID).get(String.class)));
    	
    	// realiza uma série de manobras e testa novamente o status.
    	left.cookie(JSESSIONID).post();
    	left.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	left.cookie(JSESSIONID).post();
    	right.cookie(JSESSIONID).post();
    	left.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	move.cookie(JSESSIONID).post();
    	assertTrue("{'x':22,'y':15,'facing':'E'}".equals(status.cookie(JSESSIONID).get(String.class)));
    }
    
    @Test public void testBulk() {
    	Client c = Client.create();
    	
    	WebResource status = c.resource("http://localhost:8080/drone/api/status");
    	WebResource bulk = c.resource("http://localhost:8080/drone/api/bulk");
    	
    	// faz o primeiro request e recupera o session id.
    	ClientResponse response = status.get(ClientResponse.class);
    	Cookie JSESSIONID = response.getCookies().toArray(new Cookie[0])[0];
    	
    	// testa se o estado inicial do drone está correto.
    	assertTrue("{'x':15,'y':15,'facing':'N'}".equals(response.getEntity(String.class)));
    	
    	// primeiro teste
    	bulk.cookie(JSESSIONID).entity("Data: ['L', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M']").post();
    	assertTrue("{'x':1,'y':15,'facing':'W'}".equals(status.cookie(JSESSIONID).get(String.class)));
    	
    	// segundo teste
    	bulk.cookie(JSESSIONID).entity("Data: ['M', 'R', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M']").post();
    	assertTrue("{'x':0,'y':0,'facing':'N'}".equals(status.cookie(JSESSIONID).get(String.class)));
    	
    	// terceiro teste
    	bulk.cookie(JSESSIONID).entity("Data: ['R', 'R', 'R', 'R']").post();
    	assertTrue("{'x':0,'y':0,'facing':'N'}".equals(status.cookie(JSESSIONID).get(String.class)));
    	
    	// quarto teste
    	response = bulk.cookie(JSESSIONID).entity("Data: []").post(ClientResponse.class);
    	assertTrue(response.getStatus() == 400);
    	
    	// quinto teste
    	bulk.cookie(JSESSIONID).entity("Data: ['X', 'Y', 'Z', 'R']").post();
    	assertTrue("{'x':0,'y':0,'facing':'E'}".equals(status.cookie(JSESSIONID).get(String.class)));
    }
    
    @Test public void testLimits() {
    	Client c = Client.create();
    	
    	WebResource status = c.resource("http://localhost:8080/drone/api/status");
    	WebResource move = c.resource("http://localhost:8080/drone/api/move");
    	WebResource bulk = c.resource("http://localhost:8080/drone/api/bulk");
    	
    	// faz o primeiro request e recupera o session id.
    	ClientResponse response = status.get(ClientResponse.class);
    	Cookie JSESSIONID = response.getCookies().toArray(new Cookie[0])[0];
    	
    	// testa se o estado inicial do drone está correto.
    	assertTrue("{'x':15,'y':15,'facing':'N'}".equals(response.getEntity(String.class)));
    	
    	// testa o limite 0x0
    	bulk.cookie(JSESSIONID).entity("Data: ['M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'L', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M']").post();
    	assertTrue("{'x':0,'y':0,'facing':'W'}".equals(status.cookie(JSESSIONID).get(String.class)));
    	response = move.cookie(JSESSIONID).post(ClientResponse.class);
    	assertTrue(response.getStatus() == 412);
    	
    	// testa o limite 30x30
    	bulk.cookie(JSESSIONID).entity("Data: ['L', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'L', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M']").post();
    	assertTrue("{'x':30,'y':30,'facing':'E'}".equals(status.cookie(JSESSIONID).get(String.class)));
    	response = move.cookie(JSESSIONID).post(ClientResponse.class);
    	assertTrue(response.getStatus() == 412);
    }
}
