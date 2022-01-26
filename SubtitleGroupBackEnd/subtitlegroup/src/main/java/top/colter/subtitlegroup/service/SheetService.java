package top.colter.subtitlegroup.service;

import top.colter.subtitlegroup.entity.Sheet;

import java.util.List;

public interface SheetService {
    void saveSheet(Sheet sheet);
    void removeSheet(Long id);
    void updateSheet(Sheet sheet);

    List<Sheet> findSheetByPertain(String pertain);
}
