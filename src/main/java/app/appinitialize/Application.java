package app.appinitialize;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "app")
public class Application {

    public static void main(String[] args) {
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	System.out.println("#####################################");
        System.out.println("### Beans provided by Spring Boot ###");
        System.out.println("#####################################");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println("### "+beanName);
        }
        System.out.println("###");
    	System.out.println("#####################################");
        System.out.println("###       Security provided       ###");
        System.out.println("#####################################");
		Provider[] pvds = Security.getProviders();
		int i = 0;
		for (Provider provider : pvds) {
			i++;
			System.out.println("### [" + i + "] - Provider name: " + provider.getName());
			System.out.println("### Provider version number: " + provider.getVersion());
			System.out.println("### Provider information: " + provider.getInfo());
		}
		
        System.out.println("###");
        System.out.println("#####################################");
    }


}
