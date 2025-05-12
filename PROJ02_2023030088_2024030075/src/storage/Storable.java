package storage;

public interface Storable {
    String marshal();
    void unmarshal(String data);
}
