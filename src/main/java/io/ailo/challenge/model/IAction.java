package io.ailo.challenge.model;

public interface IAction {

    /**
     * Set square coordinates based on action
     * @param s current square
     */
    void apply(Square s);
}
