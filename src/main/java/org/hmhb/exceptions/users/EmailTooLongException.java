package org.hmhb.exceptions.users;

import org.hmhb.exceptions.BadRequestException;

public class EmailTooLongException extends BadRequestException {

    public EmailTooLongException(String email) {
        super("Email was too long: email.length=" + email.length());
    }

}
