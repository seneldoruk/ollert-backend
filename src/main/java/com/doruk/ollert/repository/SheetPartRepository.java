package com.doruk.ollert.repository;

import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.SheetPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SheetPartRepository extends CrudRepository<SheetPart, Long> {
    SheetPart findByIdAndSheet(Long id, Sheet sheet);
    List<SheetPart> findAllBySheet(Sheet sheet);
    SheetPart findByIndexAndSheet(int index, Sheet sheet);
}
