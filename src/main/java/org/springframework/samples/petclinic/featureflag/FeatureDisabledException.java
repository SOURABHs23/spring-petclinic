package org.springframework.samples.petclinic.featureflag;

public class FeatureDisabledException extends RuntimeException {

	public FeatureDisabledException(String key) {
		super("Feature '" + key + "' is currently disabled.");
	}
}
