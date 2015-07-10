package TT4J.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Root resource (exposed at "activelink" path)
 */
@Path("activelink/")
public class ActiveLink {

    private Map<String, String> persistance;

    public ActiveLink(){
        this.persistance = new HashMap<>();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET @Path("/GET/{UUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@PathParam("UUID") String uuid) {
        return persistance.getOrDefault(uuid, "no such key");
    }


    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client with generated UUID
     *
     * @return Response with UUID
     *
     * Example post form command line: curl -i -H"Content-Type:text/plain" POST -d 'link-1' http://localhost:8080/TT4J/activelink/POST/
     *
     */
    @POST @Path("/POST")
    @Consumes("text/plain")
    public Response postActiveLink(String link) {
        String uuid = UUID.randomUUID().toString();
        persistance.put(uuid, link);

        return Response.ok(uuid).build();
    }
}
