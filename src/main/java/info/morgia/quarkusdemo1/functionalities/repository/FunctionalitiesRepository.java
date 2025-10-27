package info.morgia.quarkusdemo1.functionalities.repository;

import com.mongodb.client.model.Filters;
import info.morgia.quarkusdemo1.functionalities.entity.Functionalities;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.BsonDocument;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class FunctionalitiesRepository implements ReactivePanacheMongoRepository<Functionalities> {

//    public List<Functionalities> findFiltered(@Nullable String nameFilter) {
//        Bson filter = new BsonDocument();
//
//        if (nameFilter != null && !nameFilter.isEmpty()) {
//            filter = Filters.regex("name", ".*" + Pattern.quote(nameFilter) + ".*", "i");
//        }
//
//        return this.mongoCollection().find(filter)
//                .into(new ArrayList<>());
//    }


    public Uni<List<Functionalities>> findFiltered(@Nullable String nameFilter) {
        Bson filter = new BsonDocument();

        if (nameFilter != null && !nameFilter.isEmpty()) {
            filter = Filters.regex("name", ".*" + Pattern.quote(nameFilter) + ".*", "i");
        }

        return this.mongoCollection().find(filter).collect().asList();
    }
}
