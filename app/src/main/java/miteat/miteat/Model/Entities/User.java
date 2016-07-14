package miteat.miteat.Model.Entities;

/**
 * Created by Itzik on 05/06/2016.
 */
public class User {
    private String user;
    private String password;

    private Boolean CheckBoxStart;

    public  User(String user,String password){
        this.user=user;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getCheckBoxStart() {
        return CheckBoxStart;
    }

    public void setCheckBoxStart(Boolean checkBoxStart) {
        CheckBoxStart = checkBoxStart;
    }
}
