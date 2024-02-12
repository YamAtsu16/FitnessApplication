package com.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.model.BodyWeightModel;

@Repository
public interface BodyWeightRepository extends JpaRepository<BodyWeightModel, Integer>{
	
	/** "date"の降順でソートし、全件取得するメソッド */
	public List<BodyWeightModel> findAllByOrderByDate();
	
    /** 更新 */
    @Override
    <S extends BodyWeightModel> S save(S updateData);
	
	/** 削除 */
	public void deleteById(Integer id);
}
