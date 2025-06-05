/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author agarnier
 */
public class EntityAlreadyExists extends Exception {

    /**
     * Creates a new instance of <code>SchoolDoesNotExist</code> without detail
     * message.
     */
    public EntityAlreadyExists() {
        super("The entity provided already exist");
    }

    /**
     * Constructs an instance of <code>StudentDoesNotExist</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityAlreadyExists(String msg) {
        super(msg);
    }
}
