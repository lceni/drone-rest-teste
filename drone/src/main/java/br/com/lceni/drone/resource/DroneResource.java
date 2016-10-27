package br.com.lceni.drone.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("api")
public class DroneResource {
	
	@Context HttpServletRequest request;
	
	private Drone getDrone() {
		Drone r = (Drone) request.getSession().getAttribute("drone");
		if (r == null) {
			r = new Drone(15,15,30,30,'N');
			request.getSession().setAttribute("drone", r);
		}
		
		return r; 
	}
	
	/**
	 * Tenta mover o drone, dependendo para onde estiver apontando, e onde estiver posicionado, retorna um erro HTTP.
	 * @return
	 */
	@POST
	@Path("/move")
    public Response move() {
		boolean status = getDrone().move();
		
        return Response.status(status ? Status.OK : Status.PRECONDITION_FAILED).build();
    }
	
	/**
	 * Gira o drone para a direita.
	 */
	@POST
	@Path("/right")
	public Response right() {
		getDrone().right();
		
		return Response.status(Status.OK).build();
	}
	
	/**
	 * Gira o drone para a esquerda.
	 */
	@POST
	@Path("/left")
	public Response left() {
		getDrone().left();
		
		return Response.status(Status.OK).build();
	}
	
	/**
	 * Retorna o status do drone.
	 */
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/bulk")
	public Response bulk(String data) {
		char[] bulk = data.replaceAll("[^LRM]", "").toCharArray();
		
		// valida se existe algum caracter válido.
		if (bulk.length == 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		// itera sobre os comandos recebidos e executa-os na interface do drone.
		for (char c : bulk) {
			switch (c) {
			case 'L': getDrone().left(); break;
			case 'R': getDrone().right(); break;
			case 'M': getDrone().move(); break;
			}
		}
		
		return Response.status(Status.OK).build();
	}
	
	/**
	 * Retorna o status do drone.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	public String status() {
		return getDrone().status();
	}
}
