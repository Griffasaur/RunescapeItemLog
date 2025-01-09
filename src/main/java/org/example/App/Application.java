package org.example;
import org.example.Service.ItemService;
import org.example.App.ApplicationMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
 public class Application implements CommandLineRunner {

    private final ApplicationContext context;

    public Application (ApplicationContext context) {
        this.context = context;
    }

     public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
     }

     @Bean
     public RestTemplate restTemplate() {
         RestTemplate restTemplate = new RestTemplate();

         restTemplate.getInterceptors().add((request, body, execution) -> {
                 request.getHeaders().set(HttpHeaders.USER_AGENT, "Runescape Test Project For Spring Boot (discord.gg/griffasaur)");
                    return execution.execute(request, body);
                    });
         return restTemplate;
     }


    @Override
    public void run(String... args) {
        try {
            System.out.println("Starting item population...");
            ItemService itemService = context.getBean(ItemService.class);
            itemService.populateItems();
            System.out.println("Items successfully populated!");
        } catch (Exception e) {
            System.err.println("Error populating items: " + e.getMessage());
            e.printStackTrace();
            return; // Ensure the application halts on failure if needed
        }

        System.out.println("Starting Application Menu...");
        ApplicationMenu applicationMenu = context.getBean(ApplicationMenu.class);
        applicationMenu.printMainMenu();
        System.out.println("Application Menu displayed.");
    }
 }

