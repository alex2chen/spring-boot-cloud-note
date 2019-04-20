package com.github.springkit.webflux;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MonoTest {
    @Test
    public void mono() {
        Mono.just("are").subscribe(System.out::println);
        Mono.empty().subscribe(System.out::println);
        Mono.fromSupplier(() -> "you").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("ok")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }
}
