package cz.example.sentence.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sentence")
public class Sentence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime created;

	@Column(nullable = false)
	private String text;

	@Column(nullable = false)
	private int displayCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDisplayCount() {
		return displayCount;
	}

	public void setDisplayCount(int displayCount) {
		this.displayCount = displayCount;
	}
}