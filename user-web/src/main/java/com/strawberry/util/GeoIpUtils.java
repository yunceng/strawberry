package com.strawberry.util;

import com.maxmind.geoip.LookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * 功能：根据IP地址获取位置信息
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.28
 */
public class GeoIpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(GeoIpUtils.class);

    /**
     * 根据ip地址获取国家信息
     * @param ipAddr
     * @return
     */
    public static String getCountryCode(String ipAddr) {
        ipAddr = checkIP(ipAddr);
        return getLookupService().getCountry(ipAddr).getCode();
    }

    /**
     * 初始化信息
     *
     * @return
     */
    private static LookupService getLookupService(){
        LookupService ls = null;
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String ipPtah = url.getFile() + "GeoIP.dat";
        try {
            ls = new LookupService(ipPtah, LookupService.GEOIP_MEMORY_CACHE);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error(String.format("%s 文件找不到", ipPtah));
        }
        return ls;
    }

    /**
     * 去除IP值中的非法字符
     *
     * @param ipPtah
     * @return
     */
    private static String checkIP(String ipPtah) {
        try {
            ipPtah = ipPtah.replaceAll("/", "");
            int xindex = ipPtah.indexOf(":");
            if (xindex > 0) {
                ipPtah = ipPtah.substring(0, xindex);
            }
            return ipPtah;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
}
