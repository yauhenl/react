package com.yauhenl.react.repository;

import com.yauhenl.react.domain.Model;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.of;

public abstract class AbstractRepository<T extends Model> {
    private Map<String, T> objects = new ConcurrentHashMap<>();

    public Map<String, T> findAll() {
        return objects;
    }

    public T save(T object) {
        objects.put(object.getName(), object);
        return object;
    }

    public Optional<T> findByName(String name) {
        return of(objects.get(name));
    }

    public void delete(String name) {
        objects.remove(name);
    }
}
