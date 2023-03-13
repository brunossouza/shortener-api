package br.dev.bs.shortenerapi.enums;

public enum CreatedBy {
    USER, // o próprio usuário criou a URL
    ADMIN, // o administrador criou a URL e atribuiu a um usuário -- TODO: implementadar no futuro
    APPLICATION // um sistema externo criou a URL utilizando a API e um token de acesso válido atribuído a o usuário
}
