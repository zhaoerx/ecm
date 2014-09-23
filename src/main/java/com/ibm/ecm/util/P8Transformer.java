package com.ibm.ecm.util;

import org.apache.commons.collections.Transformer;

import com.filenet.api.core.Document;
import com.filenet.api.core.Folder;
import com.ibm.ecm.model.ItemType;

public class P8Transformer implements Transformer {

	@Override
	public Object transform(Object input) {
		if(input instanceof Folder) {
			com.ibm.ecm.model.Folder item = new com.ibm.ecm.model.Folder();
			Folder f = (Folder) input;
			item.setId(f.get_Id().toString());
			item.setLastModifiedDate(f.get_DateLastModified());
			item.setName(f.get_Name());
			item.setSize("-");
			item.setType(ItemType.FOLDER);
			item.setPath(f.get_PathName());
			return item;
		} else {
			com.ibm.ecm.model.Document item = new com.ibm.ecm.model.Document();
			Document d = (Document) input;
			item.setId(d.get_Id().toString());
			item.setLastModifiedDate(d.get_DateLastModified());
			item.setName(d.get_Name());
			if(d.get_ContentSize() != null)
				item.setSize(d.get_ContentSize().toString());
			item.setType(ItemType.DOCUMENT);
			return item;
		}
	}

}
