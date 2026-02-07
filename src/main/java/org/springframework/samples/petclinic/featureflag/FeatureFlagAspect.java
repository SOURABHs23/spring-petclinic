package org.springframework.samples.petclinic.featureflag;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.samples.petclinic.featureflag.FeatureToggle;
import org.springframework.samples.petclinic.featureflag.FeatureFlagService;

@Aspect
@Component
public class FeatureFlagAspect {

	private final FeatureFlagService service;

	public FeatureFlagAspect(FeatureFlagService service) {
		this.service = service;
	}

	@Around("@annotation(featureToggle)")
	public Object checkFeature(ProceedingJoinPoint joinPoint,
							   FeatureToggle featureToggle) throws Throwable {

		String key = featureToggle.value();

		if (!service.isEnabled(key)) {
			throw new FeatureDisabledException(key);
		}

		return joinPoint.proceed();
	}

}
