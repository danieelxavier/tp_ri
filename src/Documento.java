import java.util.ArrayList;

public class Documento {

	private String paperNumber;
	private int recordNumber;
	private String medlineAcessionNumber;
	private ArrayList<String> Authors;
	private String title;
	private String source;
	private int year;
	private ArrayList<String> majorSubjects;
	private ArrayList<String> minorSubjects;
	private String Abstract;
	private ArrayList<String> references;
	private ArrayList<String> citations;
	
	public String getPaperNumber() {
		return paperNumber;
	}
	public void setPaperNumber(String paperNumber) {
		this.paperNumber = paperNumber;
	}
	public int getRecordNumber() {
		return recordNumber;
	}
	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}
	public String getMedlineAcessionNumber() {
		return medlineAcessionNumber;
	}
	public void setMedlineAcessionNumber(String medlineAcessionNumber) {
		this.medlineAcessionNumber = medlineAcessionNumber;
	}
	public ArrayList<String> getAuthors() {
		return Authors;
	}
	public void setAuthors(ArrayList<String> authors) {
		Authors = authors;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public ArrayList<String> getMajorSubjects() {
		return majorSubjects;
	}
	public void setMajorSubjects(ArrayList<String> majorSubjects) {
		this.majorSubjects = majorSubjects;
	}
	public ArrayList<String> getMinorSubjects() {
		return minorSubjects;
	}
	public void setMinorSubjects(ArrayList<String> minorSubjects) {
		this.minorSubjects = minorSubjects;
	}
	public String getAbstract() {
		return Abstract;
	}
	public void setAbstract(String abstract1) {
		Abstract = abstract1;
	}
	public ArrayList<String> getReferences() {
		return references;
	}
	public void setReferences(ArrayList<String> references) {
		this.references = references;
	}
	public ArrayList<String> getCitations() {
		return citations;
	}
	public void setCitations(ArrayList<String> citations) {
		this.citations = citations;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
