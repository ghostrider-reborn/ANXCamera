package android.os.statistics;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.ss.android.ugc.effectmanager.EffectConfiguration;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class E2EScenario implements Parcelable, Serializable {
    public static final Parcelable.Creator<E2EScenario> CREATOR = new Parcelable.Creator<E2EScenario>() {
        public E2EScenario createFromParcel(Parcel source) {
            return new E2EScenario(source);
        }

        public E2EScenario[] newArray(int size) {
            return new E2EScenario[size];
        }
    };
    public static final String DEFAULT_CATEGORY = "default";
    private static final long serialVersionUID = 1;
    public String category;
    public String name;
    public String namespace;

    protected E2EScenario() {
        this.namespace = "";
        this.category = "";
        this.name = "";
    }

    public E2EScenario(String namespace2, String category2, String name2) {
        this.namespace = namespace2 == null ? "" : namespace2;
        this.category = category2 == null ? "" : category2;
        this.name = name2 == null ? "" : name2;
    }

    public E2EScenario(Parcel source) {
        String _namespace = source.readString();
        String _category = source.readString();
        String _name = source.readString();
        this.namespace = _namespace == null ? "" : _namespace;
        this.category = _category == null ? "" : _category;
        this.name = _name == null ? "" : _name;
    }

    public void normalize(String packageName) {
        if (TextUtils.isEmpty(this.namespace)) {
            this.namespace = TextUtils.isEmpty(packageName) ? "android" : packageName;
        }
        if (TextUtils.isEmpty(this.category)) {
            this.category = DEFAULT_CATEGORY;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof E2EScenario)) {
            return false;
        }
        E2EScenario other = (E2EScenario) obj;
        if (!this.namespace.equals(other.namespace) || !this.category.equals(other.category) || !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.namespace.hashCode() + this.category.hashCode() + this.name.hashCode();
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.name);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namespace);
        dest.writeString(this.category);
        dest.writeString(this.name);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("namespace", this.namespace);
            json.put(EffectConfiguration.KEY_CATEGORY, this.category);
            json.put("name", this.name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
