package com.cg.flight.exceptions;

public class NoVacancyException extends Exception {
    private static final long serialVersionUID = 1L;

    public NoVacancyException() {
        super("No Vacant Seats Available");
    }

    public NoVacancyException(String message){
        super(message);
    }
}