package com.neo4j.pregel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.neo4j.gds.NodeLabel;
import org.neo4j.gds.RelationshipType;
import org.neo4j.gds.api.GraphStore;
import org.neo4j.gds.beta.pregel.Partitioning;
import org.neo4j.gds.config.BaseConfig;
import org.neo4j.gds.config.SeedConfig;
import org.neo4j.gds.config.WriteConfig;
import org.neo4j.gds.core.CypherMapAccess;
import org.neo4j.gds.core.CypherMapWrapper;
import org.neo4j.gds.core.utils.progress.JobId;

@Generated("org.neo4j.gds.proc.ConfigurationProcessor")
public final class BidirectionalPathsMiningPregelConfigImpl implements BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig {
    private boolean isAsynchronous;

    private long maxRepeatNodes;

    private boolean withRepeition;

    private String mutateProperty;

    private String writeProperty;

    private Partitioning partitioning;

    private List<String> nodeLabels;

    private List<String> relationshipTypes;

    private boolean logProgress;

    private boolean sudo;

    private Optional<String> usernameOverride;

    private int concurrency;

    private JobId jobId;

    private Optional<String> relationshipWeightProperty;

    private int maxIterations;

    private Optional<WriteConfig.ArrowConnectionInfo> arrowConnectionInfo;

    private int writeConcurrency;

    private @Nullable String seedProperty;

    public BidirectionalPathsMiningPregelConfigImpl(@NotNull CypherMapAccess config) {
        ArrayList<IllegalArgumentException> errors = new ArrayList<>();
        try {
            this.isAsynchronous = config.getBool("isAsynchronous", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.isAsynchronous());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.maxRepeatNodes = config.getLong("maxRepeatNodes", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.maxRepeatNodes());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.withRepeition = config.getBool("withRepeition", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.withRepeition());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.mutateProperty = CypherMapAccess.failOnNull("mutateProperty", config.getString("mutateProperty", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.mutateProperty()));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.writeProperty = CypherMapAccess.failOnNull("writeProperty", config.getString("writeProperty", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.writeProperty()));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.partitioning = CypherMapAccess.failOnNull("partitioning", Partitioning.parse(config.getChecked("partitioning", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.partitioning(), Object.class)));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.nodeLabels = CypherMapAccess.failOnNull("nodeLabels", config.getChecked("nodeLabels", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.nodeLabels(), List.class));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.relationshipTypes = CypherMapAccess.failOnNull("relationshipTypes", config.getChecked("relationshipTypes", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.relationshipTypes(), List.class));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.logProgress = config.getBool("logProgress", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.logProgress());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.sudo = config.getBool("sudo", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.sudo());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.usernameOverride = CypherMapAccess.failOnNull("username", config.getOptional("username", String.class).map(BaseConfig::trim));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.concurrency = config.getInt("concurrency", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.concurrency());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.jobId = CypherMapAccess.failOnNull("jobId", JobId.parse(config.getChecked("jobId", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.jobId(), Object.class)));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.relationshipWeightProperty = CypherMapAccess.failOnNull("relationshipWeightProperty", config.getOptional("relationshipWeightProperty", String.class));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.maxIterations = config.requireInt("maxIterations");
            CypherMapAccess.validateIntegerRange("maxIterations", maxIterations, 0, 2147483647, true, true);
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.arrowConnectionInfo = CypherMapAccess.failOnNull("arrowConnectionInfo", config.getOptional("arrowConnectionInfo", Object.class).map(WriteConfig.ArrowConnectionInfo::parse));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.writeConcurrency = config.getInt("writeConcurrency", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.writeConcurrency());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.seedProperty = SeedConfig.validatePropertyName(config.getString("seedProperty", BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig.super.seedProperty()));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            validateConcurrency();
        } catch (IllegalArgumentException e) {
            errors.add(e);
        } catch (NullPointerException e) {
        }
        try {
            validateRelationshipWeightProperty();
        } catch (IllegalArgumentException e) {
            errors.add(e);
        } catch (NullPointerException e) {
        }
        try {
            validateWriteConcurrency();
        } catch (IllegalArgumentException e) {
            errors.add(e);
        } catch (NullPointerException e) {
        }
        if(!errors.isEmpty()) {
            if(errors.size() == 1) {
                throw errors.get(0);
            } else {
                String combinedErrorMsg = errors.stream().map(IllegalArgumentException::getMessage).collect(Collectors.joining(System.lineSeparator() + "\t\t\t\t", "Multiple errors in configuration arguments:" + System.lineSeparator() + "\t\t\t\t", ""));
                IllegalArgumentException combinedError = new IllegalArgumentException(combinedErrorMsg);
                errors.forEach(error -> combinedError.addSuppressed(error));
                throw combinedError;
            }
        }
    }

    @Override
    public boolean isAsynchronous() {
        return this.isAsynchronous;
    }

