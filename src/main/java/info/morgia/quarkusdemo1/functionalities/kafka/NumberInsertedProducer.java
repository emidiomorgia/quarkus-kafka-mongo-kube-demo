package info.morgia.quarkusdemo1.functionalities.kafka;

import info.morgia.quarkusdemo1.functionalities.dto.NumberInsertedResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class NumberInsertedProducer {

    @Inject
    @Channel("number-inserted")
    Emitter<NumberInsertedResult> numberInsertedResultEmitter;

    public void send(NumberInsertedResult result){
        numberInsertedResultEmitter.send(result);
    }
}
