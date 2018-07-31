package com.yauhenl.react.service;

import com.yauhenl.react.domain.College;
import com.yauhenl.react.domain.Student;
import com.yauhenl.react.domain.University;
import com.yauhenl.react.repository.CollegeRepository;
import com.yauhenl.react.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class UniversityHandler extends AbstractHandler<University> {

    private CollegeRepository collegeRepository;

    @Autowired
    public UniversityHandler(UniversityRepository rep, CollegeRepository collegeRepository) {
        repository = rep;
        this.collegeRepository = collegeRepository;
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        University university = new University();
        university.setName(req.pathVariable("name"));
        return ServerResponse.ok().body(BodyInserters.fromObject(repository.save(university)));
    }

    public Mono<ServerResponse> students(ServerRequest req) {
        Optional<University> university = repository.findByName(req.pathVariable("name"));
        if (university.isPresent()) {
            return ServerResponse.ok().body(BodyInserters.fromObject(university.get().getStudents()));
        }
        return ServerResponse.notFound().build();
    }

    public Mono<ServerResponse> generate(ServerRequest req) {
        for (int i = 0; i < 50; i++) {
            University university = new University();
            university.setName("University" + i);
            university.setStudents(new HashSet<>());
            repository.save(university);
        }
        return get(req);
    }

    public Mono<ServerResponse> getStudents(ServerRequest req) {
        List<String> names = new ArrayList<>(repository.findAll().keySet());
        Random rdm = new Random();
        collegeRepository.findAll().values().forEach(college -> {
            Flux<Student> students = Flux.fromIterable(college.getStudents());
            students.subscribe(student -> {
                Optional<University> university = repository.findByName(names.get(rdm.nextInt(names.size())));
                university.ifPresent(it -> {
                    it.getStudents().add(student);
                    repository.save(it);
                });
            });
            Mono<College> collegeMono = Mono.just(college);
            collegeMono.subscribe(it -> {
                it.setStudents(new HashSet<>());
                collegeRepository.save(college);
            });
        });
        return get(req);
    }
}
