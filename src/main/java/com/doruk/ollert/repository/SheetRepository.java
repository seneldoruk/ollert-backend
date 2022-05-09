package com.doruk.ollert.repository;

import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SheetRepository extends CrudRepository<Sheet, Long> {
    List<Sheet> findAllByUsersContaining(User user);
}
