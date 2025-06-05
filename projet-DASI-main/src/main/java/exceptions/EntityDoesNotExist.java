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
public class EntityDoesNotExist extends Exception {
    /**
     * Creates a new instance of <code>SchoolDoesNotExist</code> without detail
     * message.
     */
    public EntityDoesNotExist() {
        super("The entity id provided doesn't exist");
    }

    /**
     * Constructs an instance of <code>StudentDoesNotExist</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityDoesNotExist(String msg) {
        super(msg);
    }
}
