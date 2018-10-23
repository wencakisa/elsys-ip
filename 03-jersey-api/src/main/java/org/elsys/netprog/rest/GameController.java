package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/game")
public class GameController {

	private static List<Game> games = new ArrayList<>();

	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException {
		int rand;

		do {
			rand = ThreadLocalRandom.current().nextInt(1023, 9877 + 1);
		} while (!Game.isValidNumber(rand));

		Game newGame = new Game(rand);
		games.add(newGame);

		return Response.created(new URI("/games")).entity(newGame.getGameId()).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception {
		Integer guessNumber;

		try {
			guessNumber = Integer.valueOf(guess);
		} catch (NumberFormatException ex) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		for (Game game : games) {
			if (game.getGameId().equals(gameId)) {
				if (!Game.isValidNumber(guessNumber)) {
					return Response.status(Response.Status.BAD_REQUEST).build();
				}

				return Response.ok().entity(new GameGuess(game, guessNumber)).build();
			}
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() {
		return Response.ok().entity(games).build();
	}
}
