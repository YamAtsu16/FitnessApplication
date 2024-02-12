package com.fitness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.model.BodyWeightModel;
import com.fitness.repository.BodyWeightRepository;

@Service
public class BodyWeightService {

	@Autowired
	private BodyWeightRepository bodyWeightRepository;
	
	/**
	 * データ全件取得
	 * @return 全件データ
	 */
	public List<BodyWeightModel> getAll() {
		return bodyWeightRepository.findAllByOrderByDate();
	}
	
	/**
	 * データ更新
	 * @return 更新件数
	 */
	public int update(BodyWeightModel updateData) {
	    try {
	        bodyWeightRepository.save(updateData);
	        return 1;  // 更新成功
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;  // 更新失敗
	    }
	}
	
	/**
	 * データ削除
	 * @return 削除件数
	 */
	public int delete(Integer id) {
	    try {
	        bodyWeightRepository.deleteById(id);
	        return 1;  // 削除成功
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;  // 削除失敗
	    }
	}
}
