package repository;

import com.ardoq.ArdoqClient;
import com.ardoq.model.Component;
import com.ardoq.model.Reference;
import com.ardoq.service.ComponentService;
import com.ardoq.service.ReferenceService;

import java.util.List;
import java.util.Map;

public final class ArdoqRepositoryImpl implements ArdoqRepository {

    private ArdoqClient ardoqClient;
    private ComponentService componentService;
    private ReferenceService referenceService;

    public ArdoqRepositoryImpl() {
        this.ardoqClient = new ArdoqClient("https://app.ardoq.com", "yourApiToken");
        this.componentService = this.ardoqClient.component();
        this.referenceService = this.ardoqClient.reference();
    }

    @Override
    public Component getComponent(final String id) {
        return this.componentService.getComponentById(id);
    }

    @Override
    public List<Component> findComponent(final Map<String, String> query) {
        return this.componentService.findComponentsByFields(query);
    }

    @Override
    public Component updateComponent(final String id, final Component component) {
        return this.componentService.updateComponent(id, component);
    }

    @Override
    public Component createComponent(final Component component) {
        return this.componentService.createComponent(component);
    }

    @Override
    public void deleteComponent(final String id) {
        this.componentService.deleteComponent(id);
        return;
    }

    @Override
    public Reference getReference(final String id) {
        return this.referenceService.getReferenceById(id);
    }

    @Override
    public Reference createReference(final Reference reference) {
        return this.referenceService.createReference(reference);
    }

    @Override
    public Reference updateReference(final String id, final Reference reference) {
        return this.referenceService.updateReference(id, reference);
    }

    @Override
    public void deleteReference(final String id) {
        this.referenceService.deleteReference(id);
        return;
    }
}
