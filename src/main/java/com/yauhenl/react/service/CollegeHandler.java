package com.yauhenl.react.service;

import com.yauhenl.react.domain.College;
import com.yauhenl.react.domain.Student;
import com.yauhenl.react.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;

@Component
public class CollegeHandler extends AbstractHandler<College> {

    @Autowired
    public CollegeHandler(CollegeRepository rep) {
        repository = rep;
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        College college = new College();
        college.setName(req.pathVariable("name"));
        return ServerResponse.ok().body(BodyInserters.fromObject(repository.save(college)));
    }

    public Mono<ServerResponse> students(ServerRequest req) {
        Optional<College> college = repository.findByName(req.pathVariable("name"));
        if (college.isPresent()) {
            return ServerResponse.ok().body(BodyInserters.fromObject(college.get().getStudents()));
        }
        return ServerResponse.notFound().build();
    }

    public Mono<ServerResponse> generate(ServerRequest req) {
        for (int i = 0; i < 50; i++) {
            College college = new College();
            college.setName("College" + i);
            college.setStudents(new HashSet<>());
            for (int j = 0; j < 50; j++) {
                Student student = new Student();
                student.setName(college.getName() + " Student" + j);
                student.setAge(j);
                college.getStudents().add(student);
            }
            repository.save(college);
        }
        return get(req);
    }
}
