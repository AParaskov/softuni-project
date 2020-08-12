package gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GameStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameStoreApplication.class, args);
    }

}
