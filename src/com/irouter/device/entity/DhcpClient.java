
package com.irouter.device.entity;

public class DhcpClient {
    public String mac;
    public String deviceName;
    public String ip;

    public DhcpClient(String mac) {
        this.mac = mac;
    }

    public DhcpClient(String deviceName, String mac, String ip) {
        this.deviceName = deviceName;
        this.mac = mac;
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "DhcpClient [mac=" + mac + ", deviceName=" + deviceName + ", ip=" + ip + "]";
    }
}
