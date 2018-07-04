package repository;

import com.ardoq.model.Component;
import com.ardoq.model.Reference;

import java.util.List;
import java.util.Map;

public interface ArdoqRepository {
    // Component operations
    public Component getComponent(String id);

    public List<Component> findComponent(Map<String, String> query);

    public Component updateComponent(String id, Component component);

    public Component createComponent(Component component);

    public void deleteComponent(String id);

    // Reference operations
    public Reference getReference(String id);

    public Reference createReference(Reference reference);

    public Reference updateReference(String id, Reference reference);

    public void deleteReference(String id);
}
