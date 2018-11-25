package spittr.dao;

import java.util.List;

import spittr.dto.Spittle;

public interface SpittleRepository {

	List<Spittle> findRecentSpittles(int size);

	List<Spittle> findRecent();

	Spittle findOne(long id);

	Spittle save(Spittle spittle);

	long count();

}
