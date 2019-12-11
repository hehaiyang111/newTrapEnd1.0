package cn.huihongcloud.mapper;

import cn.huihongcloud.entity.Device_DeadTrees_maintanceEntity;
import cn.huihongcloud.entity.Device_Track_MaintanceEntity;
import cn.huihongcloud.entity.device.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Device_Track_MaintanceEntityMapper {

    List<Device_Track_MaintanceEntity> selectAll(String username,int num1,int num2);

    int countAll(String username,String startDate,String endDate,String colName,String searchText);

    int countAllByAdcode(String adcode,String startDate,String endDate,String colName,String searchText);


    int insert(Device_Track_MaintanceEntity record);

    int insertSelective(Device_Track_MaintanceEntity record);

    int countAllByArea(String username);

    List<Device_Track_MaintanceEntity> selectAllByArea(String adcode,int num1,int num2);

    List<Device_Track_MaintanceEntity> selectByDateAndColSearch(String username,String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2,String adcode);


    List<Device_Track_MaintanceEntity> selectByDateAndColSearchAdcode(String startDate,String endDate,String colName,String searchText,Integer num1,Integer num2,String adcode);


    List<Device_Track_MaintanceEntity> selectAllByAdcode(String adcode,int num1,int num2);

    List<Device> getDeviceByLocation(@Param("adcode") String adcode, @Param("town") String town,
                                     @Param("searchText") String searchText);

    List<Device_Track_MaintanceEntity> getDeviceByManager(@Param("manager") String manager);

    List<Device_Track_MaintanceEntity> getDeviceByAdcode(@Param("adcode") String adcode);



    List<Device> getDeviceByWorker(@Param("worker") String worker);

    int addMaintance(Device_Track_MaintanceEntity dataentity);

    Device_Track_MaintanceEntity selectById(String id);

    int updateRecordById(Device_Track_MaintanceEntity dataentity);

    int getMaxBatchId(String linename);

    int deleteById(String id);

    int updateRecordByFront(Device_Track_MaintanceEntity dataEntity);

    List<Device_Track_MaintanceEntity> selectByLineName(String linename);

    int updatePic(String linename,String colName,String text,String username);

    int updateMaintance(Device_Track_MaintanceEntity dataentity);




}