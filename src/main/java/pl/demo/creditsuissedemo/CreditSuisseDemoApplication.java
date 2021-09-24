package pl.demo.creditsuissedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.demo.creditsuissedemo.logic.MainLogic;

@SpringBootApplication
public class CreditSuisseDemoApplication implements CommandLineRunner {

    private final MainLogic mainLogic;

    public static void main(String[] args) {
        SpringApplication.run(CreditSuisseDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("Error, there is no path provided");
        } else {
            mainLogic.runAll(args[0]);
        }
    }

    @Autowired
    public CreditSuisseDemoApplication(MainLogic mainLogic) {
        this.mainLogic = mainLogic;
    }
}
