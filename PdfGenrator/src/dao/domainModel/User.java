package dao.domainModel;

public class User {
	private String name;
	private String firstName;
	private int level;
	private String levelUrl;
	private String privateUrlLogo;
	private boolean customLogo=false;

	public User(String name,String firstName,int level,String privateLogoUrl){
		this.name=name;
		this.firstName=firstName;
		this.level=level;
		this.levelUrl=new String("http://www.blackbeltfactory.com/imgs/level/");
		switch(level){
		case 0: 
			this.levelUrl+= "belt0_L.png";
			break;
		case 1: 
			this.levelUrl+= "belt1_L.png";
			break;
		case 2: 
			this.levelUrl+= "belt2_L.png";
			break;
		case 3: 
			this.levelUrl+= "belt3_L.png";
			break;
		case 4: 
			this.levelUrl+= "belt4_L.png";
			break;
		case 5: 
			this.levelUrl+= "belt5_L.png";
			break;
		case 6: 
			this.levelUrl+= "belt6_L.png";
			break;
		default :
			this.levelUrl+= "belt0_L.png";
			break;
		}
		if(privateLogoUrl!=null){
			this.privateUrlLogo=privateLogoUrl;
			this.customLogo=true;
		}
		else{
			this.privateUrlLogo="http://www.blackbeltfactory.com/imgs/logos/BlackBeltFactory-logo-200x127.png";
		}
	}
	
	public String getName(){
		return this.name;
	}
	public String getFirstName(){
		return this.firstName;
	}
	public int getLevel(){
		return this.level;
	}
	public String getLevelUrl(){
		return this.levelUrl;
	}
	public String getLogoUrl(){
		return this.privateUrlLogo;
	}
	public boolean useCustomLogo(){
		return this.customLogo;
	}
	public void changeTheValueOfTheBooleanOfTheUseCustomLogo(boolean bool){
		this.customLogo=bool;
	}
}
