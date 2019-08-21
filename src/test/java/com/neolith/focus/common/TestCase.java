package com.neolith.focus.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

// This class represent the Test Case object in Unit testing
public abstract class TestCase {

	protected Map<String, Object> testConditions = new HashMap<String, Object>();
	private String testName = "";
	private Map<String, Boolean> expectedResults = new HashMap<String, Boolean>();
	private Map<String, Boolean> actualResults = new HashMap<String, Boolean>();

	public TestCase() {
	}

	// main execution point of every test case
	public boolean isTestPass() throws Exception {
		System.out.println("Test Case: " + this.getTestName());

		this.executeTest();
		this.checkActualResults();
		this.syncResults();

		boolean isMatch = this.isResultsMatch();
		System.out.println((isMatch ? "PASS: " : "FAIL: ") + this.getTestName());
		return isMatch;
	}

	// execute the method to be tested, set the parameters or conditions before
	// execute
	protected abstract void executeTest() throws Exception;

	// check actual results and store in appropriate results values, use the
	// setActualResult() method
	protected abstract void checkActualResults() throws Exception;

	// check all expected results againts actual results
	private boolean isResultsMatch() {
		Iterator<Entry<String, Boolean>> i = expectedResults.entrySet().iterator();
		Map.Entry<String, Boolean> result;
		while (i.hasNext()) {
			result = i.next();
			if (this.getExpectedResult(result.getKey()) != this.getActualResult(result.getKey()))
				return false;
		}
		return true;
	}

	// this to ensure that all result entries are compared
	private void syncResults() {
		int exp = expectedResults.size();
		int act = actualResults.size();
		if (exp == act)
			return;
		Map.Entry<String, Boolean> result;
		if (exp > act) {
			Iterator<Entry<String, Boolean>> i = expectedResults.entrySet().iterator();
			while (i.hasNext()) {
				result = i.next();
				if (!actualResults.containsKey(result.getKey())) {
					actualResults.put(result.getKey(), false);
				}
			}
		} else {
			Iterator<Entry<String, Boolean>> i = actualResults.entrySet().iterator();
			while (i.hasNext()) {
				result = i.next();
				if (!expectedResults.containsKey(result.getKey())) {
					expectedResults.put(result.getKey(), false);
				}
			}
		}
	}

	// reset all test conditions, and test results
	public void initialize() {
		testConditions.clear();
		expectedResults.clear();
		actualResults.clear();
	}

	// set conditions to specific value
	public void setAllConditions(Object value) {
		this.setMapValue(this.testConditions, value);
	}

	// set expected results to specific value
	public void setAllExpectedResults(boolean value) {
		this.setMapValue(this.expectedResults, value);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setMapValue(Map m, Object value) {
		Iterator i = m.entrySet().iterator();
		Map.Entry<String, Object> entry;
		while (i.hasNext()) {
			entry = (Map.Entry<String, Object>) i.next();
			m.put(entry.getKey(), value);
		}
	}

	// setters & getters
	public void setTestCondition(String key, Object value) {
		testConditions.put(key, value);
	}

	public Object getTestCondition(String key) {
		return testConditions.get(key);
	}

	public void setExpectedResult(String key, Boolean value) {
		expectedResults.put(key, value);
	}

	public Boolean getExpectedResult(String key) {
		return expectedResults.get(key);
	}

	public void setActualResult(String key, Boolean value) {
		actualResults.put(key, value);
	}

	public Boolean getActualResult(String key) {
		return actualResults.get(key);
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}



}
