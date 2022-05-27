package com.zhlw.module.common.network;

import com.zhlw.module.common.constant.MConfigKt;

/**
 * 用作环境切换的.. 根据自己的业务补充
 */
public class HostManager {

    private HostManager() {

    }

    public static final String HOST_DOMAIN_OFFICE = MConfigKt.HOST_DOMAIN_OFFICE;

    public static String getCurrentHost() {
        return HOST_DOMAIN_OFFICE;
    }

    /**
     * 是否正式环境
     * @return boolean
     */
    public static boolean isReleaseHost() {
        return HostManager.getCurrentHost().equals(HOST_DOMAIN_OFFICE);
    }

}
