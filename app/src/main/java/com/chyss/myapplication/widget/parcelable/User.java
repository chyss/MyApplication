package com.chyss.myapplication.widget.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable 序列化对象
 *
 *  1）、实现Parcelable接口。
 *  2）、重写writeToParcel方法，该方法主要用于将对象的序列化数据存储到Parcel对象中。Parcel封装了一系列的write和read方法用于序列化和反序列化。
 *  3）、重写describeContents方法，该方法默认返回0即可。（当存在文件描述符时返回1）。
 *  4）、实例化静态内部对象CREATOR实现接口Parcelable.Creator。用于反序列化。
 *
 * @author chyss 2018-01-29
 */
public class User implements Parcelable
{
    private String username;
    private int age;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(age);
    }

    private User(Parcel parcel) {

        username = parcel.readString();
        age = parcel.readInt();
    }
}
