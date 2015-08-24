package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class Resource<T> {

    private volatile T value;

    public Resource(){

    }

    public Resource(final T value){
        this.value  = value;
    }

    public T get(){
        return value;
    }

    public void set(final T value){
        this.value = value;
    }

}
