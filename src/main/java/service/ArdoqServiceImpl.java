package service;

import com.ardoq.model.Component;
import com.ardoq.model.Reference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.ArdoqComponent;
import model.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.ArdoqRepository;
import repository.ArdoqRepositoryImpl;
import spark.Request;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ArdoqServiceImpl implements ArdoqService {

    private static Logger logger = LoggerFactory.getLogger(ArdoqServiceImpl.class);
    private static ArdoqRepository repository = new ArdoqRepositoryImpl();
    private static GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();


    @Override
    public void syncComponent(final Request request, final String componentType) {
        if (request == null || request.body() == null || request.body().isEmpty()) {
            skipCurrentAndContinue("Request is empty");
        }

        if (componentType == null || componentType.isEmpty()) {
            skipCurrentAndContinue("Component type not provided");
        }

        final List<ArdoqComponent> components = convertRequestToComponent(request, componentType);
        components.stream().forEach(e -> processComponent(e, componentType));

    }

    private List<ArdoqComponent> convertRequestToComponent(final Request request, final String componentType) {
        final List<ArdoqComponent> components;
        switch (componentType) {
            case "dataset":
                components = convertRequestToDatasetComponent(request);
                break;
            case "pipe":
                components = convertRequestToPipeComponent(request);
                break;
            case "system":
                components = convertRequestToSystemComponent(request);
                break;
            default:
                components = null;
                break;
        }
        return components;
    }

    private List<ArdoqComponent> convertRequestToDatasetComponent(final Request request) {
        final String requestJson = request.body();
        final Type listType = new TypeToken<List<Dataset>>() {}.getType();
        final List<Dataset> datasets = gson.fromJson(requestJson, listType);

        if (datasets != null && !datasets.isEmpty()) {
            return datasets.stream().map(Dataset::toComponent).collect(Collectors.toList());
        }

        return null;
    }

    private List<ArdoqComponent> convertRequestToPipeComponent(final Request request) {
        return null;
    }

    private List<ArdoqComponent> convertRequestToSystemComponent(final Request request) {
        return null;
    }

    private void processComponent(final ArdoqComponent component, final String componentType) {
        switch (componentType) {
            case "dataset":
                processDataset(component);
                break;
            case "pipe":
                processPipe(component);
                break;
            case "system":
                processSystem(component);
                break;
        }
    }

    private void processSystem(final ArdoqComponent component) {
        logger.info("processing System");
    }

    private void processPipe(final ArdoqComponent component) {
        logger.info("processing Pipe");
    }

    private void processDataset(final ArdoqComponent ardoqComponent) {
        logger.info("processing Dataset");
        // Create an api compatible component from ArdoqComponent
        Component component = ardoqComponent.toComponent();

        // find criteria
        final Map<String, String> fields = new HashMap<>();
        fields.put("externalType", "DATASET");
        fields.put("externalSystem", "SESAM");
        fields.put("externalId", ardoqComponent.getName());
        fields.put("name", ardoqComponent.getName());
        fields.put("typeId", ardoqComponent.getTypeId());

        // Find components matching the search criteria
        final List<Component> matchingCandidates = repository.findComponent(fields);

        // If no matching candidate found then create component
        if (matchingCandidates == null || matchingCandidates.isEmpty()) {
            logger.info("Creating component {}", component.toString());
            repository.createComponent(component);
            return;
        }

        if (matchingCandidates != null && !matchingCandidates.isEmpty()) {
            final Component foundComponent = matchingCandidates
                    .stream()
                    .filter(e -> e.getRootWorkspace().equals(component.getRootWorkspace()))
                    .findFirst()
                    .get();
            // if matching component is found in the current workspace
            if (foundComponent != null) {
                logger.info("Matching component found {}", foundComponent.toString());
                // if ardoqComponent was marked for deletion, then delete
                if (ardoqComponent.getDeleted() != null && ardoqComponent.getDeleted()) {
                    logger.info("Deleting component {}", component.toString());
                    repository.deleteComponent(foundComponent.getId());
                } else {
                    component.setId(foundComponent.getId());
                    component.setModel(foundComponent.getModel());
                    component.setVersion(foundComponent.getVersion());
                    component.set_version(foundComponent.get_version());
                    component.setType(foundComponent.getType());
                    component.setDescription("Some updated description");
                    logger.info("Updating component {}", component.toString());
                    logger.info("FoundComponent is {}", foundComponent.toString());

                    repository.updateComponent(foundComponent.getId(), component);
                }
            }
        }
    }

    private Reference syncReference(final String sourceId, final String targetId, final Integer typeId) {
        if (sourceId == null || sourceId.isEmpty() || targetId == null || targetId.isEmpty() || typeId == null) {
            skipCurrentAndContinue("Reference");
            return null;
        }
        return null;
    }

    private void skipCurrentAndContinue(final String message) {
        logger.info("Invalid input. {}", message);
    }
}
