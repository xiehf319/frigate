package cn.cici.frigate.monitor.controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @description: 操作系统监控
 * @createDate:2019/7/1$14:27$
 * @author: Heyfan Xie
 */
@RestController
public class OsController {


    public String getMacAddress() {

        String address = "";
        String os = System.getProperty("os.name");
        String osUser = System.getProperty("user.name");
        if (os != null && os.startsWith("Windows")) {
            try {
                String command = "cmd.exe /c ipconfig/all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("Physical Address") > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                        break;
                    }
                }
                br.close();
                return address.trim();
            } catch (Exception e) {

            }
        }
        return address;
    }

}
