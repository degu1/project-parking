package se.iths.parking_lot.services;


import java.util.List;

public interface ServiceInterface<T> {
    void create(T t);

    void updateWithPUT(T t);

    void updateWithPATCH(T t);

    List<T> getAll();

    T getById(Long id);

    void remove(Long id);
}
