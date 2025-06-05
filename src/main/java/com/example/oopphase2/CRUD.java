package com.example.oopphase2;
import java.util.ArrayList;

public interface CRUD<T> {
    boolean create(T item);
    ArrayList<T> read();
    boolean update(int id, T updatedItem);
    boolean delete(int id);
}
