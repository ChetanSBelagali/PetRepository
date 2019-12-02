package com.aroha.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aroha.pet.model.QueryInfo;
import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<QueryInfo, Long> {

    @Query(value = "SELECT q.created_by,u.name,"
            + "         q.created_at as date,COUNT(q.exception_str) as no_of_error ,\n"
            + "			COUNT(DISTINCT q.scenario ) as no_of_question, COUNT(q.sql_str) as no_of_answer ,\n"
            + "			CAST(((count(q.exception_str))/(count(q.sql_str)))*100 as decimal(5,2))as productivity\n"
            + "			FROM users u LEFT JOIN query_info q ON u.id=q.created_by \n"
            + "			left join user_roles r on u.id=r.user_id WHERE r.role_id=1\n"
            + "			GROUP BY u.id,DAY(q.created_at)\n"
            + "			having count(distinct q.scenario)>0;\n"
            + "\n"
            + "", nativeQuery = true)
    public List getFeedBackStatus();

    /*	
	
	@Query(value="select distinct scenario,sql_str,result_Str,exception_str from users u,query_info q where u.id=q.created_by and q.created_by=?1 "
			+ "and DATE(q.created_at)=DATE(?2)",nativeQuery = true)
	public List showAnalysis(long created_by,String createdAt);
     */
 /*	
	@Query("select new com.aroha.pet.model.QueryInfo(q.scenario,q.sqlStr,q.exceptionStr)"
			+ "from User u,QueryInfo q where u.id=q.createdBy and q.createdBy=?1 and DATE(q.createdAt)=DATE(?2)")
	public List<QueryInfo> showAnalysis(long created_by,String createdAt);

     */
    @Query(value = "SELECT * FROM query_info q LEFT JOIN users u ON  q.created_by=u.id LEFT JOIN question q1 ON  q.question_id=q1.id\n"
            + "WHERE q.created_by=?1 AND DATE(q.created_at)=DATE(?2)", nativeQuery = true)
    public List getReport(long created_by, String createdAt);
}
