package com.xnf.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 获取昨天开始时间和结束时间
     * @return
     */
    public static Map getYesterdayRange() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition=new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        condition.put("endDate",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        condition.put("startDate",df.format(calendar.getTime()));
        return condition;
    }

    /**
     * 获得近一周的开始时间和结束时间
     * @return
     */
    public static Map getDaySevenRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition=new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-168);
        condition.put("startDate",df.format(calendar.getTime()));
        return condition;
    }

    /**
     * 获得近一月的开始时间和结束时间
     * @return
     */
    public static Map getDayTRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition=new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-720);
        condition.put("startDate",df.format(calendar.getTime()));
        return condition;
    }

    /**
     * 获得近一年的开始时间和结束时间
     * @return
     */
    public static Map getYearTRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition= new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,24);
        condition.put("endDate",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-8640);
        condition.put("startDate",df.format(calendar.getTime()));
        return condition;
    }

   public static Map getTime(String type){
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       Calendar c = Calendar.getInstance();

       Map<String,String> map = new HashMap<>();
       map.put("endTime",format.format(new Date()));
       System.out.println("现在时间"+format.format(new Date()));
        //近一周
        if("01".equals(type)){
            //过去七天
            c.setTime(new Date());
            c.add(Calendar.DATE, - 7);
            Date d = c.getTime();
            String day = format.format(d);
            System.out.println("过去七天："+day);
            map.put("startTime",day);
        }
        //近一个月
        if("02".equals(type)){
            //过去一月
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            Date m = c.getTime();
            String mon = format.format(m);
            System.out.println("过去一个月："+mon);
            map.put("startTime",mon);
        }
        return  map;
   }

    //-----------test 程序入口---------------
    public static void main(String[] args) {
        Map map1 = getTime("01");
        Map map = getTime("02");
        String c ="";
    }
}
