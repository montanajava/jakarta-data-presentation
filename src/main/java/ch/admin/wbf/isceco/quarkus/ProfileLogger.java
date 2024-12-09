package ch.admin.wbf.isceco.quarkus;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ConfigUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class ProfileLogger {

    void onStart(@Observes StartupEvent ev) {
        Log.info("The application is starting with profile %s".formatted(ConfigUtils.getProfiles()));
    }

}


