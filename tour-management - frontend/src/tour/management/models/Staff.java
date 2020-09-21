package tour.management.models;


public class Staff {

	private int id;
	private String firstName;
	private Integer type;
	private String lastName;
	private String username;
	private String password;
	private String address;
	private Boolean status;
	private int postCode;
	private static int nextID = 1;

	public Staff(String firstName, Integer type, String lastName, String address, int postCode, String username, String password) {
		this.firstName = firstName;
		this.type = type;
		this.lastName = lastName;
		this.address = address;
		this.postCode = postCode;
		this.username = username;
		this.password = password;
		this.status = true;
		this.id = nextID++;
	}

	// Defining the getter and setter for all the variables

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Staff{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", type='" + (type == 1 ? "Admin" : "Assistant") + '\'' +
				", lastName='" + lastName + '\'' +
				", address='" + address + '\'' +
				", postCode=" + postCode +
				", username=" + username +
				", password=" + password +
				", status=" + status +
				'}';
	}
}
