package info.morgia.quarkusdemo1.functionalities.clients;

import info.morgia.quarkusdemo1.functionalities.dto.TodoResponseDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;



@ApplicationScoped
@RegisterRestClient
public interface TodoClient {
    @GET
    @Path("/todos")
    Uni<TodoListResponseDto> getTodos();
}
