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
public class AuthIncorrect extends Exception {

    /**
     * Creates a new instance of <code>AuthIncorrect</code> without detail
     * message.
     */
    public AuthIncorrect() {
        super("Auth est incorrecte");
    }

    /**
     * Constructs an instance of <code>AuthIncorrect</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public AuthIncorrect(String msg) {
        super(msg);
    }
}
