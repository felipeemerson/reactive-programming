package com.vinsguru.felipe.sec04.assignment;

import com.vinsguru.common.Util;
import com.vinsguru.felipe.sec01.subscriber.SubscriberImpl;
import com.vinsguru.felipe.sec02.FileServiceImpl;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public Flux<String> read(Path path) {
        return Flux.fromStream(readLines(path));
    }

    public Stream<String> readLines(Path path) {
        try {
            return Files.lines(path); // this is not on demand
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        FileReaderServiceImpl fileReaderService = new FileReaderServiceImpl();
        SubscriberImpl subscriber = new SubscriberImpl();
        fileReaderService.read(Paths.get("src/main/resources/sec04/file.txt"))
                .subscribe(subscriber);
        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);

        subscriber.getSubscription().request(2);
        Util.sleepSeconds(2);
    }

}
