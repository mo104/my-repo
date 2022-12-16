public class Hotelrooms {
	private int roomID;
	private String roomType;
	private double roomPrice;
	private boolean hasBalcony;
	private boolean hasLounge;
	private String occupant;
	int roomScore;

	public String toString() {
		return String.format("Reservation:\n\nRoomID: %d\nRoomType: %s\nPrice: %s\nBalcony: %b\nLounge: %b\nOccupant: %s", getRoomID(), getRoomType(), getRoomPrice(), isHasBalcony(), isHasLounge(), getOccupant());
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public boolean isHasBalcony() {
		return hasBalcony;
	}

	public void setHasBalcony(boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public boolean isHasLounge() {
		return hasLounge;
	}

	public void setHasLounge(boolean hasLounge) {
		this.hasLounge = hasLounge;
	}

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getOccupant() {
		return occupant;
	}

	public void setOccupant(String occupant) {
		this.occupant = occupant;
	}
}
