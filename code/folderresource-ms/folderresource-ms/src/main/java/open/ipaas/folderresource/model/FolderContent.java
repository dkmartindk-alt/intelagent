package open.ipaas.folderresource.model;

import java.util.List;

public class FolderContent {

    private String folderId;
    private List<Resource> contents;
    private Integer totalSize;

    // Default constructor
    public FolderContent() {}

    // Constructor with required fields
    public FolderContent(String folderId, List<Resource> contents) {
        this.folderId = folderId;
        this.contents = contents;
        this.totalSize = contents != null ? contents.size() : 0;
    }

    // Getters and Setters
    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public List<Resource> getContents() {
        return contents;
    }

    public void setContents(List<Resource> contents) {
        this.contents = contents;
        this.totalSize = contents != null ? contents.size() : 0;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
}
