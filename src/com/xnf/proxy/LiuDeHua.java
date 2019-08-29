package com.xnf.proxy;

/**
 * 目标类对象
 */
public class LiuDeHua implements People{

    @Override
    public String sing(String name) {

        return "唱"+name+"歌！好听！";
    }

    @Override
    public String dance(String name) {
        return "舞蹈跳完了，谢谢大家！";
    }
}
