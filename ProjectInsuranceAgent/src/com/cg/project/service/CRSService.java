package com.cg.project.service;

import java.util.List;

import com.cg.project.entity.CustomerClaimDetailsEntity;
import com.cg.project.entity.PolicyEntity;
import com.cg.project.entity.Claim;
import com.cg.project.exception.CRSException;

public interface CRSService {

	int insertClaimDetails(Claim presentation) throws CRSException;

	boolean CheckClaimReason(String claimReason);

	boolean CheckAccidentLocationStreet(String accidentLocationStreet);

	boolean CheckAccidentCity(String accidentCity);

	boolean CheckAccidentState(String accidentState);

	boolean CheckAccidentZip(long accidentZip);

	boolean CheckPolicyNumber(long policyNumber);

	List<Claim> claimData(String userName) throws CRSException;

	List<PolicyEntity> getPolicies()throws CRSException;

	List<CustomerClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException;

	int insertQuestions(long policyNumber, String claimQuesId, String claimAns) throws CRSException;


}
