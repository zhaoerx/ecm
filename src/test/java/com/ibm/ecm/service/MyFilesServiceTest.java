package com.ibm.ecm.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.filenet.api.core.Folder;
import com.ibm.ecm.model.Item;
import com.ibm.filenet.helper.ce.ObjectStoreProvider;

import filenet.vw.base.logging.Logger;


public class MyFilesServiceTest {
	private Logger log = Logger.getLogger(getClass());
	private MyFilesService service = new MyFilesService();
	
	@Test
	public void testGetMyFiles() {
		ObjectStoreProvider osp = new ObjectStoreProvider();
		List<Item> list = service.getContainees(osp, "/2 部门库");
		for(Item item :list) {
			log.debug(item.toString());
		}
		Assert.assertTrue(list.size()>0);
	}

}
