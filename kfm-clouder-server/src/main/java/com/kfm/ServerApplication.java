package com.kfm;

import com.kfm.service.ReceiveFileService;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        ReceiveFileService receiveFileService = new ReceiveFileService();
        receiveFileService.start();
    }
}