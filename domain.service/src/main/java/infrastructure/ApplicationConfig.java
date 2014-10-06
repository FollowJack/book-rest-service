package infrastructure;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import service.BookRestService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Deniel on 06.10.2014.
 */
@ApplicationPath("rs")
public class ApplicationConfig extends Application {

    private final Set<Class<?>> classes;

    public ApplicationConfig(){
        HashSet<Class<?>> c = new HashSet<Class<?>>();
        c.add(BookRestService.class);
        c.add(MOXyJsonProvider.class);

        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses(){
        return classes;
    }

}
