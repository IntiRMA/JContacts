package org.X1C1B.software.JContacts.gui.util;

import org.X1C1B.software.JContacts.util.Contact;

import javax.swing.*;
import java.util.ArrayList;

public class ContactListModel <T extends Contact> extends AbstractListModel <T>
{
    private java.util.List <T> list;

    public ContactListModel()
    {
        this.list = new ArrayList<>();
    }

    public ContactListModel(java.util.List <T> list)
    {
        this.list = list;
    }

    public void addElement(T element)
    {
        this.list.add(element);
        int index = this.list.size();
        fireContentsChanged(element, index, index);
    }

    public void fireDataChanged()
    {
        int index = this.list.size();
        fireContentsChanged(this.list.get(index - 1), index, index);
    }

    @Override public int getSize()
    {
        return this.list.size();
    }

    @Override public T getElementAt(int index)
    {
        return this.list.get(index);
    }

    public T removeElementAt(int index)
    {
        T element = this.list.remove(index);

        fireIntervalRemoved(element, index, index);
        return element;
    }

    public T removeElement(T object)
    {
        for(int index = 0; index < this.list.size(); ++index)
        {
            if(true == list.get(index).equals(object))
            {
                return this.removeElementAt(index);
            }
        }

        return null;
    }

    public boolean contains(T object)
    {
        return this.list.contains(object);
    }

    public java.util.List <T> getList()
    {
        return this.list;
    }
}
