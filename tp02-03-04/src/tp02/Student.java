package tp02;

public class Student {
	private final int id;
	private String firstName;
	private String lastName;
	public Student(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		//return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
		return firstName+" "+lastName+" ("+id+")";
	}
	
	public int compareTo(Student anotherStudent) {
		if (this.getLastName().compareTo(anotherStudent.getLastName()) < 0)
			return -1;
		if (this.getLastName().compareTo(anotherStudent.getLastName()) > 0)
			return 1;
		if ((this.getLastName().compareTo(anotherStudent.getLastName()) == 0))
			if ((this.getFirstName().compareTo(anotherStudent.getFirstName()) < 0))
				return -1;
			if ((this.getFirstName().compareTo(anotherStudent.getFirstName()) > 0))
				return 1;
			if ((this.getFirstName().compareTo(anotherStudent.getFirstName()) == 0))
				if (this.getId() < anotherStudent.getId())
					return -1;
				if (this.getId() > anotherStudent.getId())
					return 1;
		return 0;
			
	}



















}
