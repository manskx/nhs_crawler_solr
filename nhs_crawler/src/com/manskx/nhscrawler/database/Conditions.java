package com.manskx.nhscrawler.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "conditions")
public class Conditions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name = "url", nullable = false)
	private String url;

	@NotEmpty
	@Column(name = "anchor", nullable = false)
	private String anchor;

	@NotEmpty
	@Column(name = "title", nullable = false)
	private String title;

	@NotEmpty
	@Column(name = "header", nullable = false)
	private String header;

	@NotEmpty
	@Column(name = "contentdata", nullable = false)
	private String contentdata;
	
	@NotNull
	@Column(name="hashed_url",  unique=true, nullable=false)
	private Integer 	hashed_url;
	
	@Override
	public int hashCode() {
		return hashed_url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContentdata() {
		return contentdata;
	}

	public void setContentdata(String contentdata) {
		this.contentdata = contentdata;
	}

	public Integer getHashed_url() {
		return hashed_url;
	}

	public void setHashed_url(Integer hashed_url) {
		this.hashed_url = hashed_url;
	}
}
