package lab.andersen.scheduler;

import lab.andersen.util.PropertiesUtils;
import lab.andersen.util.TaskExecutor;
import lab.andersen.util.TelegramSender;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.time.LocalDate;
import java.util.logging.Logger;

@WebListener
public class TelegramBotSenderListener implements ServletContextListener {
    private TaskExecutor taskExecutor;
    private static final Logger logger = Logger.getLogger(TelegramBotSenderListener.class.getName());
    private static final String FULL_PATH_PDF = PropertiesUtils.get("pdf.base.url") + LocalDate.now() + ".pdf";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        taskExecutor = new TaskExecutor(() -> {
            TelegramSender.sendMessage("daily job");
            TelegramSender.sendPDF(new File(FULL_PATH_PDF));
        });
        taskExecutor.startExecutionAt(3, 19, 0);

        logger.info("TelegramBotSender executed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        taskExecutor.stop();
    }
}
