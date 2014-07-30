package model;

import java.util.Date;
import java.util.HashSet;

public class Document {
	String original;
	HashSet<String> parsed;
	Object ranking;
	int version;
	Date created;

	public Document(String originalString) {
		original = originalString;
		created = new Date();
		version = 1;
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

	@Override
	public String toString() {
		return original;
	}
}
