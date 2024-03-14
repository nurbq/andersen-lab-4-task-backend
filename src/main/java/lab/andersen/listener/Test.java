package lab.andersen.listener;

import lab.andersen.service.EmailSenderService;
import lab.andersen.util.PdfGenerator;
import lab.andersen.util.PropertiesUtils;
import lab.andersen.util.TaskExecutor;
import lab.andersen.util.TelegramSender;

import java.io.File;
import java.time.LocalDate;
import java.util.logging.Logger;

public class Test {
    private static final String EMAIL_TO = PropertiesUtils.get("mail.send.to");
    private static final String EMAIL_PASSWORD = PropertiesUtils.get("mail.password");
    private static final String EMAIL_FROM = PropertiesUtils.get("mail.send.from");
    private static final String bodyMessage = "TEST";
    private static final String subjectMessage = "TEST123";
    private static final String FULL_PATH_PDF = PropertiesUtils.get("pdf.base.url") + LocalDate.now() + ".pdf";
    private static final int TARGET_HOUR = 12;
    private static final int TARGET_MIN = 55;
    private static final int TARGET_SEC = 0;


    public static void main(String[] args) {
        PdfGenerator.getINSTANCE().generate();

        EmailSenderService.getINSTANCE().sendEmail(
                EMAIL_TO,
                EMAIL_PASSWORD,
                EMAIL_FROM,
                bodyMessage,
                subjectMessage,
                FULL_PATH_PDF
        );

        TelegramSender.sendMessage("test daily job");
        TelegramSender.sendPDF(new File(FULL_PATH_PDF));
    }
}
