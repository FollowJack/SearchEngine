package model;

import java.util.Date;
import java.util.HashSet;

public class Document {
	int id;
	String original;
	HashSet<String> parsed;
	Object ranking;
	int version;
	Date created;
	
	public Document(){
		created = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public HashSet<String> getParsed() {
		return parsed;
	}

	public void setParsed(HashSet<String> parsed) {
		this.parsed = parsed;
	}

	public Object getRanking() {
		return ranking;
	}

	public void setRanking(Object ranking) {
		this.ranking = ranking;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	
}
