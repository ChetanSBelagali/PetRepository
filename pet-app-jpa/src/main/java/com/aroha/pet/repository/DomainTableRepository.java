package com.aroha.pet.repository;

import java.util.HashMap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aroha.pet.model.Domain;
import com.aroha.pet.model.Question;


@Repository
public interface DomainTableRepository extends JpaRepository<Domain, Integer> {
	
	@Query(value="SELECT a.domain_name, a.function_name, a.scenario_title, b.question_desc FROM \r\n" + 
			"(SELECT d.id ,d.domain_name, f.function_name, s.scenario_title,s.id AS sid  \r\n" + 
			"FROM domain d LEFT JOIN function f ON d.id = f.domain_id  left JOIN scenario s ON f.id = s.function_id)a,\r\n" + 
			"( SELECT q.scenario_id AS sid ,q.question_desc FROM question q )b\r\n" + 
			"WHERE a.sid=b.sid;",nativeQuery = true)
	public List getDomainData();

}


/*
@Repository
public interface DomainTableRepository extends JpaRepository<Question, Integer>{
	
	@Query("select new com.aroha.pet.payload.DomainTable(d.domainName,f.functionName,s.scenarioTitle, "
			+ "q.questionDesc) from select new com.aroha.pet.payload.DomainTable(d.domainName,f.functionName,"
			+ "s.scenarioTitle,q.questionDesc)from Domain as d left join Function as f on d.id=f.")
	public List<DomainTable> getDomainData();
}
*/
