package top.colter.subtitlegroup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.colter.subtitlegroup.dao.SheetDao;
import top.colter.subtitlegroup.entity.Sheet;
import top.colter.subtitlegroup.service.SheetService;

import java.util.List;

@Service
public class SheetServiceImpl implements SheetService {

    private final SheetDao sheetDao;

    public SheetServiceImpl(SheetDao sheetDao) {
        this.sheetDao = sheetDao;
    }

    @Override
    public void saveSheet(Sheet sheet) {
        sheetDao.saveSheet(sheet);
    }

    @Override
    public void removeSheet(Long id) {
        sheetDao.removeSheet(id);
    }

    @Override
    public void updateSheet(Sheet sheet) {
        sheetDao.updateSheet(sheet);
    }

    @Override
    public List<Sheet> findSheetByPertain(String pertain) {
        return sheetDao.findSheetByPertain(pertain);
    }
}
