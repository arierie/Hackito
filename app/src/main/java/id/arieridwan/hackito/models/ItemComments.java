package id.arieridwan.hackito.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by arieridwan on 24/06/2017.
 */

public class ItemComments implements Parcelable {

    private String by;
    private int id;
    private int parent;
    private String text;
    private int time;
    private String type;
    private List<Integer> kids;
    private boolean deleted = false;

    protected ItemComments(Parcel in) {
        by = in.readString();
        id = in.readInt();
        parent = in.readInt();
        text = in.readString();
        time = in.readInt();
        type = in.readString();
        deleted = in.readByte() != 0;
    }

    public ItemComments() {

    }

    public static final Creator<ItemComments> CREATOR = new Creator<ItemComments>() {
        @Override
        public ItemComments createFromParcel(Parcel in) {
            return new ItemComments(in);
        }

        @Override
        public ItemComments[] newArray(int size) {
            return new ItemComments[size];
        }
    };

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(by);
        parcel.writeInt(id);
        parcel.writeInt(parent);
        parcel.writeString(text);
        parcel.writeInt(time);
        parcel.writeString(type);
        parcel.writeByte((byte) (deleted ? 1 : 0));
    }
}
