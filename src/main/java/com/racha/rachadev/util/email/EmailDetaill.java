package com.racha.rachadev.util.email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// @AllArgsConstructor
public class EmailDetaill {
    private String recipient;
    private String msgBody;
    private String subject;

    public EmailDetaill(String recipient, String msgBody, String subject) {
        this.recipient = recipient;
        this.msgBody = msgBody;
        this.subject = subject;
    }
}
