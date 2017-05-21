package actor.messages;

public class FileNameAndPath {
	private final String fileName;
	private final String absPath;
	public FileNameAndPath(String fileName, String absPath){
		this.fileName = fileName;
		this.absPath = absPath;
	}
	public String getFileName() {
		return fileName;
	}
	public String getAbsPath() {
		return absPath;
	}
	
}
