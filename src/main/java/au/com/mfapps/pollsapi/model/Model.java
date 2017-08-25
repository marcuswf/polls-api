package au.com.mfapps.pollsapi.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class Model implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4687418055470364038L;

	public Model() {
	}
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
