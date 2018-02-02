package com.neuqer.mail.client;

import com.neuqer.mail.BaseTest;
import com.neuqer.mail.client.excel.Excel;
import com.neuqer.mail.client.excel.ExcelClient;
import com.neuqer.mail.client.sms.SMSClient;
import com.neuqer.mail.exception.Client.ApiException;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by Hotown on 17/6/6.
 */
public class SMSClientTest extends BaseTest {
    @Test
    public void sendToUser() throws Exception {
        SMSClient client = (SMSClient) ClientFactory.getClient("SMS");
        ExcelClient excelClient = (ExcelClient) ClientFactory.getClient("EXCEL");
        LinkedList[] poi = excelClient.readExcel("/Users/Hotown/Desktop/NEUQer-Mail/src/main/resources/result.xlsx");
//        System.out.println(poi.length);
        for (int i = 1; i < poi.length; i++) {
            String message = "【NEUQer】{0}同学，恭喜你在图灵杯中获得了{1}，请准时参加周三晚7点，大学生会馆3楼的颁奖典礼。有问题请加QQ531584268";
            for (int j = 0; j < poi[i].size() - 1; j++) {
                String regex = Integer.toString(j);
                message = message.replaceAll("\\{" + regex + "\\}", poi[i].get(j).toString());
            }
            System.out.println(message);
            String mobile = poi[i].get(poi[i].size() - 1).toString();
            String result = client.accountPswdMobileMsgGet(mobile, message);
            int begin = result.indexOf(",");
            int end = result.indexOf(" ");
            if (!"0".equals(result.substring(begin + 1 , end))) {
                System.out.println(result);
                throw new ApiException();
            }
        }
    }
}
