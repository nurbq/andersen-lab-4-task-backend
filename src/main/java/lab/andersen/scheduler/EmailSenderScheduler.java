package lab.andersen.scheduler;

import lab.andersen.service.EmailSenderService;
import lab.andersen.util.PdfGenerator;
import lab.andersen.util.PropertiesUtils;
import lab.andersen.util.TaskExecutor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import java.time.LocalDate;
import java.util.logging.Logger;

@WebServlet
public class EmailSenderScheduler implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(EmailSenderService.class.getName());
    private TaskExecutor taskExecutor;
    private static final String EMAIL_TO = PropertiesUtils.get("mail.send.to");
    private static final String EMAIL_PASSWORD = PropertiesUtils.get("mail.password");
    private static final String EMAIL_FROM = PropertiesUtils.get("mail.send.from");
    private static final String bodyMessage = "Attaching files";
    private static final String subjectMessage = "Activity of users";
    private static final String FULL_PATH_PDF = PropertiesUtils.get("pdf.base.url") + LocalDate.now() + ".pdf";
    private static final int TARGET_HOUR = 16;
    private static final int TARGET_MIN = 5;
    private static final int TARGET_SEC = 0;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        taskExecutor = new TaskExecutor(() -> {
            PdfGenerator.getINSTANCE().generate();

            EmailSenderService.getINSTANCE().sendEmail(
                    EMAIL_TO,
                    EMAIL_PASSWORD,
                    EMAIL_FROM,
                    bodyMessage,
                    subjectMessage,
                    FULL_PATH_PDF
            );
        });

        taskExecutor.startExecutionAt(TARGET_HOUR, TARGET_MIN, TARGET_SEC);
        logger.info("EmailSender executed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        taskExecutor.stop();
    }
}

