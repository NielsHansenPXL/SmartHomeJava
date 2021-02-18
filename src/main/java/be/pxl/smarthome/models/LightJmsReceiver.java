package be.pxl.smarthome.models;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class LightJmsReceiver {

    @JmsListener(destination="LightQueue")
    public void onMessage(Message msg) {
        try {
            if(msg instanceof TextMessage) {
                String text = ((TextMessage)msg).getText();
                System.out.println("Status: " + text);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
