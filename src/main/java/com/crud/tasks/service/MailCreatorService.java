package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyDetails;
import com.crud.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyDetails companyDetails;

    @Autowired
    DbService dbService;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add(("You can manage your tasks"));
        functionality.add(("Provides connection with Trello Account"));
        functionality.add(("Application allows sending tasks to Trello"));

        Context context = new Context();
        context.setVariable("preview", "Trello app - New card has been created!");
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyDetails.getCompanyName());
        context.setVariable("company_goal", companyDetails.getCompanyGoal());
        context.setVariable("company_contact_details", companyDetails.getCompanyEmail() + companyDetails.getCompanyPhone());
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyUpdateEmail(String message) {
        List<Task> currentTasks = new ArrayList<>();
        currentTasks.addAll(dbService.getAllTasks());

        Context context = new Context();
        context.setVariable("preview", "Trello app - Your daily update is here!");
        context.setVariable("message", "You have " + currentTasks.size() + " tasks in the database!");
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Check your tasks");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyDetails.getCompanyName());
        context.setVariable("company_goal", companyDetails.getCompanyGoal());
        context.setVariable("company_contact_details", companyDetails.getCompanyEmail() + companyDetails.getCompanyPhone());
        return templateEngine.process("mail/daily-update-email", context);
    }
}
