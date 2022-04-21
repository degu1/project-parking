package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CRUDController<T> {

    @PostMapping
    void create(@RequestBody T t);

    @PutMapping
    void updateWithPUT(@RequestBody T t);

    @PatchMapping
    void updateWithPATCH(@RequestBody T t);

    @GetMapping
    List<T> getAll();

    @GetMapping("/{id}")
    T getById(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    void remove(@PathVariable("id") Long id);

}
