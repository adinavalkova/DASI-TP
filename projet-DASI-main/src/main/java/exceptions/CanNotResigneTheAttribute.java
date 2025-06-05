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
public class CanNotResigneTheAttribute extends Exception {

    /**
     * Creates a new instance of <code>CanNotResigneTheAttribute</code> without
     * detail message.
     */
    public CanNotResigneTheAttribute() {
        super("Attribute is already set and cannot modify it");
    }

    /**
     * Constructs an instance of <code>CanNotResigneTheAttribute</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CanNotResigneTheAttribute(String msg) {
        super(msg);
    }
}
