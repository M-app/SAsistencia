package com.example.sasistencia.registro;

public class RegistroInteractorImpl implements RegistroInteractor {

    RegistroRepository registroRepository;

    public RegistroInteractorImpl() {
        registroRepository = new RegistroRepositoryImpl();
    }

    @Override
    public void manejarRegistro(String email, String nombre, String pass) {
        registroRepository.manejarRegistro(email,nombre,pass);
    }
}



