package storage;

public class CSVManager implements StorageManager {
    private static int loadCount;
    private static int saveCount;

    public CSVManager(){
        loadCount = 0;
        saveCount = 0;
    }

    private String readSpecificLine(String filePath, int lineNumber){
        return "foo";
    }

    public void load(Storable s, String filePath){
        String data = readSpecificLine(filePath, loadCount);
        if(data == null)
            throw new RuntimeException();
        s.unmarshal(data);
    }

    public void save(Storable s, String filePath, boolean append){
        
    }

}
