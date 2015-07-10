package TT4J.resources;

import TT4J.persistance.PersistentMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Root resource (exposed at "activelink" path)
 */
@Path("activelink/")
public class ActiveLinkResources {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET @Path("/GET/{UUID}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@PathParam("UUID") String uuid) {
        return PersistentMap.getOrDefault(uuid, "no such key");
    }


    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client with generated UUID
     *
     * @return Response with UUID
     *
     * Example post form command line: curl -i -H"Content-Type:text/plain" -d 'link-1' http://localhost:8080/TT4J/activelink/POST/
     *
     */
    @POST @Path("/POST")
    @Consumes("text/plain")
    public Response postActiveLink(String link) {
        String uuid = UUID.randomUUID().toString();
        try {
            PersistentMap.put(uuid, link);
            return Response.ok(uuid).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }
}
