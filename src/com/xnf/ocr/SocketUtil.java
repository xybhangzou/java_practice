package com.xnf.ocr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * 自主测试各jpg 合同版本的内容。
 */
public class SocketUtil {
    private Logger logger = Logger.getLogger(this.getClass());
    private Socket socket;
    /**
     * 判断是否断开连接，断开返回true,没有返回false
     * @param
     * @return
     */

    public static String scan (String ip, int port, String filePath){
        SocketUtil socketUtil = new SocketUtil();
        Boolean serverClose = socketUtil.isServerClose(ip, port);
        if(serverClose){
            return "true";
        }
        return socketUtil.send(ip, port, filePath);
    }

    public Boolean isServerClose(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            socket.setSoTimeout(120000);
            //下面这段话注释，否则windows引擎无法使用
            //socket.sendUrgentData(0xFF);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            return false;
        } catch (Exception se) {
            return true;
        }
    }

    /**
     * 调用引擎
     * @param ip 引擎IP地址
     * @param port 引擎端口
     * @param filePath 要识别图片地址
     * @return
     * @author wujp
     * 2019年9月17日上午9:47:36
     */
    public String send(String ip, int port, String filePath) {
        String result = null;
        try {
            // 2.获取输出流，用来向服务器发送信息
            OutputStream os = socket.getOutputStream();// 字节输出流
            // 转换为打印流
            PrintWriter pw = new PrintWriter(os);
            pw.write(filePath);
            pw.flush();// 刷新缓存，向服务器端输出信息
            // 关闭输出流
//			socket.shutdownOutput();
            // 3.获取输入流，用来读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            Charset charset = Charset.forName("gbk");
            result = IOUtils.toString(is, charset);
            result = StringUtils.trimAllWhitespace(result);
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("socket传输异常：" + ex.getMessage());
            ex.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 合同版本号
        String ContactVersion="";
        //合同编号
        String ContactNo="";
        //供电人
        String OrgName="";
        //用电人
        String ConsName="";
        //用户编号
        String ConsNo="";
        //用电地址
        String ConsAddr="";
        //签订时间
        String ContactTime="";
        //行业类型
        String HangyeType="";
        //用电类型
        String ConsSort="";
        //负荷性质
        String LoadType="";
        //用电容量
        String ConsCap="";
        //变压器信息
        String TransInfo="";
        //供电方式
        String ElecSupply="";
        //产权分界点描述
        String RightPoint="";
        //电源数目
        String PowerNum="";
        //合同有效期
        String ContactValidity="";
        //自备应急电源
        String EmerPower="";

        // 合同版本号
        String ContactVersionValue="SGTYHT";
        //合同编号
        String ContactNoValue="合同编号";
        //供电人
        String OrgNameValue="供电人";
        String OrgNameKeyValue="名称";
        //用电人
        String ConsNameValue="用电人";
        //用户编号
        String ConsNoValue="用户编号";
        //用电地址
        String ConsAddrValue="用电地址";
        //签订时间
        String ContactTimeValue="签订日期";
        //行业类型
        String HangyeTypeValue="行业分类";
        //用电类型
        String ConsSortValue="用电分类";
        //负荷性质
        String LoadTypeValue="负荷性质";
        //用电容量
        String ConsCapValue="用电总容量";
        //变压器信息
        String TransInfoValue="第三条用电容量";
        //供电方式
        String ElecSupplyValue="电源性质";
        String ElecSupplyValue1="第五条补偿及功率因数";
        //产权分界点描述
        String RightPointValue="为产权分界点";
        //电源数目
        String PowerNumValue="第四条供电方式";
        String PowerNumValue1="采用";
        String PowerNumValue2="向用电人";
        //合同有效期
        String ContactValidityValue="合同有效期为";
        //自备应急电源
        String EmerPowerValue="自备应急电源";
        //中文慢号
        String manhao="：";
        //英文慢号
        String manhaoEg=":";
        //中文
        String juhao="。";
        //英文句号
        String juhaoEg=".";
        //中文
        String fenhao="；";
        //英文分号
        String fenhaoEg=";";
        StringBuffer strbuf = new StringBuffer();

        for (int j=1;j<=19;j++) {
            String img="d:\\ht\\"+j+".jpg";
            String resltstr = scan("127.0.0.1", 9160, img);
            JSONObject res=new JSONObject(resltstr);
            String content  = res.get("content").toString();
            String reslt[]=content.split("\\n\\r\\n");
            System.out.println(content);
//            for(int i=0;i<reslt.length;i++) {
//                String s=new String(reslt[i].getBytes("utf-8"),"utf-8").trim();
//                strbuf.append(s);
//                strbuf.append("\n");
//                if(j==0) {
//
//                    if(ContactVersion.equals("")&&s.indexOf(ContactVersionValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ContactVersion=s;
//                        System.out.println("ContactVersion->"+ContactVersion);
//                    }
//                    //閸氬牆鎮撶紓鏍у娇
//                    if(ContactNo.equals("")&&s.indexOf(ContactNoValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ContactNo=s;
//                        System.out.println("ContactNo->"+ContactNo);
//                    }
//                    //娓氭稓鏁告禍锟�
//                    if(OrgName.equals("")&&s.indexOf(OrgNameValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        OrgName=s;
//                        System.out.println("OrgName->"+OrgName);
//                    }
//                    //閻€劎鏁告禍锟�
//                    if(ConsName.equals("")&&s.indexOf(ConsNameValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ConsName=s;
//                        System.out.println("ConsName->"+ConsName);
//                    }
//                    //閻€劍鍩涚紓鏍у娇
//                    if(ConsNo.equals("")&&s.indexOf(ConsNoValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ConsNo=s;
//                        System.out.println("ConsNo->"+ConsNo);
//                    }
//                    //缁涙崘顓归弮鍫曟？
//                    if(ContactTime.equals("")&&s.indexOf(ContactTimeValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ContactTime=s;
//                        System.out.println("ContactTime->"+ContactTime);
//                    }
//
//                    //2.通过行定位查找
//                    if("".equals(ContactNo)&&i==0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ContactNo=s;
//                        System.out.println("ContactNo->"+ContactNo);
//                    }
//                    if(ContactTime==""&&i==3) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ContactTime=s;
//                        System.out.println("ContactTime->"+ContactTime);
//                    }
//
//                }else if(j==1)
//                {
//                    if("".equals(OrgName)&&"".equals(ConsName)&&i==1)//闁俺绻冪悰灞界暰娴ｅ秷骞忛崣锟�
//                    {
//                        OrgName=s.substring(s.indexOf(OrgNameKeyValue),s.lastIndexOf(OrgNameKeyValue));//名称：山西省电力公司高平供电支公司名称：高平市第二职业中学校
//                        if(OrgName.indexOf(manhao)>0) {
//                            OrgName=OrgName.substring(OrgName.indexOf(manhao)+1, OrgName.length());
//                        }
//                        if(OrgName.indexOf(manhaoEg)>0) {
//                            OrgName=OrgName.substring(OrgName.indexOf(manhaoEg)+1, OrgName.length());
//                        }
//                        ConsName=s.substring(s.lastIndexOf(OrgNameKeyValue)+2,s.length());
//                        if(ConsName.indexOf(manhao)>0) {
//                            ConsName=ConsName.substring(ConsName.indexOf(manhao)+1, ConsName.length());
//                        }
//                        if(ConsName.indexOf(manhaoEg)>0) {
//                            ConsName=ConsName.substring(ConsName.indexOf(manhaoEg)+1, ConsName.length());
//                        }
//                        System.out.println("OrgName->"+OrgName);
//                        System.out.println("ConsName->"+ConsName);
//                    }
//
//                }else if(j==7)
//                {
//                    String hh="户号";
//                    Boolean a=	s.contains(hh);
//                    if(s.contains(hh)) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        System.out.println(i+"no----------------->"+s);
//                    }
//                    hh="档案号";
//                    if(s.contains(hh)) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        System.out.println(i+"danganno"+s);
//                    }
//                    hh="合同编号";
//                    if(s.contains(hh)) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        System.out.println(i+"-contactno------------------------------>"+s);
//                    }
//                    hh="供电人";
//                    if(s.contains(hh)) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        System.out.println(i+"OrgName---------------------->"+s);
//                    }
//                    hh="用电人";
//                    if(s.contains(hh)) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        System.out.println(i+"ConsName------------------------------------->"+s);
//                    }
//                    hh="签订日期";
//                    if(s.contains(hh)) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        System.out.println(i+"ContactTime------------------------->"+s);
//                    }
//
//                }else {
//
//                    if(ConsAddr.equals("")&&s.indexOf(ConsAddrValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ConsAddr=s.replace(juhao, "").replace(juhaoEg, "");
//                        System.out.println(i+"ConsAddr->"+ConsAddr);
//                    }
//                    //
//                    if(HangyeType.equals("")&&s.indexOf(HangyeTypeValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        HangyeType=s.replace(juhao, "").replace(juhaoEg, "");
//                        System.out.println(i+"HangyeType->"+HangyeType);
//                    }
//                    //
//                    if(ConsSort.equals("")&&s.indexOf(ConsSortValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        ConsSort=s.replace(juhao, "").replace(juhaoEg, "");
//                        System.out.println(i+"ConsSort->"+ConsSort);
//                    }
//                    //
//                    if(LoadType.equals("")&&s.indexOf(LoadTypeValue)>0) {
//                        if(s.indexOf(manhao)>0) {
//                            s=s.substring(s.indexOf(manhao)+1, s.length());
//                        }
//                        if(s.indexOf(manhaoEg)>0) {
//                            s=s.substring(s.indexOf(manhaoEg)+1, s.length());
//                        }
//                        LoadType=s.replace(fenhao, "").replace(fenhaoEg, "");
//                        System.out.println(i+"LoadType->"+LoadType);
//                    }
//
//                    //
//                    if(ConsCap.equals("")&&s.indexOf(ConsCapValue)>0) {
//
//                        s=s.substring(s.indexOf(ConsCapValue)+5, s.length());
//                        ConsCap=s.replace(juhao, "").replace(juhaoEg, "");
//                        System.out.println(i+"ConsCap->"+ConsCap);
//                    }
//                    //
//                    if((s.contentEquals(PowerNumValue)||s.indexOf(PowerNumValue)>0)&&PowerNum.equals("")) {
//
//                        PowerNum=reslt[i+1].toString().trim().replace(juhao, "");
//                        PowerNum=PowerNum.substring(PowerNum.indexOf(PowerNumValue1)+2, PowerNum.lastIndexOf(PowerNumValue2));
//                        System.out.println("PowerNum->"+PowerNum);
//                    }
//
//                    //
//                    if(s.indexOf(EmerPowerValue)>0&&EmerPower.equals("")) {
//                        EmerPower=s;
//                        System.out.println("EmerPower->"+EmerPower);
//                    }
//                    //
//                    if(s.indexOf(RightPointValue)>0&&RightPoint.equals("")) {
//                        RightPoint=s;
//                        System.out.println("RightPoint->"+RightPoint);
//                    }
//
//                    //
//                    if((s.indexOf(ContactValidityValue)>0||s.contains(ContactValidityValue))&&ContactValidity.equals("")) {
//
//                        ContactValidity=s.substring(6,8);
//                        System.out.println("ContactValidity->"+ContactValidity);
//                    }
//
//                }
//            }

        }
        String reslut=strbuf.toString();
        //1閸欐ê甯囬崳銊や繆閹拷
        if(reslut.indexOf(TransInfoValue)>0&&reslut.indexOf(PowerNumValue)>0&&TransInfo.equals("")) {
            TransInfo=reslut.substring(reslut.indexOf(TransInfoValue),reslut.indexOf(PowerNumValue)-1);
            System.out.println("TransInfo->"+TransInfo);
        }
        //娓氭稓鏁搁弬鐟扮础
        if(reslut.indexOf(ElecSupplyValue)>0&&reslut.indexOf(ElecSupplyValue1)>0&&ElecSupply.equals("")) {
            ElecSupply=reslut.substring(reslut.indexOf(ElecSupplyValue),reslut.indexOf(ElecSupplyValue1)-1);
            System.out.println("ElecSupply->"+ElecSupply);
        }
//
        System.out.println("全文信息--------->\n"+reslut);
    }
}

