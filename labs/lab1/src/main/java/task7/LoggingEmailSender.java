package task7;

import task4.EmailSender;

public class LoggingEmailSender extends EmailSender {
    @Override
    protected String doSend(String recipient, String message) {
        System.out.println("[log] sending to " + recipient);
        String result = super.doSend(recipient, message);
        System.out.println("[log] sent: " + result);
        return result;
    }
}
