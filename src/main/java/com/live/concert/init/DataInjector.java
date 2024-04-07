package com.live.concert.init;

import com.live.concert.entity.Account;
import com.live.concert.entity.Concert;
import com.live.concert.repository.AccountRepository;
import com.live.concert.repository.ConcertRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInjector implements ApplicationRunner {
    Timestamp startSellingDate = Timestamp.valueOf(LocalDateTime.now().plusDays((long) (Math.random() * 30)));
    Timestamp finishSellingDate = Timestamp.valueOf(startSellingDate.toLocalDateTime().plusDays(30));

    EasyRandomParameters parameters = new EasyRandomParameters()

            .randomize(FieldPredicates.named("startSellingOn"), () -> startSellingDate)
            .randomize(FieldPredicates.named("finishSellingOn"), () -> finishSellingDate)
            .randomize(Timestamp.class, () -> Timestamp.valueOf(LocalDateTime.now().plusDays((long) (Math.random() * 30))))
            .randomize(FieldPredicates.named("totalTickets"), () -> (int) (Math.random() * 10000) + 1000)
            .randomize(FieldPredicates.named("totalTicketsSold"), () -> 0)
            .randomize(LocalDateTime.class, () -> LocalDateTime.now().plusDays((long) (Math.random() * 7)))
            .stringLengthRange(10, 50);

    EasyRandom easyRandom = new EasyRandom(parameters);

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        concertRepository.saveAll(createConcerts());
        accountRepository.saveAll(createAccount());
        System.out.println("Data injected into the database.");
    }

    private List<Concert> createConcerts() {
        return easyRandom.objects(Concert.class, 15).collect(Collectors.toList());
    }

    private List<Account> createAccount() {

        return easyRandom.objects(Account.class, 15).collect(Collectors.toList());
    }
}