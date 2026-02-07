package org.springframework.samples.petclinic.featureflag;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.samples.petclinic.featureflag.FeatureFlag;
import org.springframework.samples.petclinic.featureflag.FeatureFlagService;

@RestController
@RequestMapping("/api/flags")
public class FeatureFlagController {

	private final FeatureFlagService service;

	public FeatureFlagController(FeatureFlagService service) {
		this.service = service;
	}

	@PostMapping
	public FeatureFlag createOrUpdate(@RequestBody FeatureFlag flag) {
		return service.save(flag);
	}

	@GetMapping
	public List<FeatureFlag> getAll() {
		return service.findAll();
	}

	@DeleteMapping("/{key}")
	public void delete(@PathVariable String key) {
		service.deleteByKey(key);
	}
}
