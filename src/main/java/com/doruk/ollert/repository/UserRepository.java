package com.doruk.ollert.repository;

import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllBySheetsContaining(Sheet sheet);
}
