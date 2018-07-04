package model;

import com.ardoq.model.Component;

public class ArdoqComponent extends Component {
    public ArdoqComponent(String name, String rootWorkspace, String description, String typeId) {
        super(name, rootWorkspace, description, typeId);
    }

    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Component toComponent() {
        final Component component = new Component(this.getName(), this.getRootWorkspace(), this.getDescription(), this.getTypeId());
        if (this.getFields() != null)
            component.setFields(this.getFields());
        if (this.getType() != null)
            component.setType(this.getType());
        if (this.getId() != null)
            component.setId(this.getId());
        if (this.get_version() != null)
            component.set_version(this.get_version());
        if (this.getChildren() != null)
            component.setChildren(this.getChildren());
        if (this.getCreated() != null)
            component.setCreated(this.getCreated());
        if (this.getCreatedBy() != null)
            component.setCreatedBy(this.getCreatedBy());
        if (this.getLastUpdated() != null)
            component.setLastUpdated(this.getLastUpdated());
        if (this.getModel() != null)
            component.setModel(this.getModel());
        if (this.getParent() != null)
            component.setParent(this.getParent());
        if (this.getState() != null)
            component.setState(this.getState());
        return component;
    }
}
