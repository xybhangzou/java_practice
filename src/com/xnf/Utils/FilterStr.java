package com.xnf.Utils;

import com.xnf.test.FaArBag;
import com.xnf.test.Son;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql 注入过滤。
 */
public class FilterStr {

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 入参过滤，防止sql 注入。
     * @param str
     * @return
     */
    public static  String paramValidate(String str) {
        String str2 = str.toLowerCase();//统一转为小写
        //sql 关键字
        String[] SqlStr1 = {"and", "exec", "execute", "insert", "select", "delete", "update", "count", "drop", "chr", "mid", "master", "truncate", "char", "declare", "sitename", "net user", "xp_cmdshell", "like", "and", "exec", "execute", "insert", "create", "drop", "table", "from", "grant", "use", "group_concat", "column_name", "information_schema.columns", "table_schema", "union", "where", "select", "delete", "update", "order", "by", "count", "chr", "mid", "master", "truncate", "char", "declare", "or"};//词语
        for (int i = 0; i < SqlStr1.length; i++) {
            if (str2.indexOf(SqlStr1[i]) >= 0) {
                str2 = str2.replaceAll(SqlStr1[i], "");//正则替换词语，无视大小写
            }
        }
        //特殊字符
        str2 = stringFilter(str2);
        return str2;
    }

    public static void main(String[] args) {
        System.out.println(paramValidate("aaa%';drop table tbl_test;#"));
    }
}
