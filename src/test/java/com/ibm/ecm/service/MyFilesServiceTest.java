package com.ibm.ecm.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.filenet.api.core.Folder;
import com.ibm.filenet.helper.ce.ObjectStoreProvider;


public class MyFilesServiceTest {
	
	private MyFilesService service = new MyFilesService();
	
	@Test
	public void testGetMyFiles() {
		ObjectStoreProvider osp = new ObjectStoreProvider();
		List<Folder> list = service.getContainees(osp);
		Assert.assertTrue(list.size()>0);
	}

}
