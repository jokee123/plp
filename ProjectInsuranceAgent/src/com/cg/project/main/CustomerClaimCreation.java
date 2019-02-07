package com.cg.project.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.project.entity.PolicyEntity;
import com.cg.project.entity.Claim;
import com.cg.project.exception.CRSException;
import com.cg.project.service.CRSService;
import com.cg.project.service.CRSServiceImpl;

public class CustomerClaimCreation {
	static Logger logger = Logger.getLogger(CustomerClaimCreation.class);
	static long policyNumber = 0;
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		PropertyConfigurator.configure("resources/log4j.properties");
		Scanner scanner = null;
		int choice = 0;
		boolean choiceFlag = false;
		List<PolicyEntity> list;
		CRSService service = new CRSServiceImpl();
		try {
			list = service.getPolicies();
			System.out.println("********** Policy List **********");
			for (PolicyEntity entity : list) {
				System.out.println("Policy Number   Premium Amount  Account Number   ");
				System.out.println(entity.getPolicyNumber() + "      " + entity.getPolicyPremium() + "           "
						+ entity.getAccountNumber()); 
				System.out.println(" ");

			}
		} catch (CRSException e1) {
			System.err.println("Problem Occured while viewing policies");
		}

		do {
			scanner = new Scanner(System.in);
			System.out.println("******** Agent Insurance Claiming **********");
			System.out.println("1. create claim for the customer");
			System.out.println("2. View the claim status of the my customer's");
			System.out.println("Enter choice");
			try {
				choice = scanner.nextInt();
				choiceFlag = true;
				switch (choice) {
				case 1:

					String claimReason;
					boolean doClaimReason = false;
					boolean validateClaimReason;
					do {
						scanner = new Scanner(System.in);
						System.out.println("Enter the reason of the claim: ");
						claimReason = scanner.nextLine();
						validateClaimReason = service.CheckClaimReason(claimReason);
						if (validateClaimReason == false) {
							System.err.println("The reason should always starts with uppercase");
							doClaimReason = false;

						} else {
							doClaimReason = true;
						}
					} while (doClaimReason == false);

					if (validateClaimReason == true) {
						String accidentLocationStreet;
						boolean validateAccidentLocationStreet;
						boolean doAccidentLocationStreet = false;
						do {
							scanner = new Scanner(System.in);
							System.out.println("Enter the street of the accident: ");
							accidentLocationStreet = scanner.nextLine();
							validateAccidentLocationStreet = service
									.CheckAccidentLocationStreet(accidentLocationStreet);
							if (validateAccidentLocationStreet == false) {
								System.err.println("The Street name should always starts with uppercase");
								doAccidentLocationStreet = false;

							} else {
								doAccidentLocationStreet = true;
							}

						} while (doAccidentLocationStreet == false);

						if (validateAccidentLocationStreet) {
							String accidentCity;
							boolean validateAccidentCity;
							boolean doAccidentCity = false;
							do {
								scanner = new Scanner(System.in);
								System.out.println("Enter the city where accident occurred: ");
								accidentCity = scanner.nextLine();
								validateAccidentCity = service.CheckAccidentCity(accidentCity);
								if (validateAccidentCity == false) {
									System.err.println("The City name should always starts with uppercase");
									doAccidentCity = false;

								} else {
									doAccidentCity = true;
								}

							} while (doAccidentCity == false);
							if (validateAccidentCity) {
								String accidentState;
								boolean validateAccidentState;
								boolean doAccidentState = false;
								do {
									scanner = new Scanner(System.in);
									System.out.println("Enter the state where accident occurred: ");
									accidentState = scanner.nextLine();
									validateAccidentState = service.CheckAccidentState(accidentState);
									if (validateAccidentState == false) {
										System.err.println("The State should always starts with uppercase");
										doAccidentState = false;

									} else {
										doAccidentState = true;
									}

								} while (doAccidentState == false);
								if (validateAccidentState) {
									long accidentZip = 0;
									boolean validateAccidentZip = false;
									boolean doAccidentZip = false;
									do {
										scanner = new Scanner(System.in);
										System.out.println("Enter the postal code where accident occurred: ");
										try {
											accidentZip = scanner.nextLong();
											validateAccidentZip = service.CheckAccidentZip(accidentZip);
											if (validateAccidentZip == false) {
												System.err
														.println("The Postal code must be only digits with 5 numbers");
												doAccidentZip = false;

											} else {
												doAccidentZip = true;
											}
										} catch (InputMismatchException e) {
											System.err.println("Enter Only Digits");

										}

									} while (doAccidentZip == false);

									if (validateAccidentZip) {
										boolean selectClaimTypeFlag = false;
										String claimType = " ";
										do {
											scanner = new Scanner(System.in);
											System.out.println("Select the type which you want to claim: ");
											System.out.println("1. Annum ");
											System.out.println("2. Half Yearly");
											System.out.println("3. Quaterly");
											try {

												int selectClaimType = scanner.nextInt();
												selectClaimTypeFlag = true;
												switch (selectClaimType) {
												case 1:
													claimType = "Annum";
													break;
												case 2:
													claimType = "Half Yearly";
													break;
												case 3:

													claimType = "Quaterly";
													break;
												default:
													selectClaimTypeFlag = false;
													System.err.println("Select any options from 1 to 3");
													break;
												}
											} catch (InputMismatchException e) {
												System.err.println("Enter the valid details of integer type");
											}
										} while (!selectClaimTypeFlag);

										boolean doPolicyNumberFlag = false;
										boolean validatePolicyNumber;
										//long policyNumber = 0;

										do {
											scanner = new Scanner(System.in);
											System.out.println("Enter the policy number of your policy: ");

											try {
												policyNumber = scanner.nextLong();
												validatePolicyNumber = service.CheckPolicyNumber(policyNumber);
												if (validatePolicyNumber == false) {
													System.err.println("Enter the policy number of 10 digits");
													doPolicyNumberFlag = false;
												} else {
													doPolicyNumberFlag = true;
												}
											} catch (InputMismatchException e) {
												System.err.println("Enter only digits");
											}
										} while (!doPolicyNumberFlag);

										Claim presentation = new Claim();
										presentation.setAgentClaimReason(claimReason);
										presentation.setAgentAccidentLocationStreet(accidentLocationStreet);
										presentation.setAgentAccidentCity(accidentCity);
										presentation.setAgentAccidentState(accidentState);
										presentation.setAgentAccidentZip(accidentZip);
										presentation.setAgentClaimType(claimType);
										presentation.setAgentPolicyNumber(policyNumber);

										try {
											int rows = service.insertClaimDetails(presentation);
											System.out.println(rows + "inserted");
											System.out.println("Enter Yes or No to enter Claim Details Screen");
											scanner.nextLine();
											String claimDetails = scanner.nextLine();
											if (claimDetails.equals("Yes") || claimDetails.equals("yes")
													|| claimDetails.equals("Y") || claimDetails.equals("y")) {
												CustomerClaimDetails.main(args);
											} else if (claimDetails.equals("No") || claimDetails.equals("no")
													|| claimDetails.equals("n") || claimDetails.equals("N")) {
												System.out.println("Thank You....");
											} else {
												System.out.println("Enter only Yes or No or Y/N");
											}
											
											
											//System.out.println(rows + "inserted");
										} catch (CRSException e) {
											System.err.println("Problem occured while inserting claim details ");

										}
									}
								}
							}
						}
					}
					break;

				case 2:
					scanner.nextLine();
					System.out.println("Your Customer's Claiming History are");
					/*System.out.println("enter user name");
					String userName = scanner.nextLine();*/
					String userName="agent1";
					
					service = new CRSServiceImpl();
					List<Claim> list2;
					try {
						list2 = service.claimData(userName);

						for (Claim list3 : list2) {
							System.out.println(list3.getAgentClaimNumber() + "             "
									+ list3.getAgentClaimReason() + "           "
									+ list3.getAgentAccidentLocationStreet() + "           "
									+ list3.getAgentAccidentCity() + "           " + list3.getAgentAccidentState()
									+ "           " + list3.getAgentAccidentZip() + "    " + list3.getAgentClaimType()
									+ "       " + list3.getAgentPolicyNumber());
						}
						logger.info("All Customer claim details are displayed");
					} catch (CRSException e) {
						
						System.err.println("Problem Occured while viewing customer claim status ");
					}

					break;
				default:
					choiceFlag = false;
					System.out.println("input should be in the range of (1-2)");
					System.out.println("Enter your input again");
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Enter the valid details");
				System.out.println("Enter you input again");

			}

		} while (!choiceFlag);

	}
}
