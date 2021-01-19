package com.uwjx.serialport;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@SpringBootApplication
@Slf4j
public class JavaSerialPortRxtxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSerialPortRxtxDemoApplication.class, args);


        List<String> ports = SerialUtil.findPort();
        for (String port : ports) {
            log.warn("port -> {}" , port);
        }
        String name = ports.get(0);
        log.warn("name : {}" , name);
        SerialPort serialPort = SerialUtil.openPort(name, 115200);
        SerialUtil.sendToPort(serialPort , "wanghuan".getBytes(StandardCharsets.UTF_8));

        SerialUtil.addListener(serialPort, new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                log.warn("event -> {}" , serialPortEvent.getEventType());
            }
        });

        String flag = "";
        while (true){
            if("stop".equals(flag)){
                break;
            }
            byte[] msg = SerialUtil.readFromPort(serialPort);
            String msgStr = new String(msg);
            log.warn("msgStr : {}" , msgStr);
            flag = msgStr;

        }
        SerialUtil.closePort(serialPort);
    }

//    public static final ArrayList<String> findPort() {
//
//        //获得当前所有可用串口
//        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
//
//        ArrayList<String> portNameList = new ArrayList<>();
//
//        //将可用串口名添加到List并返回该List
//        while (portList.hasMoreElements()) {
//            String portName = portList.nextElement().getName();
//            portNameList.add(portName);
//        }
//
//        return portNameList;
//
//    }

}
