/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author rkhedair
 */
public class IdDontMatchWithForgienKey extends Exception {

    /**
     * Creates a new instance of <code>IdDontMatchWithForgienKey</code> without
     * detail message.
     */
    public IdDontMatchWithForgienKey() {
        super("The id dont match the other id");
    }

    /**
     * Constructs an instance of <code>IdDontMatchWithForgienKey</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IdDontMatchWithForgienKey(String msg) {
        super(msg);
    }
}
