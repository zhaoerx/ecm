package com.ibm.ecm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import com.filenet.api.collection.EngineCollection;
import com.ibm.ecm.model.Item;

public class ItemCollectionUtil {

	public static List<Item> collect(EngineCollection ec,
			Transformer transformer) {
		return collect(ec, transformer, null);
	}

	public static List<Item> collect(EngineCollection ec,
			Transformer transformer, List<Item> outputList) {
		if (outputList == null)
			outputList = new ArrayList<Item>();
		if (ec != null && !ec.isEmpty()) {
			CollectionUtils.collect(ec.iterator(), transformer, outputList);
		}
		return outputList;
	}

}
