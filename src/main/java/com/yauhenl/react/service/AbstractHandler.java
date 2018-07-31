package com.yauhenl.react.service;

import com.yauhenl.react.domain.Model;
import com.yauhenl.react.repository.AbstractRepository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class AbstractHandler<T extends Model> {
    AbstractRepository<T> repository;

    public Mono<ServerResponse> get(ServerRequest req) {
        return ServerResponse.ok().body(BodyInserters.fromObject(repository.findAll()));
    }

    public Mono<ServerResponse> find(ServerRequest req) {
        return ServerResponse.ok().body(BodyInserters.fromObject(repository.findByName(req.pathVariable("name"))));
    }
}
