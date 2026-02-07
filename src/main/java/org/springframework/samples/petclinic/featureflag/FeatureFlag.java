package org.springframework.samples.petclinic.featureflag;

import org.springframework.samples.petclinic.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_flags")
public class FeatureFlag extends BaseEntity {

	@Column(name = "flag_key", nullable = false, unique = true)
	private String key;

	@Column(name = "description")
	private String description;

	@Column(name = "enabled", nullable = false)
	private boolean enabled = true;

	public FeatureFlag() {
	}

	public FeatureFlag(String key, boolean enabled) {
		this.key = key;
		this.enabled = enabled;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
