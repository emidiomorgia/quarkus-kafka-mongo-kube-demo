package info.morgia.quarkusdemo1.functionalities.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MongoEntity(collection = "functionalities")
@RegisterForReflection
@Data
@NoArgsConstructor
public class Functionalities {

    @BsonId
    @BsonProperty("_id")
    private ObjectId id;
    private String name;
    private String description;


};

