package info.morgia.quarkusdemo1.functionalities.api;


import info.morgia.quarkusdemo1.functionalities.clients.TodoClient;
import info.morgia.quarkusdemo1.functionalities.dto.InsertNumberRequestDto;
import info.morgia.quarkusdemo1.functionalities.dto.MyDto;
import info.morgia.quarkusdemo1.functionalities.dto.NumberInsertedResult;
//mport info.morgia.quarkusdemo1.functionalities.kafka.NumberInsertedProducer;
import info.morgia.quarkusdemo1.functionalities.kafka.NumberInsertedProducer;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/demo")
public class DemoController {

    @Inject
    @RestClient
    TodoClient todoClient;

    @Context
    UriInfo uriInfo;

    @Inject
    NumberInsertedProducer numberInsertedProducer;


    @GET
    @Path("/rest-client")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDemo() {

        return todoClient.getTodos().
                map(response -> Response.ok(response).build());

    }

    @GET
    @Path("/uni-demo1")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDemo1() {
        int first = doCpuHeavyA();
        double second = doCpuHeavyB();
        double res = first + second;

        return Response.ok(res).build();
    }


    @GET
    @Path("/uni-demo2")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDemo2() {


        Uni<Integer> u1 = Uni.createFrom().item(this::doCpuHeavyA)
                .runSubscriptionOn(cpuExecutor);

        Uni<Double> u2 = Uni.createFrom().item(this::doCpuHeavyB)
                .runSubscriptionOn(cpuExecutor);

        // Esegue entrambe in parallelo e combina i risultati
        return Uni.combine().all()
                .unis(u1, u2)
                .asTuple()
                .map(tuple -> Response.ok(new MyDto(tuple.getItem1(), tuple.getItem2())).build());

    }

    @GET
    @Path("/uni-demo3")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDemo3() {

        Uni<List<MyDto>> uniDtos = Uni.createFrom().item(List.of(
                new MyDto(2, 2),
                new MyDto(4, 4),
                new MyDto(5, 6)
        ));

        // Sottoscrizione per stampare ogni elemento della lista
        return uniDtos
                .map(list -> list.stream().map(e -> new MyDto(e.getN1()+1, e.getN2()+2)).toList())
                .onItem().invoke(l -> l.forEach(System.out::println))
                .map(l -> Response.ok(l).build());
    }

    @GET
    @Path("/uni-demo4")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getDemo4() {

        Uni<List<MyDto>> uniDtos = Uni.createFrom().item(List.of(
                new MyDto(2, 2),
                new MyDto(4, 4),
                new MyDto(5, 6)
        ));

        // Sottoscrizione per stampare ogni elemento della lista
        return uniDtos
                .map(list -> list.stream().map(e -> new MyDto(e.getN1()+1, e.getN2()+2)).toList())
                .onItem().invoke(l -> l.forEach(System.out::println))
                .map(l -> Response.ok(l).build());
    }

    @POST
    @Path("/insert-number")
    public Response insertNumber(InsertNumberRequestDto request) {
        numberInsertedProducer.send(new NumberInsertedResult(request.number(), Instant.now()));

        var uri = UriBuilder.fromUri(uriInfo.getAbsolutePath())
                .path(String.valueOf(request.number())) // supponendo savedDto.id sia la chiave
                .build();

        return Response.created(uri).entity(request.number()).build();
    }

    private final ExecutorService cpuExecutor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private int doCpuHeavyA() {
        // simulazione di calcolo CPU-intensive
        long sum = 0;
        for (int i = 0; i < 100_000_000; i++) sum += i;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.error("Error during sleep", e);
        }

        return (int) (sum % 1000);
    }

    private double doCpuHeavyB() {
        double res = 1.0;
        for (int i = 1; i < 50_000_000; i++) res = Math.sin(res + i);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Log.error("Error during sleep", e);
        }

        return res;
    }


}
