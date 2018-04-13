package org.tools.social.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The TextFieldLimit class defines a method for restricting the input length
 * of a JTextField to a given length.
 *
 * @since 2018-03-23
 */

public class TextFieldLimit extends PlainDocument
{
    private int limit;

    public TextFieldLimit(int limit)
    {
        super();
        this.limit = limit;
    }

    @Override public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException
    {
        if(null == text)
        {
            return;
        }

        if((this.getLength() + text.length()) <= this.limit)
        {
            super.insertString(offset, text, attr);
        }
    }
}