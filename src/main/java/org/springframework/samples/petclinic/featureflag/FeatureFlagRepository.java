package org.springframework.samples.petclinic.featureflag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Integer> {

	Optional<FeatureFlag> findByKey(String key);

}
