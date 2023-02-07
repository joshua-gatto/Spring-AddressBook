package org.lab1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Container {

    private static final Logger log = LoggerFactory.getLogger(Container.class);


    public static void main(String[] args) {
        SpringApplication.run(Container.class);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository abRepository){
        return (args) -> {
            //save an AddressBook
            BuddyInfo Alice = new BuddyInfo("Alice", "613-499-3231");
            BuddyInfo Bob = new BuddyInfo("Bob", "613-842-1395");
            BuddyInfo Charlie = new BuddyInfo("Charlie", "613-291-9950");
            BuddyInfo b[]= {Alice, Bob, Charlie};
            abRepository.save(new AddressBook(b));

            //Print All BuddyInfo
            log.info("Collecting all AddressBooks and displaying their contents");
            for(AddressBook book : abRepository.findAll()){
                log.info(book.toString());
            }
        };
    }
}
