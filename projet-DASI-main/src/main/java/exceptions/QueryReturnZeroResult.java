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
public class QueryReturnZeroResult extends Exception {

    /**
     * Creates a new instance of <code>QueryReturndZeroeResult</code> without detail
     * message.
     */
    public QueryReturnZeroResult() {
        super("The query returned zero results");
    }

    /**
     * Constructs an instance of <code>QueryReturndZeroeResult</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public QueryReturnZeroResult(String msg) {
        super(msg);
    }
}
