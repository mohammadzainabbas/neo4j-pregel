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
public final class FrequentSubgraphMiningPregelConfigImpl implements FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig {
    private boolean isAsynchronous;

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

    public FrequentSubgraphMiningPregelConfigImpl(@NotNull CypherMapAccess config) {
        ArrayList<IllegalArgumentException> errors = new ArrayList<>();
        try {
            this.isAsynchronous = config.getBool("isAsynchronous", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.isAsynchronous());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.mutateProperty = CypherMapAccess.failOnNull("mutateProperty", config.getString("mutateProperty", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.mutateProperty()));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.writeProperty = CypherMapAccess.failOnNull("writeProperty", config.getString("writeProperty", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.writeProperty()));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.partitioning = CypherMapAccess.failOnNull("partitioning", Partitioning.parse(config.getChecked("partitioning", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.partitioning(), Object.class)));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.nodeLabels = CypherMapAccess.failOnNull("nodeLabels", config.getChecked("nodeLabels", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.nodeLabels(), List.class));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.relationshipTypes = CypherMapAccess.failOnNull("relationshipTypes", config.getChecked("relationshipTypes", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.relationshipTypes(), List.class));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.logProgress = config.getBool("logProgress", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.logProgress());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.sudo = config.getBool("sudo", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.sudo());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.usernameOverride = CypherMapAccess.failOnNull("username", config.getOptional("username", String.class).map(BaseConfig::trim));
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.concurrency = config.getInt("concurrency", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.concurrency());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.jobId = CypherMapAccess.failOnNull("jobId", JobId.parse(config.getChecked("jobId", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.jobId(), Object.class)));
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
            this.writeConcurrency = config.getInt("writeConcurrency", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.writeConcurrency());
        } catch (IllegalArgumentException e) {
            errors.add(e);
        }
        try {
            this.seedProperty = SeedConfig.validatePropertyName(config.getString("seedProperty", FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.seedProperty()));
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
        return Arrays.asList("isAsynchronous", "mutateProperty", "writeProperty", "partitioning", "nodeLabels", "relationshipTypes", "logProgress", "sudo", "username", "concurrency", "jobId", "relationshipWeightProperty", "maxIterations", "arrowConnectionInfo", "writeConcurrency", "seedProperty");
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

    public static FrequentSubgraphMiningPregelConfigImpl.Builder builder() {
        return new FrequentSubgraphMiningPregelConfigImpl.Builder();
    }

    public static final class Builder {
        private final Map<String, Object> config;

        public Builder() {
            this.config = new HashMap<>();
        }

        public static FrequentSubgraphMiningPregelConfigImpl.Builder from(
                FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig baseConfig) {
            var builder = new FrequentSubgraphMiningPregelConfigImpl.Builder();
            builder.isAsynchronous(baseConfig.isAsynchronous());
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

        public FrequentSubgraphMiningPregelConfigImpl.Builder isAsynchronous(
                boolean isAsynchronous) {
            this.config.put("isAsynchronous", isAsynchronous);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder mutateProperty(
                String mutateProperty) {
            this.config.put("mutateProperty", mutateProperty);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder writeProperty(String writeProperty) {
            this.config.put("writeProperty", writeProperty);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder partitioning(Object partitioning) {
            this.config.put("partitioning", partitioning);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder nodeLabels(List<String> nodeLabels) {
            this.config.put("nodeLabels", nodeLabels);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder relationshipTypes(
                List<String> relationshipTypes) {
            this.config.put("relationshipTypes", relationshipTypes);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder logProgress(boolean logProgress) {
            this.config.put("logProgress", logProgress);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder sudo(boolean sudo) {
            this.config.put("sudo", sudo);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder usernameOverride(
                String usernameOverride) {
            this.config.put("username", usernameOverride);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder usernameOverride(
                Optional<String> usernameOverride) {
            usernameOverride.ifPresent(actualusernameOverride -> this.config.put("username", actualusernameOverride));
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder concurrency(int concurrency) {
            this.config.put("concurrency", concurrency);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder jobId(Object jobId) {
            this.config.put("jobId", jobId);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder relationshipWeightProperty(
                String relationshipWeightProperty) {
            this.config.put("relationshipWeightProperty", relationshipWeightProperty);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder relationshipWeightProperty(
                Optional<String> relationshipWeightProperty) {
            relationshipWeightProperty.ifPresent(actualrelationshipWeightProperty -> this.config.put("relationshipWeightProperty", actualrelationshipWeightProperty));
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder maxIterations(int maxIterations) {
            this.config.put("maxIterations", maxIterations);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder arrowConnectionInfo(
                Object arrowConnectionInfo) {
            this.config.put("arrowConnectionInfo", arrowConnectionInfo);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder arrowConnectionInfo(
                Optional<Object> arrowConnectionInfo) {
            arrowConnectionInfo.ifPresent(actualarrowConnectionInfo -> this.config.put("arrowConnectionInfo", actualarrowConnectionInfo));
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder writeConcurrency(
                int writeConcurrency) {
            this.config.put("writeConcurrency", writeConcurrency);
            return this;
        }

        public FrequentSubgraphMiningPregelConfigImpl.Builder seedProperty(String seedProperty) {
            this.config.put("seedProperty", seedProperty);
            return this;
        }

        public FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig build() {
            CypherMapWrapper config = CypherMapWrapper.create(this.config);
            return new FrequentSubgraphMiningPregelConfigImpl(config);
        }
    }
}