package learn.microservices.multiplicator.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public boolean autoIndexCreation() {
        return true;
    }

    @Override
    public String getDatabaseName() {
        return "multiplicator";
    }

}
