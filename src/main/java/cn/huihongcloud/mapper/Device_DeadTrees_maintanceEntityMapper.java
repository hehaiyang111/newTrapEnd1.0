package cn.huihongcloud.mapper;

import cn.huihongcloud.entity.Device_DeadTrees_maintanceEntity;
import cn.huihongcloud.entity.Device_NaturalEnemies_maintanceEntity;
import cn.huihongcloud.entity.device.Device;
import cn.huihongcloud.entity.summary.DeadTreesSummary;
import cn.huihongcloud.entity.summary.NaturalSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public interface Device_DeadTrees_maintanceEntityMapper {

    int countAll(String username,String startDate,String endDate,String colName,String searchText);

    List<Device_DeadTrees_maintanceEntity> selectAll(String username,int num1,int num2);

    List<DeadTreesSummary> queryDeviceSummaryByArea(String adcode, String startDate, String endDate);

    List<DeadTreesSummary> queryDeviceSummaryByManager(String adcode, String startDate, String endDate);

    List<Device_DeadTrees_maintanceEntity> getMaintenanceDataByManagerArea(@Param("adcode") String adcode, @Param("town") String town,
                                                                                @Param("condition") String condition, @Param("date") String date, @Param("endDate") String endDate, @Param("manager") String manager);

    int reportData(@Param("id") Integer id);


    Map<String, Long> queryDeviceSum(String adcode, String startDate, String endDate);
    Map<String, Long> queryDeviceSum4(String adcode,String startDate,String endDate);


    int insert(Device_DeadTrees_maintanceEntity record);

    int insertSelective(Device_DeadTrees_maintanceEntity record);

    int countAllByArea(String adcode,String startDate,String endDate,String colName,String searchText);

    List<Device_DeadTrees_maintanceEntity> selectAllByArea(String adcode,int num1,int num2);

    List<Device_DeadTrees_maintanceEntity> selectByDateAndColSearch(String customProject,String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2,String adcode);


    List<Device_DeadTrees_maintanceEntity> selectAllByDateAndColSearch(String customProject,String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2,String adcode);


    int selectWorkDayByDateAndColSearch(String customProject,String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2,String adcode);

    int selectWorkDayByDateAndColSearchAndAdcode(String adcode,String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2);

    List<Device_DeadTrees_maintanceEntity> selectByDateAndColSearchAdcode(String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2,String adcode);


    List<Device_DeadTrees_maintanceEntity> selectAllByAdcode(String adcode,int num1,int num2);

    List<Device> getDeviceByLocation(@Param("adcode") String adcode, @Param("town") String town,
                                     @Param("searchText") String searchText);

    List<Device> getDeviceByManager(@Param("manager") String manager);

    List<Device> getDeviceByWorker(@Param("worker") String worker);

    int addMaintance(Device_DeadTrees_maintanceEntity dataEnity);

    Device_DeadTrees_maintanceEntity selectById(String id);

    int updateRecordById(Device_DeadTrees_maintanceEntity dataEnity);

    List<Device_DeadTrees_maintanceEntity> getMaxBatch(String deviceId);

    int deleteById(String id);

    int updateRecordByFront(Device_DeadTrees_maintanceEntity dataEntity);

    List<Device_DeadTrees_maintanceEntity> getMaintenanceDataByAdcodeAndTownArea(@Param("adcode") String adcode, @Param("town") String town,
                                                                                      @Param("condition") String condition, @Param("date") String date, @Param("endDate") String endDate, @Param("reported") Boolean reported);
    List<Device_DeadTrees_maintanceEntity> getMaintenanceDataByDeviceId(String myusername, String deviceId, @Param("startDate")String startDate, @Param("endDate")String endDate, @Param("reported")Boolean reported);


    List<Device_DeadTrees_maintanceEntity> getMaintenanceDataByAdcodeAndTown1(@Param("adcode") String adcode, @Param("town") String town,
                                                                                   @Param("condition") String condition,@Param("batch") String batch,@Param("searchtown") String searchtown, @Param("date") String date, @Param("endDate") String endDate, @Param("reported") Boolean reported);
    List<Device_DeadTrees_maintanceEntity> getMaintenanceDataByManager1(@Param("adcode") String adcode, @Param("town") String town,
                                                                             @Param("condition") String condition,@Param("batch")String batch,@Param("searchtown") String searchtown, @Param("date") String date, @Param("endDate") String endDate, @Param("manager") String manager);




}