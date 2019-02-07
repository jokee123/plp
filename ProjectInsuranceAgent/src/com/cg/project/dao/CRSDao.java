package com.cg.project.dao;

import java.util.List;

import com.cg.project.entity.CustomerClaimDetailsEntity;
import com.cg.project.entity.PolicyEntity;
import com.cg.project.entity.Claim;
import com.cg.project.exception.CRSException;

public interface CRSDao {

	int insertClaimDetails(Claim presentation) throws CRSException;

	List<Claim> claimData(String userName) throws CRSException;

	List<PolicyEntity> getPolicies()throws CRSException;

	List<CustomerClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException;

	int insertQuestions(long policyNumber, String claimQuesId, String claimAns) throws CRSException;

}
