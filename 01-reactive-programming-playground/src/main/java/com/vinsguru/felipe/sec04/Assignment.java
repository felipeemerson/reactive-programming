package com.vinsguru.felipe.sec04;

import com.vinsguru.felipe.sec01.subscriber.SubscriberImpl;
import com.vinsguru.felipe.sec04.assignment.FileReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Assignment implements FileReaderService {

    private static final Logger log = LoggerFactory.getLogger(Assignment.class);

    @Override
    public Flux<String> read(Path path) {
        return Flux.generate(
                () -> openFile(path),
                this::readFile,
                this::closeFile
        );
    }

    private BufferedReader openFile(Path path) throws IOException {
        log.info("opening file");
        return Files.newBufferedReader(path);
    }

    private BufferedReader readFile(BufferedReader reader, SynchronousSink<String> sink) {
        try {
            var line = reader.readLine();
            log.info("reading line: {}", line);

            if (Objects.isNull(line)) {
                sink.complete();
            } else {
                sink.next(line);
            }
        } catch (IOException e) {
            sink.error(e);
        }

        return reader;
    }

    private void closeFile(BufferedReader reader) {
        try {
            reader.close();
            log.info("closed file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Assignment fileReader = new Assignment();
        Flux<String> flux = fileReader.read(Path.of("src/main/resources/sec04/file.txt"));
        var subscriber = new SubscriberImpl();
        flux.subscribe(subscriber);
        subscriber.getSubscription().request(18);
        subscriber.getSubscription().cancel();

    }

}
