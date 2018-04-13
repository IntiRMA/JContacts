package org.tools.social.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DigitLimitFilter extends DocumentFilter
{
    protected int characterLimit;
    protected char [] characterList;

    public DigitLimitFilter(int limit, char ... characters)
    {
        this.characterLimit = limit;
        this.characterList = new char [characters.length];

        System.arraycopy(characters, 0, this.characterList, 0, characters.length);
    }

    @Override public void insertString(FilterBypass fb, int offset, String text,
                                       AttributeSet attr) throws BadLocationException
    {
        if(null == text)
        {
            return;
        }

        if((fb.getDocument().getLength() + text.length()) <= this.characterLimit)
        {
            super.insertString(fb, offset, revise(text), attr);
        }
    }

    @Override public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attr) throws BadLocationException
    {
        if(null == text)
        {
            return;
        }

        if((fb.getDocument().getLength() + text.length() - length) <= this.characterLimit)
        {
            super.replace(fb, offset, length, this.revise(text), attr);
        }
    }

    private String revise(String text)
    {
        int index = 0;
        StringBuilder builder = new StringBuilder(text);

        while(index < builder.length())
        {
            if(true == accept(builder.charAt(index)))
            {
                ++index;
            }
            else
            {
                builder.deleteCharAt(index);
            }
        }

        return builder.toString();
    }

    public boolean accept(final char ch)
    {
        if(true == Character.isDigit(ch))
        {
            return true;
        }

        for(char element : this.characterList)
        {
            if(ch == element)
            {
                return true;
            }
        }

        return false;
    }
}
