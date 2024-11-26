package com.vinsguru.felipe.sec02;

import com.vinsguru.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public Mono<String> read(String fileName) {
        log.info("creating read mono");

        Path outputDirectory = Paths.get("src/main/resources/sec02");
        Path filePath = outputDirectory.resolve(fileName);

        if (!Files.exists(filePath)) {
            return Mono.error(new RuntimeException("File not exists"));
        }

        return Mono.fromCallable(() -> {
            List<String> lines = Files.readAllLines(filePath);
            return String.join("\n", lines);
        });
    }

    @Override
    public Mono<Void> write(String fileName, String content) {
        log.info("creating write mono");
        Path outputDirectory = Paths.get("src/main/resources/sec02");
        Path filePath = outputDirectory.resolve(fileName);

        if (Files.exists(filePath)) {
            return Mono.error(new RuntimeException("File already exists"));
        }

        return Mono.fromCallable(
            () -> {
                Files.createFile(filePath);
                Files.write(filePath.toAbsolutePath(), content.getBytes());
                return null;
            }
        );
    }

    @Override
    public Mono<Void> delete(String fileName) {
        log.info("creating delete mono");
        Path outputDirectory = Paths.get("src/main/resources/sec02");
        Path filePath = outputDirectory.resolve(fileName);

        if (!Files.exists(filePath)) {
            return Mono.error(new RuntimeException("File not exists"));
        }

        return Mono.fromCallable(
                () -> {
                    Files.delete(filePath);
                    return null;
                }
        );
    }

    public static void main(String[] args) {
        FileServiceImpl fileService = new FileServiceImpl();

        String file1 = "teste1.txt";

        fileService
                .write(file1, "Ola, Mundo!\nHello, World!");
//                .subscribe(
//                        v -> log.info("{} created", file1),
//                        err -> log.error("failed to create {}", file1, err),
//                        () -> log.info("creation of {} completed", file1)
//                );

        fileService
                .read(file1);
//                .subscribe(
//                        System.out::println,
//                        err -> log.error("failed to read {}", file1, err),
//                        () -> log.info("read of {} completed", file1)
//                );

        fileService
                .delete(file1);
//                .subscribe(
//                        v -> log.info("{} deleted", file1),
//                        err -> log.error("failed to delete {}", file1, err),
//                        () -> log.info("deletion of {} completed", file1)
//                );
    }

}
