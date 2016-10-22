package ru.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    private static DBInit dbInit;

    @Autowired
    private void setDbInit(DBInit dbInit) {
        ServerApplication.dbInit = dbInit;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

        dbInit.populate();
    }
}
