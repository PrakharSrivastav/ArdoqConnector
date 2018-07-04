package model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;


public final class Dataset {
    @SerializedName("_id")
    private String id;
    @SerializedName("_deleted")
    private Boolean deleted;
    @SerializedName("_updated")
    private Integer updated;

    public String getId() {
        return id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Integer getUpdated() {
        return updated;
    }

    public ArdoqComponent toComponent() {
        final Map<String, Object> fields = new HashMap<>();
        fields.put("externalId", this.id);
        fields.put("externalType", "DATASET");
        fields.put("externalSystem", "SESAM");
//        final ArdoqComponent component = new ArdoqComponent(this.id, "Your workspaceId", "SomeDescription", "Your Type Id");

        final ArdoqComponent component = new ArdoqComponent(this.id, "rootWorkspaceId", "SomeDescription", "ModelTypeId");
        component.setFields(fields);
        component.setDeleted(this.deleted);

        return component;
    }
}
