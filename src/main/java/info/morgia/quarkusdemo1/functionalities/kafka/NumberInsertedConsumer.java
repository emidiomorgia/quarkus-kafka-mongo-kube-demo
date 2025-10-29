package info.morgia.quarkusdemo1.functionalities.kafka;

import info.morgia.quarkusdemo1.functionalities.dto.NumberInsertedResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class NumberInsertedConsumer {

    @Incoming("number-inserted-in")
    public void cosume(NumberInsertedResult message){
        Log.debugf("Received message: {0}", message);


    }
}
