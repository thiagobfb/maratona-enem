package br.com.maratonaenem.web.rest.errors;

public class LoginAlreadyUsedException extends BadRequestAlertException {

	private static final long serialVersionUID = -3824304219133441091L;

	public LoginAlreadyUsedException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login already in use", "userManagement", "userexists");
    }
}
