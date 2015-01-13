package org.spend.reportserver.integration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import static play.Logger.debug;

@Configuration
@ComponentScan
public class Global extends GlobalSettings {
    private AnnotationConfigApplicationContext context;

    @Override
    public void onStart(Application application) {
        super.onStart(application);
        context = new AnnotationConfigApplicationContext(Global.class);

        for (String bean : context.getBeanDefinitionNames()) {
            debug(bean);
        }
    }

    @Override
    public void onStop(final Application app) {
        context.close();
        super.onStop(app);
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
        return context.getBean(clazz);
    }
}

