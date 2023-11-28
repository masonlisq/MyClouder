package com.kfm;

import com.kfm.controller.MonitorController;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        MonitorController monitorController = new MonitorController();
        monitorController.startMonitor();
    }
}