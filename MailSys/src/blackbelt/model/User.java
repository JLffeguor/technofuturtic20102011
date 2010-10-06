package blackbelt.model;

public class User {
	private String pseudo;
	private String email;
	
	public User(String pseudo,String email){
		this.pseudo = pseudo;
		this.email=email;
	}
	
	
	public String getPseudo(){
		return this.pseudo;		
	}
	public String getEmail(){
		return this.email;
	}
	
	
}
