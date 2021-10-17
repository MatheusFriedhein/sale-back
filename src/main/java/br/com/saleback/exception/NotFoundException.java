package br.com.saleback.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {}

    public NotFoundException(String msg) {
        super(msg);
    }

}
