package br.com.maratonaenem.web.rest.errors;

public class EmailAlreadyUsedException extends BadRequestAlertException {

	private static final long serialVersionUID = -8991909061459180056L;

	public EmailAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email address already in use", "userManagement", "emailexists");
    }
}
