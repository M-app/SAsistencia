package com.example.sasistencia.login;

public class LoginInteractorImpl implements LoginInteractor {

    LoginRepository loginRepository;

    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void loginUsuario(String email, String password) {
        loginRepository.loginUsuario(email,password);
    }

    @Override
    public void checkLogin() {
        loginRepository.checkLogin();
    }
}
