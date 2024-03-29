package com.goku.producer;

import com.alibaba.fastjson.JSONObject;
import com.goku.mapper.UserMapper;
import com.goku.util.DateUtils;
import com.goku.util.MysqlJDBCUtils;
import com.goku.util.ObjectUtils;
import com.goku.config.ConfigurationManager;
import com.goku.util.KafkaUtils;

import java.util.*;

public class MockUserData {

    private static String countSQL = "select count(1) as cnt from tbl_user where user_id = ?";

    private static String insertSQL = "insert into tbl_user(\n" +
            "    user_id,\n" +
            "    user_name,\n" +
            "    sex,\n" +
            "    account,\n" +
            "    nick_name,\n" +
            "    register_time,\n" +
            "    city_id,\n" +
            "    city_name,\n" +
            "    crowd_type \n" +
            ")value(\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?,\n" +
            "    ?\n" +
            ")";


    public static boolean cycleFlag = true;
    private static boolean testFlag = false;

    public static boolean isCycleFlag() {
        return cycleFlag;
    }

    public static void setCycleFlag(boolean cycleFlag) {
        MockUserData.cycleFlag = cycleFlag;
    }

    public static void mockUser(boolean orderedFlag, int sleepTime, int unOrderedNum) throws InterruptedException{

        Random random = new Random();

        Map<Integer, String> bookShopCityPool = new HashMap<>();
        bookShopCityPool.put(1, "北京市");
        bookShopCityPool.put(2, "上海市");
        bookShopCityPool.put(3, "广州市");

        Map<Integer,String> crowdTypePool = new HashMap<>();
        crowdTypePool.put(1,"学生");
        crowdTypePool.put(2,"教师");
        crowdTypePool.put(3,"白领");
        crowdTypePool.put(4,"科学工作者");
        crowdTypePool.put(5,"工人");

        List<UserMapper> users = UserMapper.users;

         int userId=0;
         String userName;
         int sex;
         String account;
         String nickName;
         String registerDateTime;
         int cityId;
         String cityName;
        String crowdType;


        List<UserMapper> cache = new ArrayList<>(unOrderedNum);
        int i = 0;
        while (cycleFlag) {

            userId = userId+1;
            userName = "User-" + userId;
            sex = random.nextInt(2)+1;
            account = "Account-" + String.valueOf(random.nextInt(999999999));
            nickName = "Nick-" + String.valueOf(random.nextInt(999999999));
            registerDateTime = DateUtils.getCurrentDatetime();
            cityId = random.nextInt(bookShopCityPool.size()) + 1;
            cityName = bookShopCityPool.get(cityId);
            crowdType = crowdTypePool.get(random.nextInt(crowdTypePool.size())+1);

            UserMapper user = new UserMapper(
             userId,
             userName,
             sex,
             account,
             nickName,
             registerDateTime,
             cityId,
             cityName,
             crowdType
            );
            users.add(user);

            if(i<unOrderedNum){
                cache.add(user);
                i++;
            }else {
                i=0;
                if(!orderedFlag){
                    Collections.shuffle(cache);
                }

                for(UserMapper entry :cache){
                    if(testFlag){
                        System.out.println(entry.toString());
                    }else {
                        JSONObject jsonObject = ObjectUtils.objInstanceToJsonObject(entry, UserMapper.class);

//                        将数据写入外部存储
                        if(!(MysqlJDBCUtils.getInstance().getCount(countSQL,new Object[]{entry.getUserId()}) > 0)) {
                            MysqlJDBCUtils.getInstance().executeUpdate(
                                    insertSQL,
                                    new Object[]{
                                            entry.getUserId(),
                                            entry.getUserName(),
                                            entry.getSex(),
                                            entry.getAccount(),
                                            entry.getNickName(),
                                            entry.getRegisterTime(),
                                            entry.getCityId(),
                                            entry.getCityName(),
                                            entry.getCrowdType()
                                    }
                            );
                        }
                        KafkaUtils.getInstance().send(ConfigurationManager.getString("user.topics"), jsonObject.toString());
                    }
                }

                cache.clear();
                cache.add(user);
                i++;
            }
            Thread.sleep(random.nextInt(sleepTime));
        }

        for(UserMapper entry :cache){
            if(testFlag){
                System.out.println(entry.toString());
            }else {
                JSONObject jsonObject = ObjectUtils.objInstanceToJsonObject(entry, UserMapper.class);

//                        将数据写入外部存储
                if(!(MysqlJDBCUtils.getInstance().getCount(countSQL,new Object[]{entry.getUserId()}) > 0)) {
                    MysqlJDBCUtils.getInstance().executeUpdate(
                            insertSQL,
                            new Object[]{
                                    entry.getUserId(),
                                    entry.getUserName(),
                                    entry.getSex(),
                                    entry.getAccount(),
                                    entry.getNickName(),
                                    entry.getRegisterTime(),
                                    entry.getCityId(),
                                    entry.getCityName(),
                                    entry.getCrowdType()
                            }
                    );
                }
                KafkaUtils.getInstance().send(ConfigurationManager.getString("user.topics"), jsonObject.toString());
            }
        }

    }

    public static void main(String[] args) throws Exception{
        mockUser(false,1000,3);
    }
}
