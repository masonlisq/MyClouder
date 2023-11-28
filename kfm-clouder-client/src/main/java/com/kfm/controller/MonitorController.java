package com.kfm.controller;

import com.kfm.service.MonitorService;

public class MonitorController {
    /**
     * 启动监听的方法
     */
    public void startMonitor(){
        MonitorService monitorService = new MonitorService();
        try {
            monitorService.monitor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
