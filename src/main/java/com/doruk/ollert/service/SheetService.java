package com.doruk.ollert.service;

import com.doruk.ollert.dto.SheetAccessDTO;
import com.doruk.ollert.dto.SheetViewDTO;
import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.User;
import com.doruk.ollert.repository.SheetRepository;
import com.doruk.ollert.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SheetService {
    SheetRepository sheetRepository;
    UserRepository userRepository;

    public SheetService(SheetRepository sheetRepository, UserRepository userRepository) {
        this.sheetRepository = sheetRepository;
        this.userRepository = userRepository;
    }

    public List<SheetViewDTO> findAllByUsername(String username) {
        User user = userRepository.findByUsername(username);
        List<Sheet> sheets = sheetRepository.findAllByUsersContaining(user);
        List<SheetViewDTO> sheetViewDTO = new ArrayList<>();
        for (Sheet s : sheets) {
            SheetViewDTO dto = new SheetViewDTO();
            dto.name = s.getName();
            dto.id = s.getId();
            sheetViewDTO.add(dto);
        }
        return sheetViewDTO;
    }

    public List<Sheet> findAll() {
        return (List<Sheet>) sheetRepository.findAll();
    }

    public Sheet findById(Long sheet_id) throws Exception {
        return sheetRepository.findById(sheet_id)
                .orElseThrow(() -> new Exception("Sheet " + sheet_id + "not found"));
    }

    public void changeSheetName(Long sheet_id, String name) throws Exception {
        Sheet sheet = sheetRepository.findById(sheet_id)
                .orElseThrow(() -> new Exception("Sheet " + sheet_id + "not found"));
        sheet.setName(name);
        sheetRepository.save(sheet);

    }

    public void deleteById(Long sheet_id) throws Exception {
        Sheet sheet = sheetRepository.findById(sheet_id)
                .orElseThrow(() -> new Exception("Sheet " + sheet_id + "not found"));
        sheetRepository.delete(sheet);
    }

    public void newSheet(String name, String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new Exception("User " + username + "not found");
        }

        Sheet sheet = new Sheet();
        sheet.setName(name);
        sheet.getUsers().add(user);
        sheetRepository.save(sheet);
    }

    public Boolean checkAuth(String username, Long sheet_id) {
        Sheet sheet = sheetRepository.findById(sheet_id).orElse(null);
        User user = userRepository.findByUsername(username);
        if (user == null || sheet == null) {
            return false;
        }

        return sheetRepository.findAllByUsersContaining(user).contains(sheet);
    }

    public SheetAccessDTO sheetAccess(Long id) throws Exception {
        Sheet sheet = sheetRepository.findById(id)
                .orElseThrow(() -> new Exception("Sheet " + id + "not found"));
        List<User> allusers = (List<User>) userRepository.findAll();
        List<User> permittedUsers = userRepository.findAllBySheetsContaining(sheet);
        allusers = allusers.stream().filter(user -> {
                    return !permittedUsers.contains(user);
                })
                .collect(Collectors.toList());
        SheetAccessDTO dto = new SheetAccessDTO();
        dto.permitted = permittedUsers.stream().map(User::getUsername).collect(Collectors.toList());
        dto.notPermitted = allusers.stream().map(User::getUsername).collect(Collectors.toList());
        return dto;
    }

    public void sheetAccessAdd(Long sheet_id, String username) throws Exception {
        Sheet sheet = sheetRepository.findById(sheet_id)
                .orElseThrow(() -> new Exception("Sheet " + sheet_id + "not found"));
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new Exception("User " + username + "not found");
        }
        sheet.getUsers().add(user);
        sheetRepository.save(sheet);
    }

    public void sheetAccessRemove(Long sheet_id, String username) throws Exception {
        Sheet sheet = sheetRepository.findById(sheet_id)
                .orElseThrow(() -> new Exception("Sheet " + sheet_id + "not found"));
        User user = userRepository.findByUsername(username);
        try {
            sheet.getUsers().remove(user);
            sheetRepository.save(sheet);
        } catch (Exception e) {
            throw new Exception("User " + username + "not found in sheet" + sheet_id);
        }


    }
}
