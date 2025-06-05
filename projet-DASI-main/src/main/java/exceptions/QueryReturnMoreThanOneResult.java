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
public class QueryReturnMoreThanOneResult extends Exception {

    /**
     * Creates a new instance of <code>QueryReturndMoreThanOneResult</code> without detail
     * message.
     */
    public QueryReturnMoreThanOneResult() {
        super("The query returned more than one results");
    }

    /**
     * Constructs an instance of <code>QueryReturndMoreThanOneResult</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public QueryReturnMoreThanOneResult(String msg) {
        super(msg);
    }
}