    @Override
    public long maxRepeatNodes() {
        return this.maxRepeatNodes;
    }

    @Override
    public boolean withRepeition() {
        return this.withRepeition;
    }

    @Override
    public String mutateProperty() {
        return this.mutateProperty;
    }

    @Override
    public String writeProperty() {
        return this.writeProperty;
    }

    @Override
    public Partitioning partitioning() {
        return this.partitioning;
    }

    @Override
    public void graphStoreValidation(GraphStore graphStore, Collection<NodeLabel> selectedLabels,
            Collection<RelationshipType> selectedRelationshipTypes) {
        ArrayList<IllegalArgumentException> errors_ = new ArrayList<>();
        try {
            validateNodeLabels(graphStore, selectedLabels, selectedRelationshipTypes);
        } catch (IllegalArgumentException e) {
            errors_.add(e);
        }
        try {
            validateRelationshipTypes(graphStore, selectedLabels, selectedRelationshipTypes);
        } catch (IllegalArgumentException e) {
            errors_.add(e);
        }
        try {
            relationshipWeightValidation(graphStore, selectedLabels, selectedRelationshipTypes);
        } catch (IllegalArgumentException e) {
            errors_.add(e);
        }
        try {
            validateGraphIsSuitableForWrite(graphStore, selectedLabels, selectedRelationshipTypes);
        } catch (IllegalArgumentException e) {
            errors_.add(e);
        }
        try {
            validateMutateProperty(graphStore, selectedLabels, selectedRelationshipTypes);
        } catch (IllegalArgumentException e) {
            errors_.add(e);
        }
        try {
            validateSeedProperty(graphStore, selectedLabels, selectedRelationshipTypes);
        } catch (IllegalArgumentException e) {
            errors_.add(e);
        }
        if(!errors_.isEmpty()) {
            if(errors_.size() == 1) {
                throw errors_.get(0);
            } else {
                String combinedErrorMsg_ = errors_.stream().map(IllegalArgumentException::getMessage).collect(Collectors.joining(System.lineSeparator() + "\t\t\t\t", "Multiple errors in configuration arguments:" + System.lineSeparator() + "\t\t\t\t", ""));
                IllegalArgumentException combinedError_ = new IllegalArgumentException(combinedErrorMsg_);
                errors_.forEach(error_ -> combinedError_.addSuppressed(error_));
                throw combinedError_;
            }
        }
    }

    @Override
    public List<String> nodeLabels() {
        return this.nodeLabels;
    }

    @Override
    public List<String> relationshipTypes() {
        return this.relationshipTypes;
    }

    @Override
    public Collection<String> configKeys() {
        return Arrays.asList("isAsynchronous", "maxRepeatNodes", "withRepeition", "mutateProperty", "writeProperty", "partitioning", "nodeLabels", "relationshipTypes", "logProgress", "sudo", "username", "concurrency", "jobId", "relationshipWeightProperty", "maxIterations", "arrowConnectionInfo", "writeConcurrency", "seedProperty");
    }

    @Override
    public boolean logProgress() {
        return this.logProgress;
    }

    @Override
    public boolean sudo() {
        return this.sudo;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("isAsynchronous", isAsynchronous());
        map.put("maxRepeatNodes", maxRepeatNodes());
        map.put("withRepeition", withRepeition());
        map.put("mutateProperty", mutateProperty());
        map.put("writeProperty", writeProperty());
        map.put("partitioning", org.neo4j.gds.beta.pregel.Partitioning.toString(partitioning()));
        map.put("nodeLabels", nodeLabels());
        map.put("relationshipTypes", relationshipTypes());
        map.put("logProgress", logProgress());
        map.put("sudo", sudo());
        usernameOverride().ifPresent(username -> map.put("username", username));
        map.put("concurrency", concurrency());
        map.put("jobId", org.neo4j.gds.core.utils.progress.JobId.asString(jobId()));
        relationshipWeightProperty().ifPresent(relationshipWeightProperty -> map.put("relationshipWeightProperty", relationshipWeightProperty));
        map.put("maxIterations", maxIterations());
        arrowConnectionInfo().ifPresent(arrowConnectionInfo -> map.put("arrowConnectionInfo", org.neo4j.gds.config.WriteConfig.ArrowConnectionInfo.toMap(arrowConnectionInfo)));
        map.put("writeConcurrency", writeConcurrency());
        map.put("seedProperty", seedProperty());
        return map;
    }

    @Override
    public Optional<String> usernameOverride() {
        return this.usernameOverride;
    }

    @Override
    public int concurrency() {
        return this.concurrency;
    }

    @Override
    public JobId jobId() {
        return this.jobId;
    }

    @Override
    public Optional<String> relationshipWeightProperty() {
        return this.relationshipWeightProperty;
    }

    @Override
    public int maxIterations() {
        return this.maxIterations;
    }

