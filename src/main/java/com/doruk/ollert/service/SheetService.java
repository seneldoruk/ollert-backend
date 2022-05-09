package com.doruk.ollert.service;

import com.doruk.ollert.dto.SheetViewDTO;
import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.User;
import com.doruk.ollert.repository.SheetRepository;
import com.doruk.ollert.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SheetService {
    @Autowired
    SheetRepository sheetRepository;

    @Autowired
    UserRepository userRepository;

    public List<SheetViewDTO> findAllByUsername(String username){
        User user = userRepository.findByUsername(username);
        List<Sheet> sheets = sheetRepository.findAllByUsersContaining(user);
        List<SheetViewDTO> sheetViewDTO = new ArrayList<>();
        for(Sheet s: sheets){
            SheetViewDTO dto = new SheetViewDTO();
            dto.name = s.getName();
            dto.id = s.getId();
            sheetViewDTO.add(dto);
        }
        return sheetViewDTO;
    }
    public List<Sheet> findAll(){
        return  (List<Sheet>) sheetRepository.findAll();
    }

    public Sheet findById(Long table_id) {
        return sheetRepository.findById(table_id).orElse(null);
    }

    public void changeSheetName(Long table_id, String name) {
        Sheet sheet = sheetRepository.findById(table_id).orElse(null);
        sheet.setName(name);
        sheetRepository.save(sheet);

    }

    public void deleteById(Long table_id) {
        Sheet sheet = sheetRepository.findById(table_id).orElse(null);
        sheetRepository.delete(sheet);
    }

    public void newSheet(String name) {
        User user = userRepository.findById(1L).orElse(null);
        Sheet sheet = new Sheet();
        sheet.setName(name);
        sheet.getUsers().add(user);
        sheetRepository.save(sheet);
    }

    public Boolean checkAuth(String username, Long sheet_id) {
        Sheet sheet = sheetRepository.findById(sheet_id).orElse(null);
        User user = userRepository.findByUsername(username);
        return sheetRepository.findAllByUsersContaining(user).contains(sheet);
    }
}
