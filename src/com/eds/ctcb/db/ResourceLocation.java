package com.eds.ctcb.db;

// default package

/**
 * ResourceLocation generated by MyEclipse - Hibernate Tools
 */

public class ResourceLocation implements java.io.Serializable {

	// Fields

	private Long id;

	private Resource resource;

	private Location location;

	// Constructors

	/** default constructor */
	public ResourceLocation() {
	}

	/** full constructor */
	public ResourceLocation(Long id, Resource resource, Location location) {
		this.id = id;
		this.resource = resource;
		this.location = location;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}