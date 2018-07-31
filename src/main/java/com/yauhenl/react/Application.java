package com.yauhenl.react;

import com.yauhenl.react.service.CollegeHandler;
import com.yauhenl.react.service.UniversityHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(CollegeHandler collegeHandler, UniversityHandler universityHandler) {
        return route(POST("/college/create/{name}"), collegeHandler::create)
                .andRoute(GET("/college"), collegeHandler::get)
                .andRoute(GET("/college/generate"), collegeHandler::generate)
                .andRoute(GET("/college/{name}"), collegeHandler::find)
                .andRoute(GET("/college/{name}/students"), collegeHandler::students)
                .andRoute(POST("/university/create/{name}"), universityHandler::create)
                .andRoute(GET("/university"), universityHandler::get)
                .andRoute(GET("/university/generate"), universityHandler::generate)
                .andRoute(GET("/university/getStudents"), universityHandler::getStudents)
                .andRoute(GET("/university/{name}"), universityHandler::find)
                .andRoute(GET("/university/{name}/students"), universityHandler::find);
    }
}
