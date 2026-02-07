package org.springframework.samples.petclinic.featureflag;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureFlagAspect {

	private final FeatureFlagService service;

	public FeatureFlagAspect(FeatureFlagService service) {
		this.service = service;
	}

	@Before("@annotation(featureToggle)")
	public void checkFeature(FeatureToggle featureToggle) {

		String key = featureToggle.value();

		if (!service.isEnabled(key)) {
			throw new FeatureDisabledException(key);
		}
	}

}
