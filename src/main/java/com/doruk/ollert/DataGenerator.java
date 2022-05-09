package com.doruk.ollert;

import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.SheetPart;
import com.doruk.ollert.entity.User;
import com.doruk.ollert.repository.SheetPartRepository;
import com.doruk.ollert.repository.SheetRepository;
import com.doruk.ollert.repository.UserRepository;
import com.doruk.ollert.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataGenerator {


    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, SheetRepository sheetRepository, SheetPartRepository sheetPartRepository, UserService userService) {
        return args -> {
            User user = new User();
            user.setUsername("doruk");
            user.setPassword("12345");
            userService.saveUser(user);

            Sheet table = new Sheet();
            table.getUsers().add(user);
            table.setName("AAAAAA");
            sheetRepository.save(table);

            SheetPart column =  new SheetPart();
            column.setName("To do");
            column.setTasks(List.of("Finish the app and test", "Finish it"));
            column.setIndex(0);
            column.setSheet(table);
            sheetPartRepository.save(column);

            SheetPart column1 =  new SheetPart();
            column1.setName("Doing");
            column1.setTasks(List.of("Database Stuff"));
            column1.setSheet(table);
            column1.setIndex(1);
            sheetPartRepository.save(column1);


            column = sheetPartRepository.findById(3L).orElse(null);
            column.getTasks().set(1, "dont finish it");
            sheetPartRepository.save(column);







        };
    }
}