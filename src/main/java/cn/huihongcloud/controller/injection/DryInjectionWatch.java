package cn.huihongcloud.controller.injection;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.huihongcloud.entity.Device_Injection_maintanceEntity;
import cn.huihongcloud.entity.Device_NaturalEnemies_maintanceEntity;
import cn.huihongcloud.entity.common.Result;
import cn.huihongcloud.entity.device.Device;
import cn.huihongcloud.entity.page.PageWrapper;
import cn.huihongcloud.entity.summary.InjectionSummary;
import cn.huihongcloud.entity.user.User;
import cn.huihongcloud.mapper.Device_Injection_maintanceEntityMapper;
import cn.huihongcloud.service.DryInjectionService;
import cn.huihongcloud.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/dryWatch")
public class DryInjectionWatch {
    @Autowired
    DryInjectionService dryInjectionService;
    @Autowired
    UserService userService;
    @Autowired
    private Device_Injection_maintanceEntityMapper deviceInjectionMaintanceEntityMapper;
    JSONObject jsonObject = new JSONObject();


    @RequestMapping("/dataDetail")
    public Object getDataDetail(@RequestAttribute("username") String username,
                             @RequestParam int page,
                             @RequestParam int limit,
                             @RequestParam Integer optionIndex,
                             @RequestParam(required = false) String searchText,
                             @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {

        User user = userService.getUserByUserName(username);
        Page<Object> pageObject = PageHelper.startPage(page, limit);
        System.out.println("收到用户名:");

        System.out.println(username);


        if (!Objects.equals(startDate, "")) {
            startDate = startDate + " 00:00:00";
        }
        if (!Objects.equals(endDate, "")) {
            endDate = endDate + " 23:59:59";
        }
        List<Device_Injection_maintanceEntity> deviceInjectionMaintanceEntities = null;

        if(user.getRole()==4){
            deviceInjectionMaintanceEntities = dryInjectionService.getDryInjectionDetail(user.getParent(), optionIndex, searchText, startDate, endDate);
        }else if(user.getRole()<=3){
            deviceInjectionMaintanceEntities = deviceInjectionMaintanceEntityMapper.selectByConditionsAdcode(user.getAdcode(), optionIndex, searchText, startDate, endDate);
        }

        System.out.println("+++");
        for (Device_Injection_maintanceEntity d:
             deviceInjectionMaintanceEntities) {
            System.out.println("---");

            System.out.println(d.getDeviceId());

        }

        PageWrapper pageWrapper = new PageWrapper();
        pageWrapper.setTotalPage(pageObject.getPages());
        pageWrapper.setCurrentPage(page);
        pageWrapper.setTotalNum(pageObject.getTotal());
        pageWrapper.setData(deviceInjectionMaintanceEntities);
        return Result.ok(pageWrapper);
    }

    @RequestMapping("/area")
    public Object getDeviceSummaryByArea(String adcode, int page, int limit,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate) {
        Page<Object> pageObject = PageHelper.startPage(page, limit);
        if (startDate != "" && startDate != null) {
            startDate = startDate + " 00:00:00";
        }
        if (endDate != "" && endDate != null) {
            endDate = endDate + " 23:59:59";
        }
        List<InjectionSummary> summaryEntities = dryInjectionService.queryDeviceSummaryByArea(adcode, startDate, endDate);
        PageWrapper pageWrapper = new PageWrapper();
        pageWrapper.setTotalPage(pageObject.getPages());
        pageWrapper.setCurrentPage(page);
        pageWrapper.setTotalNum(pageObject.getTotal());
        pageWrapper.setData(summaryEntities);
        return Result.ok(pageWrapper);
    }

    @RequestMapping("/getAreaMaintanceDetail")
    public Object getMaintenanceData2(@RequestAttribute("username") String username, int page, int limit,
                                      @RequestParam(required = false) String condition,
                                      @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        System.out.println(condition);
//        if(startDate.equals("null")){
//            startDate=null;
//        }
//        if(endDate.equals("null")){
//            endDate=null;
//        }
        if (startDate != "" && startDate != null) {
            startDate = startDate + " 00:00:00";
        }
        if (endDate != "" && endDate != null) {
            endDate = endDate + " 23:59:59";
        }
        User user = userService.getUserByUserName(username);
        Page<Object> pageObject = PageHelper.startPage(page, limit);
        List<Device_Injection_maintanceEntity> maintenanceData = dryInjectionService.getAreaMaintanceDetail(user, condition, startDate, endDate);
        PageWrapper pageWrapper = new PageWrapper();
        pageWrapper.setData(maintenanceData);
        pageWrapper.setCurrentPage(page);
        pageWrapper.setTotalNum(pageObject.getTotal());
        pageWrapper.setTotalPage(pageObject.getPages());
        return pageWrapper;
    }

    @RequestMapping("/maintenance/byDeviceId")
    public Object getMaintenanceDataByDeviceId(@RequestAttribute("username") String username,
                                               @RequestParam String deviceId,
                                               @RequestParam(required = false) String myusername,
                                               @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {


//        if(startDate.equals("null")){
//            startDate=null;
//        }
//        if(endDate.equals("null")){
//            endDate=null;
//        }
        if (startDate != "" && startDate != null) {
            startDate = startDate + " 00:00:00";
        }
        if (endDate != "" && endDate != null) {
            endDate = endDate + " 23:59:59";
        }
        User user = userService.getUserByUserName(username);
        Object maintenanceData = dryInjectionService.getMaintenanceDataByDeviceId(user, myusername, deviceId, startDate, endDate);
        System.out.println(maintenanceData);

        PageWrapper pageWrapper = new PageWrapper();
        pageWrapper.setData(maintenanceData);

        return pageWrapper;
    }


    @GetMapping("/maintenance1")
    public Object getMaintenanceData1(@RequestAttribute("username") String username, int page, int limit,
                                      @RequestParam(required = false) String condition,
                                      @RequestParam(required = false) String batch, @RequestParam(required = false) String town,
                                      @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        //System.out.println(startDate+"cc");
//        if(startDate.equals("null")){
//            startDate=null;
//        }
//        if(endDate.equals("null")){
//            endDate=null;
//        }
        if (startDate != "" && startDate != null) {
            startDate = startDate + " 00:00:00";
            System.out.println(startDate + "dd");
        }
        if (endDate != "" && endDate != null) {
            endDate = endDate + " 23:59:59";
        }
        User user = userService.getUserByUserName(username);
        Page<Object> pageObject = PageHelper.startPage(page, limit);

        List<Device_Injection_maintanceEntity> maintenanceData = dryInjectionService.getMaintenanceData1(user, condition, startDate, endDate, batch, town);
        PageWrapper pageWrapper = new PageWrapper();
        pageWrapper.setData(maintenanceData);
        pageWrapper.setCurrentPage(page);
        pageWrapper.setTotalNum(pageObject.getTotal());
        pageWrapper.setTotalPage(pageObject.getPages());
        return pageWrapper;
    }

    @PostMapping("/maintenance/report")
    public Object reportMaintenanceData(@RequestBody Map<String, Object> data) {
        System.out.println(data.size());
        List<Integer> list = (List<Integer>) data.get("list");
        dryInjectionService.report(list);
        return Result.ok();
    }

    @RequestMapping(value = "device_list", method = RequestMethod.GET)
    @ApiOperation("获取设备列表")
    public PageWrapper getDevices(@RequestAttribute("username") String username, @RequestParam("page") int page,
                                  @RequestParam("limit") int limit,
                                  @RequestParam(value = "searchText", required = false) String searchText,
                                  @RequestParam(value = "workerName", required = false) String workerName) {
        System.out.println(workerName);
        User user = userService.getUserByUserName(username);
        List<Device> list = null;
        Page<Object> pages = null;
        PageWrapper pageWrapper = new PageWrapper();
        /*
        if (user.getRole() > 1 && user.getRole() != 3) {
            // 工人查询所管理的设备
            pages = PageHelper.startPage(page, limit);
            list = deviceService.getDeviceByMap(username);
        } else if (workerName == null){
            // 管理员未传递指定工人 查询其下属地区所有诱捕器信息
            pages = PageHelper.startPage(page, limit);
            list = deviceService.getDeviceByLocation(user.getAdcode(), user.getTown(), searchText.trim());
        } else {
            // 管理员传递工人信息
            // todo 限制跨区域查询

            list = deviceService.getDeviceByMap(workerName);
        }
        */
        /*
        县级用户看到所有下属的诱捕器
        管理员看到关联的
        工人看到关联的
         */
        pages = PageHelper.startPage(page, limit);
        if (user.getRole() == 1) {
            list = dryInjectionService.getDeviceByLocation(user.getAdcode(), null, null);
        }
        if (user.getRole() == 2) {
            list = dryInjectionService.getDeviceByLocation(user.getAdcode(), null, null);
        }
        if (user.getRole() == 3) {
            list = dryInjectionService.getDeviceByLocation(user.getAdcode(), null, null);
        }

        if (user.getRole() == 4) {
            list = dryInjectionService.getDeviceByManager(username);
        }

        if (user.getRole() == 5) {
            list = dryInjectionService.getDeviceByWorker(username);
        }
        pageWrapper.setData(list);
        pageWrapper.setTotalPage(pages.getPages());
        pageWrapper.setCurrentPage(page);
        pageWrapper.setTotalNum(pages.getTotal());
        return pageWrapper;
    }

    @RequestMapping("/searchDetail")
    public JSONObject searchDetail(@RequestAttribute("username") String username, @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate, @RequestParam(required = false) String optionIndex,
                                   @RequestParam(required = false) String searchText){
        JSONObject jsonObject = new JSONObject();
        User user = userService.getUserByUserName(username);
        System.out.println(user.getUsername());
        int optVal = 0;

        if(optionIndex!=null && optionIndex!=""){
            optVal = Integer.parseInt(optionIndex);

        }

        String dateString = "";
        System.out.println(endDate);

        if(endDate!=null && endDate!="") {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date currentTime_2 = formatter.parse(endDate, pos);

            currentTime_2.setTime(currentTime_2.getTime() + 24 * 3600 * 1000);

            System.out.println(currentTime_2.getDate());

            dateString = formatter.format(currentTime_2);

            System.out.println(dateString);
        }



        jsonObject.put("Res",true);

        System.out.println(optVal);
        System.out.println(searchText);

        if(user.getRole()==4){
            jsonObject.put("Data",deviceInjectionMaintanceEntityMapper.selectByConditions(user.getParent(),optVal,searchText,startDate,dateString));
        }



        jsonObject.put("total",deviceInjectionMaintanceEntityMapper.CountAll(username));
        jsonObject.put("current",1);
        System.out.println(jsonObject);

        return jsonObject;
    }

    @RequestMapping("/deleteRecord")
    public Object deleteRecord(@RequestParam String id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error",false);

        deviceInjectionMaintanceEntityMapper.deleteRecord(Long.parseLong(id));
        return jsonObject;
    }


    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,
                            String token,
                            @RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            @RequestParam(required = false) String colName,
                            @RequestParam(required = false) String searchText,
                            @RequestParam String username,
                            @RequestParam String adcode
    ) throws IOException {
        response.setContentType("application/excel");
        response.setHeader("Content-disposition",
                "attachment; filename=" +  "export.xls");

        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(colName);
        System.out.println(searchText);
        if(colName.equals("1")){
            colName = "serial";
        }
        if(colName.equals("2")){
            colName = "CustomTown";
        }
        if(colName.equals("3")){
            colName = "batch";
        }
        if(colName.equals("4")){
            colName = "Worker";
        }

        List<Device_Injection_maintanceEntity> deviceNaturalEnemiesMaintanceEntities  = deviceInjectionMaintanceEntityMapper.selectByDateAndColSearch(username,startDate,endDate,colName,searchText,adcode);
//        for (Device_NaturalEnemies_maintanceEntity d:
//             deviceNaturalEnemiesMaintanceEntities) {
//            System.out.println(d.getArea());
//
//        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("注干剂监测", "注干剂监测"), Device_Injection_maintanceEntity.class, deviceNaturalEnemiesMaintanceEntities);
        workbook.write(response.getOutputStream());

    }

    //这里还没写完
    @RequestMapping("/importExcel")
    public Object importExcel(String token,@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        importParams.setHeadRows(1);
        List<Device_Injection_maintanceEntity> deviceMaintenanceList = ExcelImportUtil
                .importExcel(multipartFile.getInputStream(), Device_Injection_maintanceEntity.class, importParams);
        for (Device_Injection_maintanceEntity d:
                deviceMaintenanceList) {
            System.out.println("natural");
            System.out.println(d.getId());
            Device_Injection_maintanceEntity tmp =  deviceInjectionMaintanceEntityMapper.selectById2(BigInteger.valueOf(d.getId()));
            if(tmp!=null){
                deviceInjectionMaintanceEntityMapper.updateRecordById(d);
            }else {
                deviceInjectionMaintanceEntityMapper.insert(d);
            }
        }
        return "OK";
    }

    @RequestMapping("/updateRec")
    public Object updateRec(@RequestBody Device_Injection_maintanceEntity d){
        deviceInjectionMaintanceEntityMapper.updateRecordByFront(d);
        return "OK";

    }







}
