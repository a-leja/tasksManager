package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    private void sendInformationEmail() {
        long size = taskRepository.count();
        String singularOrPlural = size == 1 ? " task" : "tasks";
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, "Currently in the database you've got: " + size + singularOrPlural));
    };

    private static final String SUBJECT = "Tasks: Once a day email";
}
