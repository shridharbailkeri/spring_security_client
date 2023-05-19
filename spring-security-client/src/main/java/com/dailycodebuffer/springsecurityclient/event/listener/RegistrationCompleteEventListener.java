package com.dailycodebuffer.springsecurityclient.event.listener;

import com.dailycodebuffer.springsecurityclient.entity.User;
import com.dailycodebuffer.springsecurityclient.event.RegistrationCompleteEvent;
import com.dailycodebuffer.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        // create verification token for user for User with link
        // when user clicks this link he will be redirected back to the application
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationServiceForUser(token, user);

        // Send mail to User
        // event.getApplicationUrl() this is ur context url
        // verifyRegistration api
        // ?token= parameter

        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        // sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);

    }
}
