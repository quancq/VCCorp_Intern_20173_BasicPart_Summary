package tool_bt1;

public class UserLog {
	private long logTime;
	private int numUsers;
	
	public UserLog(long logTime, int numUsers) {
		this.logTime = logTime;
		this.numUsers = numUsers;
	}
	
	public long getLogTime() {
		return logTime;
	}
	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}
	public int getNumUsers() {
		return numUsers;
	}
	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}
	
	@Override
	public String toString() {
		return "(Log time : " + logTime + ", num user : " + numUsers + ")";
	}
	
}
