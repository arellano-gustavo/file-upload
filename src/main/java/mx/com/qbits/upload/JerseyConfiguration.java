package mx.com.qbits.upload;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import mx.com.qbits.upload.controller.BookController;

@Configuration
@ApplicationPath("rest")
public class JerseyConfiguration extends ResourceConfig {
    @PostConstruct
    public void setUp() {
        register(MultiPartFeature.class);
        register(BookController.class);
    }
}
