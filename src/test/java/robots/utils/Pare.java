package robots.utils;

import java.util.Objects;

public class Pare <K, V> {
    private K key;
    private V value;

    public Pare(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pare<?, ?> pare = (Pare<?, ?>) o;
        return Objects.equals(key, pare.key) &&
                Objects.equals(value, pare.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Pare{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
