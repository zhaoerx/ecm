package com.ibm.ecm.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.filenet.api.collection.FolderSet;
import com.filenet.api.collection.ReferentialContainmentRelationshipSet;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.ibm.filenet.helper.ce.ObjectStoreProvider;
import com.ibm.filenet.helper.ce.util.EngineCollectionUtils;

@Service("myFilesService")
public class MyFilesService {
	
	protected Logger log = Logger.getLogger(getClass());
	
	
	
	public List<Folder> getContainees(ObjectStoreProvider osp) {
		Folder root = osp.getRootFolder();
		System.out.println(root.get_Id());
		FolderSet set = root.get_SubFolders();
		System.out.println(set.isEmpty());
		List<Folder> list = EngineCollectionUtils.c(set, Folder.class);
		for(Folder f : list) {
			log.debug(f.getClassName());
		}
		return list;
	}

}
