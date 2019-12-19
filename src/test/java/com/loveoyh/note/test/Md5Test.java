package com.loveoyh.note.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Md5Test {
	
	@Test
	public void testMd5(){
		String str = "123456";
		String md5 = DigestUtils.md5Hex(str);
		System.out.println(md5);
		
		/*
		 * 加盐摘要
		 */
		String salt = "今天你吃了吗?";
		String amd5 = DigestUtils.md5Hex(salt+str);
		System.out.println(amd5);
		
	}
	
}
