package model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class User implements Parcelable {
    private Date birthdate;
    private String firstname;
    private String lastname;
    private String username;
    private String profileImagePath;
    private ArrayList<Category> Interests;

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public ArrayList<Category> getInterests() {
        return Interests;
    }

    public void setInterests(ArrayList<Category> interests) {
        Interests = interests;
    }
    public int getAge()
    {
        Date now = new Date();
        int age = now.getYear() - birthdate.getYear();
        if(now.getMonth() < birthdate.getMonth() || (now.getMonth() == birthdate.getMonth() && now.getDay() < birthdate.getDay()))
        {
            age--;
        }
        return age;
    }
    public User()
    {

    }
    protected User(Parcel in) {
        long tmpBirthdate = in.readLong();
        birthdate = tmpBirthdate != -1 ? new Date(tmpBirthdate) : null;
        firstname = in.readString();
        lastname = in.readString();
        username = in.readString();
        profileImagePath = in.readString();
        if (in.readByte() == 0x01) {
            Interests = new ArrayList<Category>();
            in.readList(Interests, Category.class.getClassLoader());
        } else {
            Interests = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(birthdate != null ? birthdate.getTime() : -1L);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(username);
        dest.writeString(profileImagePath);
        if (Interests == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Interests);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
