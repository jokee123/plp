package com.cg.project.dao;

public interface QueryMapper {

	public static final String insertClaimDetails = "insert into claim values(claim_number_seq.nextval,?,?,?,?,?,?,?)";
	public static final String getPolicies = "select * from policy";
	public static final String getCustomerClaimQuestionId = "select * from claim_questions where bus_seg_id=(select bus_seg_id from accounts where account_number=(select account_number from policy where policy_number=?))";
	public static final String insertQuestion = "insert into policy_details values(?,?,?)";
	//public static String viewClaimHistory = "Select  * from claim where policy_number=(select policy_number from policy where account_number=(select account_number from accounts where username=?))";
	public static final String viewClaimHistory = " select c.claim_number,c.claim_reason,c.accident_street,c.accident_city,c.accident_state,c.accident_zip,c.claim_type,c.policy_number from claim c,policy p,accounts a,user_role u where u.username=a.username and a.account_number=p.account_number and p.policy_number=c.policy_number and u.username=?";
}
