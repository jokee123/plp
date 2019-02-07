package com.cg.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.project.entity.CustomerClaimDetailsEntity;
import com.cg.project.entity.PolicyEntity;
import com.cg.project.entity.Claim;
import com.cg.project.exception.CRSException;
import com.cg.project.jdbc.JDBCUtility;

public class CRSDaoImpl implements CRSDao {
	Connection connection = null;

	PreparedStatement statement = null;
	ResultSet resultSet = null;
	static Logger logger = Logger.getLogger(CRSDaoImpl.class);

	@Override
	public int insertClaimDetails(Claim presentation) throws CRSException {
		logger.info("in add Customer method");
		/**
		 * method : addCustomerDetails argument : it's taking model object as an
		 * argument return type : this method return the generated id to the user Author
		 * : Capgemini Date : 04 - Feb - 2019
		 * 
		 **/

		connection = JDBCUtility.getConnection();
		logger.info("connection object created");

		int generatedId = 0;

		try {

			statement = connection.prepareStatement(QueryMapper.insertClaimDetails);
			logger.debug("statement object created");

			statement.setString(1, presentation.getAgentClaimReason());
			statement.setString(2, presentation.getAgentAccidentLocationStreet());
			statement.setString(3, presentation.getAgentAccidentCity());
			statement.setString(4, presentation.getAgentAccidentState());
			statement.setLong(5, presentation.getAgentAccidentZip());
			statement.setString(6, presentation.getAgentClaimType());
			statement.setLong(7, presentation.getAgentPolicyNumber());

			generatedId = statement.executeUpdate();

			logger.info("execute update called");

		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the statement object");
		}
		return generatedId;
	}

	@Override
	public List<Claim> claimData(String userName) throws CRSException {
		connection = JDBCUtility.getConnection();
		List<Claim> list = new ArrayList<>();
		try {
			statement = connection.prepareStatement(QueryMapper.viewClaimHistory);
			statement.setString(1, userName);
			logger.info("connection created");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Long agentClaimNumber = resultSet.getLong(1);
				String agentClaimReason = resultSet.getString(2);
				String agentAccidentLocationStreet = resultSet.getString(3);
				String agentAccidentCity = resultSet.getString(4);
				String agentAccidentState = resultSet.getString(5);
				Long agentAccidentZip = resultSet.getLong(6);
				String agentClaimType = resultSet.getString(7);
				Long agentPolicyNumber = resultSet.getLong(8);
				Claim presentation = new Claim();
				presentation.setAgentClaimNumber(agentClaimNumber);
				presentation.setAgentClaimReason(agentClaimReason);
				presentation.setAgentAccidentLocationStreet(agentAccidentLocationStreet);
				presentation.setAgentAccidentCity(agentAccidentCity);
				presentation.setAgentAccidentState(agentAccidentState);
				presentation.setAgentAccidentZip(agentAccidentZip);
				presentation.setAgentClaimType(agentClaimType);
				presentation.setAgentPolicyNumber(agentPolicyNumber);

				list.add(presentation);
				
			}
			logger.info("Claim History viewed successfully");
		} catch (SQLException e) {
			throw new CRSException("statement problem occured while creating statement object");

		}

		return list;
	}

	@Override
	public List<PolicyEntity> getPolicies() throws CRSException {
		List<PolicyEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		try {
			statement = connection.prepareStatement(QueryMapper.getPolicies);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long policyNumber = resultSet.getLong(1);
				double policyPremium = resultSet.getDouble(2);
				long accountNumber = resultSet.getLong(3);
				PolicyEntity policy = new PolicyEntity();
				policy.setPolicyNumber(policyNumber);
				policy.setPolicyPremium(policyPremium);
				policy.setAccountNumber(accountNumber);
				list.add(policy);

			}
		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the statement object");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}
		return list;
	}

	@Override
	public List<CustomerClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException {
		List<CustomerClaimDetailsEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();

		try {
			statement = connection.prepareStatement(QueryMapper.getCustomerClaimQuestionId);
			statement.setLong(1, policyNumber);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String claimQuesId = resultSet.getString(1);
				int claimQuesSeq = resultSet.getInt(2);
				String busSeqId = resultSet.getString(3);
				String claimQuesDesc = resultSet.getString(4);
				String claimQuesAns1 = resultSet.getString(5);
				int claimQues1Weightage = resultSet.getInt(6);
				String claimQuesAns2 = resultSet.getString(7);
				int claimQues2Weightage = resultSet.getInt(8);
				String claimQuesAns3 = resultSet.getString(9);
				int claimQues3Weightage = resultSet.getInt(10);
				String claimQuesAns4 = resultSet.getString(11);
				int claimQues4Weightage = resultSet.getInt(12);
				CustomerClaimDetailsEntity claimDetailsEntity = new CustomerClaimDetailsEntity();
				claimDetailsEntity.setClaimQuesId(claimQuesId);
				claimDetailsEntity.setClaimQuesSeq(claimQuesSeq);
				claimDetailsEntity.setBusSeqId(busSeqId);
				claimDetailsEntity.setClaimQuesDesc(claimQuesDesc);
				claimDetailsEntity.setClaimQuesAns1(claimQuesAns1);
				claimDetailsEntity.setClaimQuesAns1Weightage(claimQues1Weightage);
				claimDetailsEntity.setClaimQuesAns2(claimQuesAns2);
				claimDetailsEntity.setClaimQuesAns2Weightage(claimQues2Weightage);
				claimDetailsEntity.setClaimQuesAns3(claimQuesAns3);
				claimDetailsEntity.setClaimQuesAns3Weightage(claimQues3Weightage);
				claimDetailsEntity.setClaimQuesAns4(claimQuesAns4);
				claimDetailsEntity.setClaimQuesAns4Weightage(claimQues4Weightage);
				list.add(claimDetailsEntity);
			}

		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the statement object");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}

		return list;
	}

	@Override
	public int insertQuestions(long policyNumber, String claimQuesId, String claimAns) throws CRSException {
		connection = JDBCUtility.getConnection();
		int rows = 0;
		try {
			statement = connection.prepareStatement(QueryMapper.insertQuestion);
			statement.setLong(1, policyNumber);
			statement.setString(2, claimQuesId);
			statement.setString(3, claimAns);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the statement object");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}

		return rows;
	}

}
