package com.doruk.ollert.service;

import com.doruk.ollert.dto.ChangePartPositionDTO;
import com.doruk.ollert.dto.ChangeTaskPositionDTO;
import com.doruk.ollert.entity.Sheet;
import com.doruk.ollert.entity.SheetPart;
import com.doruk.ollert.entity.User;
import com.doruk.ollert.repository.SheetPartRepository;
import com.doruk.ollert.repository.SheetRepository;
import com.doruk.ollert.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetPartService {

    final SheetPartRepository sheetPartRepository;
    final SheetRepository sheetRepository;
    final UserRepository userRepository;

    public SheetPartService(SheetPartRepository sheetPartRepository, SheetRepository sheetRepository, UserRepository userRepository) {
        this.sheetPartRepository = sheetPartRepository;
        this.sheetRepository = sheetRepository;
        this.userRepository = userRepository;
    }

    public void changeTaskPosition(ChangeTaskPositionDTO dto, Long id) {
        Sheet sheet = sheetRepository.findById(id).orElse(null);
        SheetPart from = sheetPartRepository.findByIdAndSheet(dto.fromPartID, sheet);
        SheetPart to = sheetPartRepository.findByIdAndSheet(dto.toPartID, sheet);
        String str = from.getTasks().get(dto.taskCurrentIndex);
        from.getTasks().remove(str);
        to.getTasks().add(dto.taskNextIndex, str);
        sheetPartRepository.save(from);
        sheetPartRepository.save(to);

    }

    public void changePartPosition(ChangePartPositionDTO dto, Long id) throws Exception {
        Sheet sheet = sheetRepository.findById(id)
                .orElseThrow(()->new Exception("Sheet " + id + "not found"));
        List<SheetPart> list = sheetPartRepository.findAllBySheet(sheet);
        SheetPart part = sheetPartRepository.findByIndexAndSheet(dto.currentIndex, sheet);
        if (part == null) {
            throw new Exception("Part of index" + dto.currentIndex + "not found in sheet" + id);
        }
        list.forEach(
                sheetPart -> {
                    if (sheetPart.getIndex() >= dto.wantedIndex) {
                        sheetPart.setIndex(sheetPart.getIndex() + 1);
                        sheetPartRepository.save(sheetPart);
                    }
                }
        );
        part.setIndex(dto.wantedIndex);
        sheetPartRepository.save(part);
    }

    public void newSheetPart(Long sheetid) throws Exception {
        SheetPart sheetPart = new SheetPart();
        Sheet sheet = sheetRepository.findById(sheetid)
                .orElseThrow(() -> new Exception("Sheet " + sheetid + "not found"));
        sheetPart.setName("New Column");
        sheetPart.setIndex(sheet.getParts().size());
        sheetPart.setSheet(sheet);
        sheetPartRepository.save(sheetPart);
    }

    public void deleteSheetPart(Long sheetPartID) throws Exception {
        sheetPartRepository.delete(sheetPartRepository.findById(sheetPartID)
                .orElseThrow(() -> new Exception("SheetPart " + sheetPartID + "not found")));

    }

    public void changeSheetPartName(Long sheetpart_id, String newName) throws Exception {
        SheetPart sheetPart = sheetPartRepository.findById(sheetpart_id)
                .orElseThrow(() -> new Exception("SheetPart " + sheetpart_id + "not found"));
        sheetPart.setName(newName);
        sheetPartRepository.save(sheetPart);

    }

    public void deleteTask(Long sheetpart_id, String task) throws Exception {
        SheetPart sheetPart = sheetPartRepository.findById(sheetpart_id)
                .orElseThrow(() -> new Exception("SheetPart " + sheetpart_id + "not found"));
        sheetPart.getTasks().remove(task);
        sheetPartRepository.save(sheetPart);
    }

    public void addTask(Long sheetpart_id, String task) throws Exception {
        SheetPart sheetPart = sheetPartRepository.findById(sheetpart_id)
                .orElseThrow(() -> new Exception("SheetPart " + sheetpart_id + "not found"));
        sheetPart.getTasks().add(task);
        sheetPartRepository.save(sheetPart);
    }

    public Boolean checkAuth(String username, Long sheetpart_id) {
        SheetPart sheetPart = sheetPartRepository.findById(sheetpart_id).orElse(null);
        if (sheetPart == null) {
            return false;
        }
        Sheet sheet = sheetPart.getSheet();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return sheetRepository.findAllByUsersContaining(user).contains(sheet);
    }
}
