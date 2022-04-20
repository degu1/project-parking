package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.services.ServiceInterface;

import java.util.List;

@RestController
public abstract class CRUDController<T, U extends ServiceInterface<T>> {

    protected U service;

    @PostMapping
    public void create(@RequestBody T t) {
        service.create(t);
    }

    @PutMapping
    public void updateWithPUT(@RequestBody T t) {
        service.updateWithPUT(t);
    }

    @PatchMapping
    public void updateWithPATCH(@RequestBody T t) {
        service.updateWithPATCH(t);
    }

    @GetMapping
    public List<T> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Long id) {
        service.remove(id);
    }
}
