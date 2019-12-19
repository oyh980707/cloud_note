package com.loveoyh.note.dao;

import com.loveoyh.note.entity.Stars;

public interface StarsDAO {
	public Stars findSatrsByUserId(String userId);
	
	public int insertStars(Stars stars);
	 
	public int updateStars(Stars stars);
}
