package top.colter.subtitlegroup;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.colter.subtitlegroup.dao.OrganizationDao;
import top.colter.subtitlegroup.dao.SheetDao;
import top.colter.subtitlegroup.dao.UserDao;
import top.colter.subtitlegroup.entity.*;
import top.colter.subtitlegroup.service.OrganizationService;

import java.io.File;
import java.util.*;

@SpringBootTest
class SubtitlegroupApplicationTests {

    @Autowired
    private SheetDao sheetDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private OrganizationService organizationService;

    private static final Log log = LogFactory.get();

    @Test
    void saveSheetTest() {
        Sheet sheet = new Sheet();
        sheet.setPertain("/vivid/bell");
        sheet.setCreator(3375582524L);
        sheet.setCutName(new TextLock("", false));
        sheet.setTitle(new TextLock("", false));
        sheet.setRemark(new TextLock("", false));
        sheetDao.saveSheet(sheet);
    }

    @Test
    void insertGroupTest(){
        Organization organization = new Organization();
        organization.setOrganization("ProPro");
        organization.setAvatar("");
        organization.setHomeLink("/propro");
        organization.setHomeText("ProPro首页");
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("恋乃夜舞","/lian","",2546314569L));
        groups.add(new Group("花云凛","/hua","",8974641232L));
        organization.setGroup(groups);
        organizationDao.saveOrganization(organization);
    }

    @Test
    void getGroupTest(){
        //log.info(ArrayUtil.toString(organizationDao.findAllOrganization()));
        //log.info(ArrayUtil.toString(organizationService.findAllGroupNumber()));
        log.info(ArrayUtil.toString(organizationService.findOrganization(List.of("/vivid/bell", "/propro/lian"))));
    }

    @Test
    void test(){
        Long qq = 3375582524L;
        String js = "{\"code\":0,\"msg\":\"\",\"data\":[{\"id\":1527296113,\"memberName\":\"(ᗜ‸ᗜ)\",\"specialTitle\":\"\",\"permission\":\"ADMINISTRATOR\",\"joinTimestamp\":1585646922,\"lastSpeakTimestamp\":1642151043,\"muteTimeRemaining\":0,\"group\":{\"id\":1030460255,\"name\":\"Dev\",\"permission\":\"MEMBER\"}},{\"id\":2854196306,\"memberName\":\"小冰\",\"specialTitle\":\"\",\"permission\":\"MEMBER\",\"joinTimestamp\":1589473099,\"lastSpeakTimestamp\":1589473274,\"muteTimeRemaining\":0,\"group\":{\"id\":1030460255,\"name\":\"Dev\",\"permission\":\"MEMBER\"}},{\"id\":2945271969,\"memberName\":\"Bot\",\"specialTitle\":\"\",\"permission\":\"ADMINISTRATOR\",\"joinTimestamp\":1604722269,\"lastSpeakTimestamp\":1608255576,\"muteTimeRemaining\":0,\"group\":{\"id\":1030460255,\"name\":\"Dev\",\"permission\":\"MEMBER\"}},{\"id\":3375582524,\"memberName\":\"Colter\",\"specialTitle\":\"\",\"permission\":\"OWNER\",\"joinTimestamp\":1585646921,\"lastSpeakTimestamp\":1641990347,\"muteTimeRemaining\":0,\"group\":{\"id\":1030460255,\"name\":\"Dev\",\"permission\":\"MEMBER\"}}]}";
        log.info(String.valueOf(js.contains("\"id\":"+qq)));
    }


    @Test
    void saveUserTest(){
        User user = new User();
        user.setQq(3375582524L);
        user.setName("Colter");

        Skill skill = new Skill();

        user.setPertain(Map.of("/vivid/bell",skill));

        userDao.saveUser(user);
    }


    @Test
    void readSheetTest() {
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("D:/Desktop/vvd工作表备份/test.xlsx"));
        List<List<Object>> readAll = reader.read();
        readAll.forEach(d -> {
            d.forEach(v -> {
                System.out.println(v.toString());
            });
        });
    }

    @Test
    void aliReadSheetTest() {
        String fileName = "D:/Desktop/vvd工作表备份/test.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener())
                .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();

    }


    class DemoDataListener implements ReadListener<DemoData> {

        /**
         * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
         */
        private static final int BATCH_COUNT = 100;
        /**
         * 缓存的数据
         */
        private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        /**
         * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
         */


        public DemoDataListener() {
            // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数

        }


        /**
         * 这个每一条数据解析都会来调用
         *
         * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
         * @param context
         */
        @Override
        public void invoke(DemoData data, AnalysisContext context) {
            System.out.println("解析到一条数据:{"+ JSON.toJSONString(data)+"}");
            cachedDataList.add(data);
            // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
            if (cachedDataList.size() >= BATCH_COUNT) {
                saveData();
                // 存储完成清理 list
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }

        @Override
        public void extra(CellExtra extra, AnalysisContext context) {

            log.info("读取到了一条额外信息:{}", JSON.toJSONString(extra));
            switch (extra.getType()) {
                case MERGE:
                    log.info(
                            "额外信息是超链接,而且覆盖了一个区间,在firstRowIndex:{},firstColumnIndex;{},lastRowIndex:{},lastColumnIndex:{}",
                            extra.getFirstRowIndex(), extra.getFirstColumnIndex(), extra.getLastRowIndex(),
                            extra.getLastColumnIndex());
                    break;
                default:
            }
        }

        /**
         * 所有数据解析完成了 都会来调用
         *
         * @param context
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            // 这里也要保存数据，确保最后遗留的数据也存储到数据库
            saveData();
            System.out.println("所有数据解析完成！");
        }

        /**
         * 加上存储数据库
         */
        private void saveData() {
            //System.out.println("{}条数据，开始存储数据库！", cachedDataList.size());
            //demoDAO.save(cachedDataList);
            System.out.println("存储数据库成功！");
        }
    }

}
