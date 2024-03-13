package lab.andersen.listener;

import lab.andersen.util.TaskExecutor;
import lab.andersen.util.TelegramSender;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private TaskExecutor taskExecutor;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        taskExecutor = new TaskExecutor(() -> {
            TelegramSender.sendMessage("test daily job");
        });
        taskExecutor.startExecutionAt(23, 55, 0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        taskExecutor.stop();
    }
}
