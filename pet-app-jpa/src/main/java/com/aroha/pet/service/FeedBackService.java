package com.aroha.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aroha.pet.model.QueryInfo;
import com.aroha.pet.model.Question;
import com.aroha.pet.payload.FeedBackPayload;
import com.aroha.pet.payload.FeedBackScenario;
import com.aroha.pet.repository.FeedBackRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
public class FeedBackService {

	@Autowired
	private FeedBackRepository fedRepo;

	public List getData(){
		return fedRepo.getFeedBackStatus();
	}

/*
	public HashSet<FeedBackPayload> showAnalysis(long created_by,String createdAt){
		ArrayList<QueryInfo>list=(ArrayList<QueryInfo>) fedRepo.showAnalysis(created_by,createdAt);
		HashSet<FeedBackPayload> list2=new HashSet<FeedBackPayload>();
		Iterator<QueryInfo>itr=list.iterator(); 
		while(itr.hasNext()) {
			QueryInfo query=itr.next();
			FeedBackPayload obj=new FeedBackPayload();
			obj.setScenario(query.getScenario());
			obj.setSqlStr(query.getSqlStr());
			obj.setExceptionStr(query.getExceptionStr());
			list2.add(obj);
		}
		return list2;
	}
}
*/

	/*

	public HashSet<FeedBackScenario> showAnalysis(long created_by,String createdAt){
		ArrayList<QueryInfo>list=(ArrayList<QueryInfo>) fedRepo.showAnalysis(created_by,createdAt);
		//Iterator<QueryInfo>itr=list.iterator();
		HashSet<FeedBackScenario>set=new HashSet<FeedBackScenario>();
		//HashMap<String,FeedBackScenario>map=new HashMap<String,FeedBackScenario>();
		/*	while(itr.hasNext()) {
			QueryInfo query=itr.next();
			FeedBackScenario fScenario=new FeedBackScenario();
			fScenario.setScenario(query.getScenario());
			FeedBackPayload fload=new FeedBackPayload();
			fload.setSqlStr(query.getSqlStr());
			fload.setExceptionStr(query.getExceptionStr());
			fScenario.getFeedback().add(fload);
			set.add(fScenario);
			//map.put(query.getScenario(), fScenario);

		}*/
		
/*	
		FeedBackScenario fScenario=null;
		for(int i=0;i<list.size();i++) {
			QueryInfo query=list.get(i);
			for(int j=i+1;j<list.size();j++) {
				QueryInfo query2=list.get(j);
				if(query.getScenario().equals(query2.getScenario())) {
					fScenario=new FeedBackScenario();
					fScenario.setScenario(query2.getScenario());
					FeedBackPayload fload=new FeedBackPayload();
					fload.setSqlStr(query.getSqlStr());
					fload.setExceptionStr(query.getExceptionStr());
					fScenario.getFeedback().add(fload);
				}
			}
			set.add(fScenario);
		}
		return set;

	}
	*/

public HashMap<FeedBackScenario,FeedBackPayload>showAnalysis(long created_by,String createdAt){
    List list=fedRepo.getReport(created_by,createdAt);
    HashMap<FeedBackScenario,FeedBackPayload> map=new HashMap<FeedBackScenario,FeedBackPayload>();
    Iterator<QueryInfo>itr=list.iterator();
    while(itr.hasNext()){
        QueryInfo query=itr.next();
        FeedBackPayload load=new FeedBackPayload();
        System.out.println("Scenario is: "+query.getScenario());
        System.out.println("Learner result : "+query.getResultStr());
    }
    
    return map;
}


}