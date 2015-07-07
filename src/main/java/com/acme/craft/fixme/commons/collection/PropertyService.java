package com.acme.craft.fixme.commons.collection;


import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class PropertyService {

	public List<String> defaultProperties() {
		List<String> properties = Lists.newArrayList("property1","property2","property3","property4");
		properties = Collections.singleton("p1");
		StringUtils.isbl
	}
	
	public boolean valid(List<String> properties) {
		if (CollectionUtils.isEmpty(properties)) {
			boolean isValid = true;
			for (String property : properties) {
				isValid = isValid && valid(property);
			}
		}
		return false;
	}

	private boolean valid(String property) {
		return property != null && !property.isEmpty();
	}
}
