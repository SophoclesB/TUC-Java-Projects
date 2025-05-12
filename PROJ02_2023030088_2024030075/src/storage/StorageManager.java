package storage;

public interface StorageManager {
    public void load(Storable s, String filePath);
    public void save(Storable s, String filePath, boolean append);
}
