package info.morgia.quarkusdemo1.functionalities.api;


import info.morgia.quarkusdemo1.functionalities.dto.FunctionalityPostRequestDto;
import info.morgia.quarkusdemo1.functionalities.service.FunctionalityService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;


@Path("/functionalities")
public class FunctionalitiesController {

    @Inject
    FunctionalityService functionalityService;

    @Context
    UriInfo uriInfo;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getFunctionalitiesList(@QueryParam("nameFilter") @Nullable String nameFilter) {
        

        return functionalityService.findFiltered(nameFilter)
                .onItem()
                .transform(list -> Response.ok(list).build());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getFunctionalitiesById(@PathParam("id") String id) {
        return functionalityService.getById(id).map(
                dto ->
                dto.map(mappedDto -> Response.ok(mappedDto).build())
                        .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build())
        );

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> saveFunctionality(FunctionalityPostRequestDto request){
        return functionalityService.saveFunctionality(request)
                .onItem()

                .transform(result -> {
                    var uri = UriBuilder.fromUri(uriInfo.getAbsolutePath())
                            .path(result.id()) // supponendo savedDto.id sia la chiave
                            .build();
                    return Response.created(uri).entity(result).build();
                });
    }
}
