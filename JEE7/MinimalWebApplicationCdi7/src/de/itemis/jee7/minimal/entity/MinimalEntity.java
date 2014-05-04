package de.itemis.jee7.minimal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="minimal")
public class MinimalEntity {
	private int id;
	private String name;

	@Id
	@TableGenerator(name = "MinimalIDs", table = "IDs", pkColumnName = "id", valueColumnName = "next_value", pkColumnValue = "Minimal", initialValue = 1, allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MinimalIDs")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
