package org.obicere.bcviewer.dom;

/**
 * @author Obicere
 */
public class SharedResource<T> extends Resource<T> {

    private final ResourcePool<T> pool;

    private final String name;

    public SharedResource(final ResourcePool<T> pool, final String name) {
        if(pool == null){
            throw new NullPointerException("pool cannot be null.");
        }
        if(name == null){
            throw new NullPointerException("name cannot be null.");
        }
        this.pool = pool;
        this.name = name;
    }

    public SharedResource(final ResourcePool<T> pool, final String name, final T value) {
        if(pool == null){
            throw new NullPointerException("pool cannot be null.");
        }
        if(name == null){
            throw new NullPointerException("name cannot be null.");
        }
        this.pool = pool;
        this.name = name;
        pool.add(name, value);
    }

    @Override
    public T get() {
        return pool.get(name);
    }

    @Override
    public void set(final T value) {
        pool.add(name, value);
    }
}
