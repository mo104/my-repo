public class Userpreferences {
	private String userRoomtypes;
	private double userRoomprices;
	private boolean userHasbalcony;
	private boolean userHaslounge;
	private String userEmail;
	String toString;

	public String toString() {
		return String.format("Reservation:\n\nRoomType: %s\nPrice: %s\nBalcony: %b\nLounge: %b", getUserRoomtypes(), getUserRoomprices(), isUserHasbalcony(), isUserHaslounge(), getUserEmail());
	}

	public String getUserRoomtypes() {
		return userRoomtypes;
	}

	public void setUserRoomtypes(String userRoomtypes) {
		this.userRoomtypes = userRoomtypes;
	}

	public double getUserRoomprices() {
		return userRoomprices;
	}

	public void setUserRoomprices(double userRoomprices) {
		this.userRoomprices = userRoomprices;
	}

	public boolean isUserHasbalcony() {
		return userHasbalcony;
	}

	public void setUserHasbalcony(boolean userHasbalcony) {
		this.userHasbalcony = userHasbalcony;
	}

	public boolean isUserHaslounge() {
		return userHaslounge;
	}

	public void setUserHaslounge(boolean userHaslounge) {
		this.userHaslounge = userHaslounge;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}