    @Override
    public Optional<WriteConfig.ArrowConnectionInfo> arrowConnectionInfo() {
        return this.arrowConnectionInfo;
    }

    @Override
    public int writeConcurrency() {
        return this.writeConcurrency;
    }

    @Override
    public @Nullable String seedProperty() {
        return this.seedProperty;
    }

    public static BidirectionalPathsMiningPregelConfigImpl.Builder builder() {
        return new BidirectionalPathsMiningPregelConfigImpl.Builder();
    }

    public static final class Builder {
        private final Map<String, Object> config;

        public Builder() {
            this.config = new HashMap<>();
        }

        public static BidirectionalPathsMiningPregelConfigImpl.Builder from(
                BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig baseConfig) {
            var builder = new BidirectionalPathsMiningPregelConfigImpl.Builder();
            builder.isAsynchronous(baseConfig.isAsynchronous());
            builder.maxRepeatNodes(baseConfig.maxRepeatNodes());
            builder.withRepeition(baseConfig.withRepeition());
            builder.mutateProperty(baseConfig.mutateProperty());
            builder.writeProperty(baseConfig.writeProperty());
            builder.partitioning(baseConfig.partitioning());
            builder.nodeLabels(baseConfig.nodeLabels());
            builder.relationshipTypes(baseConfig.relationshipTypes());
            builder.logProgress(baseConfig.logProgress());
            builder.sudo(baseConfig.sudo());
            builder.usernameOverride(baseConfig.usernameOverride());
            builder.concurrency(baseConfig.concurrency());
            builder.jobId(baseConfig.jobId());
            builder.relationshipWeightProperty(baseConfig.relationshipWeightProperty());
            builder.maxIterations(baseConfig.maxIterations());
            builder.arrowConnectionInfo(baseConfig.arrowConnectionInfo());
            builder.writeConcurrency(baseConfig.writeConcurrency());
            builder.seedProperty(baseConfig.seedProperty());
            return builder;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder isAsynchronous(
                boolean isAsynchronous) {
            this.config.put("isAsynchronous", isAsynchronous);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder maxRepeatNodes(
                long maxRepeatNodes) {
            this.config.put("maxRepeatNodes", maxRepeatNodes);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder withRepeition(
                boolean withRepeition) {
            this.config.put("withRepeition", withRepeition);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder mutateProperty(
                String mutateProperty) {
            this.config.put("mutateProperty", mutateProperty);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder writeProperty(
                String writeProperty) {
            this.config.put("writeProperty", writeProperty);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder partitioning(Object partitioning) {
            this.config.put("partitioning", partitioning);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder nodeLabels(
                List<String> nodeLabels) {
            this.config.put("nodeLabels", nodeLabels);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder relationshipTypes(
                List<String> relationshipTypes) {
            this.config.put("relationshipTypes", relationshipTypes);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder logProgress(boolean logProgress) {
            this.config.put("logProgress", logProgress);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder sudo(boolean sudo) {
            this.config.put("sudo", sudo);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder usernameOverride(
                String usernameOverride) {
            this.config.put("username", usernameOverride);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder usernameOverride(
                Optional<String> usernameOverride) {
            usernameOverride.ifPresent(actualusernameOverride -> this.config.put("username", actualusernameOverride));
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder concurrency(int concurrency) {
            this.config.put("concurrency", concurrency);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder jobId(Object jobId) {
            this.config.put("jobId", jobId);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder relationshipWeightProperty(
                String relationshipWeightProperty) {
            this.config.put("relationshipWeightProperty", relationshipWeightProperty);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder relationshipWeightProperty(
                Optional<String> relationshipWeightProperty) {
            relationshipWeightProperty.ifPresent(actualrelationshipWeightProperty -> this.config.put("relationshipWeightProperty", actualrelationshipWeightProperty));
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder maxIterations(int maxIterations) {
            this.config.put("maxIterations", maxIterations);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder arrowConnectionInfo(
                Object arrowConnectionInfo) {
            this.config.put("arrowConnectionInfo", arrowConnectionInfo);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder arrowConnectionInfo(
                Optional<Object> arrowConnectionInfo) {
            arrowConnectionInfo.ifPresent(actualarrowConnectionInfo -> this.config.put("arrowConnectionInfo", actualarrowConnectionInfo));
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder writeConcurrency(
                int writeConcurrency) {
            this.config.put("writeConcurrency", writeConcurrency);
            return this;
        }

        public BidirectionalPathsMiningPregelConfigImpl.Builder seedProperty(String seedProperty) {
            this.config.put("seedProperty", seedProperty);
            return this;
        }

        public BidirectionalPathsMiningPregel.BidirectionalPathsMiningPregelConfig build() {
            CypherMapWrapper config = CypherMapWrapper.create(this.config);
            return new BidirectionalPathsMiningPregelConfigImpl(config);
        }
    }
}
