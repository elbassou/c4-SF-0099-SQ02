package kata.commandeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class CommandeServiceApplication {

    public static void main(String[] args) {



        SpringApplication.run(CommandeServiceApplication.class, args);

     /*   ConfigurableApplicationContext cap
                = new ClassPathXmlApplicationContext(
                "resources/spring.xml");*/

    /*   final AbstractApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/application.xml");

        applicationContext.registerShutdownHook();
        applicationContext.refresh();

        applicationContext.getBean("myBean");
        applicationContext.getBean(MyBean.class);

        applicationContext.close();*/



    }

}
