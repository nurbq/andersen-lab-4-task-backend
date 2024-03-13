package lab.andersen.listener;

import lab.andersen.util.TelegramSender;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class AppContextListener implements ServletContextListener {
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true);
        timer.schedule(new SendPdfJob(), 0, 10000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
    }

    private static class SendPdfJob extends TimerTask {
        @Override
        public void run() {
            TelegramSender.sendMessage("timer test");
        }
    }
}
