package org.springframework.samples.petclinic.featureflag;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.featureflag.FeatureFlag;
import org.springframework.samples.petclinic.featureflag.FeatureFlagRepository;

@Service
public class FeatureFlagService {

	private final FeatureFlagRepository repository;

	public FeatureFlagService(FeatureFlagRepository repository) {
		this.repository = repository;
	}

	// Helper method usable anywhere in the app
	@Transactional(readOnly = true)
	public boolean isEnabled(String key) {
		return repository.findByKey(key)
			.map(FeatureFlag::isEnabled)
			.orElse(false); // fail-safe
	}

	@Transactional
	public FeatureFlag save(FeatureFlag flag) {
		return repository.save(flag);
	}

	@Transactional(readOnly = true)
	public List<FeatureFlag> findAll() {
		return repository.findAll();
	}

	@Transactional
	public void deleteByKey(String key) {
		repository.findByKey(key)
			.ifPresent(repository::delete);
	}
}
