package com.neo4j.pregel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.immutables.value.Generated;
import org.jetbrains.annotations.Nullable;
import org.neo4j.gds.beta.pregel.Partitioning;
import org.neo4j.gds.beta.pregel.PregelConfig;
import org.neo4j.gds.beta.pregel.PregelProcedureConfig;
import org.neo4j.gds.config.AlgoBaseConfig;
import org.neo4j.gds.config.BaseConfig;
import org.neo4j.gds.config.ConcurrencyConfig;
import org.neo4j.gds.config.IterationsConfig;
import org.neo4j.gds.config.JobIdConfig;
import org.neo4j.gds.config.MutatePropertyConfig;
import org.neo4j.gds.config.RelationshipWeightConfig;
import org.neo4j.gds.config.SeedConfig;
import org.neo4j.gds.config.WriteConfig;
import org.neo4j.gds.config.WritePropertyConfig;
import org.neo4j.gds.core.utils.progress.JobId;

/**
 * Immutable implementation of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableFrequentSubgraphMiningPregelConfig.builder()}.
 * Use the static factory method to create immutable instances:
 * {@code ImmutableFrequentSubgraphMiningPregelConfig.of()}.
 */
@Generated(from = "FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig", generator = "Immutables")
@SuppressWarnings({"all"})
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
public final class ImmutableFrequentSubgraphMiningPregelConfig
    implements FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig {
  private final Collection<String> configKeys;
  private final boolean logProgress;
  private final boolean sudo;
  private transient final Map<String, Object> toMap;
  private final String usernameOverride;
  private final int concurrency;
  private final int minBatchSize;
  private final JobId jobId;
  private final List<String> nodeLabels;
  private final List<String> relationshipTypes;
  private transient final boolean hasRelationshipWeightProperty;
  private final String relationshipWeightProperty;
  private final int maxIterations;
  private final Partitioning partitioning;
  private transient final boolean useForkJoin;
  private final WriteConfig.ArrowConnectionInfo arrowConnectionInfo;
  private final int writeConcurrency;
  private final String mutateProperty;
  private final String writeProperty;
  private final @Nullable String seedProperty;

  @SuppressWarnings("unchecked") // safe covariant cast
  private ImmutableFrequentSubgraphMiningPregelConfig(
      int concurrency,
      int minBatchSize,
      Iterable<String> nodeLabels,
      Iterable<String> relationshipTypes,
      Optional<String> relationshipWeightProperty,
      int maxIterations,
      Partitioning partitioning,
      Optional<? extends WriteConfig.ArrowConnectionInfo> arrowConnectionInfo,
      int writeConcurrency,
      String mutateProperty,
      String writeProperty,
      @Nullable String seedProperty) {
    initShim.concurrency(concurrency);
    initShim.minBatchSize(minBatchSize);
    initShim.nodeLabels(createUnmodifiableList(false, createSafeList(nodeLabels, true, false)));
    initShim.relationshipTypes(createUnmodifiableList(false, createSafeList(relationshipTypes, true, false)));
    this.relationshipWeightProperty = relationshipWeightProperty.orElse(null);
    this.maxIterations = maxIterations;
    initShim.partitioning(Objects.requireNonNull(partitioning, "partitioning"));
    this.arrowConnectionInfo = arrowConnectionInfo.orElse(null);
    initShim.writeConcurrency(writeConcurrency);
    initShim.mutateProperty(Objects.requireNonNull(mutateProperty, "mutateProperty"));
    initShim.writeProperty(Objects.requireNonNull(writeProperty, "writeProperty"));
    initShim.seedProperty(seedProperty);
    this.usernameOverride = null;
    this.configKeys = initShim.configKeys();
    this.logProgress = initShim.logProgress();
    this.sudo = initShim.sudo();
    this.toMap = initShim.toMap();
    this.concurrency = initShim.concurrency();
    this.minBatchSize = initShim.minBatchSize();
    this.jobId = initShim.jobId();
    this.nodeLabels = initShim.nodeLabels();
    this.relationshipTypes = initShim.relationshipTypes();
    this.hasRelationshipWeightProperty = initShim.hasRelationshipWeightProperty();
    this.partitioning = initShim.partitioning();
    this.useForkJoin = initShim.useForkJoin();
    this.writeConcurrency = initShim.writeConcurrency();
    this.mutateProperty = initShim.mutateProperty();
    this.writeProperty = initShim.writeProperty();
    this.seedProperty = initShim.seedProperty();
    this.initShim = null;
  }

  @SuppressWarnings("unchecked") // safe covariant cast
  private ImmutableFrequentSubgraphMiningPregelConfig(
      int concurrency,
      int minBatchSize,
      Iterable<String> nodeLabels,
      Iterable<String> relationshipTypes,
      String relationshipWeightProperty,
      int maxIterations,
      Partitioning partitioning,
      WriteConfig.ArrowConnectionInfo arrowConnectionInfo,
      int writeConcurrency,
      String mutateProperty,
      String writeProperty,
      @Nullable String seedProperty) {
    initShim.concurrency(concurrency);
    initShim.minBatchSize(minBatchSize);
    initShim.nodeLabels(createUnmodifiableList(false, createSafeList(nodeLabels, true, false)));
    initShim.relationshipTypes(createUnmodifiableList(false, createSafeList(relationshipTypes, true, false)));
    this.relationshipWeightProperty = relationshipWeightProperty;
    this.maxIterations = maxIterations;
    initShim.partitioning(Objects.requireNonNull(partitioning, "partitioning"));
    this.arrowConnectionInfo = arrowConnectionInfo;
    initShim.writeConcurrency(writeConcurrency);
    initShim.mutateProperty(Objects.requireNonNull(mutateProperty, "mutateProperty"));
    initShim.writeProperty(Objects.requireNonNull(writeProperty, "writeProperty"));
    initShim.seedProperty(seedProperty);
    this.usernameOverride = null;
    this.configKeys = initShim.configKeys();
    this.logProgress = initShim.logProgress();
    this.sudo = initShim.sudo();
    this.toMap = initShim.toMap();
    this.concurrency = initShim.concurrency();
    this.minBatchSize = initShim.minBatchSize();
    this.jobId = initShim.jobId();
    this.nodeLabels = initShim.nodeLabels();
    this.relationshipTypes = initShim.relationshipTypes();
    this.hasRelationshipWeightProperty = initShim.hasRelationshipWeightProperty();
    this.partitioning = initShim.partitioning();
    this.useForkJoin = initShim.useForkJoin();
    this.writeConcurrency = initShim.writeConcurrency();
    this.mutateProperty = initShim.mutateProperty();
    this.writeProperty = initShim.writeProperty();
    this.seedProperty = initShim.seedProperty();
    this.initShim = null;
  }

  private ImmutableFrequentSubgraphMiningPregelConfig(ImmutableFrequentSubgraphMiningPregelConfig.Builder builder) {
    this.usernameOverride = builder.usernameOverride;
    this.relationshipWeightProperty = builder.relationshipWeightProperty;
    this.maxIterations = builder.maxIterations;
    this.arrowConnectionInfo = builder.arrowConnectionInfo;
    if (builder.configKeys != null) {
      initShim.configKeys(builder.configKeys);
    }
    if (builder.logProgressIsSet()) {
      initShim.logProgress(builder.logProgress);
    }
    if (builder.sudoIsSet()) {
      initShim.sudo(builder.sudo);
    }
    if (builder.concurrencyIsSet()) {
      initShim.concurrency(builder.concurrency);
    }
    if (builder.minBatchSizeIsSet()) {
      initShim.minBatchSize(builder.minBatchSize);
    }
    if (builder.jobId != null) {
      initShim.jobId(builder.jobId);
    }
    if (builder.nodeLabelsIsSet()) {
      initShim.nodeLabels(builder.nodeLabels == null ? Collections.<String>emptyList() : createUnmodifiableList(true, builder.nodeLabels));
    }
    if (builder.relationshipTypesIsSet()) {
      initShim.relationshipTypes(builder.relationshipTypes == null ? Collections.<String>emptyList() : createUnmodifiableList(true, builder.relationshipTypes));
    }
    if (builder.partitioning != null) {
      initShim.partitioning(builder.partitioning);
    }
    if (builder.writeConcurrencyIsSet()) {
      initShim.writeConcurrency(builder.writeConcurrency);
    }
    if (builder.mutateProperty != null) {
      initShim.mutateProperty(builder.mutateProperty);
    }
    if (builder.writeProperty != null) {
      initShim.writeProperty(builder.writeProperty);
    }
    if (builder.seedPropertyIsSet()) {
      initShim.seedProperty(builder.seedProperty);
    }
    this.configKeys = initShim.configKeys();
    this.logProgress = initShim.logProgress();
    this.sudo = initShim.sudo();
    this.toMap = initShim.toMap();
    this.concurrency = initShim.concurrency();
    this.minBatchSize = initShim.minBatchSize();
    this.jobId = initShim.jobId();
    this.nodeLabels = initShim.nodeLabels();
    this.relationshipTypes = initShim.relationshipTypes();
    this.hasRelationshipWeightProperty = initShim.hasRelationshipWeightProperty();
    this.partitioning = initShim.partitioning();
    this.useForkJoin = initShim.useForkJoin();
    this.writeConcurrency = initShim.writeConcurrency();
    this.mutateProperty = initShim.mutateProperty();
    this.writeProperty = initShim.writeProperty();
    this.seedProperty = initShim.seedProperty();
    this.initShim = null;
  }

  private ImmutableFrequentSubgraphMiningPregelConfig(
      Collection<String> configKeys,
      boolean logProgress,
      boolean sudo,
      String usernameOverride,
      int concurrency,
      int minBatchSize,
      JobId jobId,
      List<String> nodeLabels,
      List<String> relationshipTypes,
      String relationshipWeightProperty,
      int maxIterations,
      Partitioning partitioning,
      WriteConfig.ArrowConnectionInfo arrowConnectionInfo,
      int writeConcurrency,
      String mutateProperty,
      String writeProperty,
      @Nullable String seedProperty) {
    initShim.configKeys(configKeys);
    initShim.logProgress(logProgress);
    initShim.sudo(sudo);
    this.usernameOverride = usernameOverride;
    initShim.concurrency(concurrency);
    initShim.minBatchSize(minBatchSize);
    initShim.jobId(jobId);
    initShim.nodeLabels(nodeLabels);
    initShim.relationshipTypes(relationshipTypes);
    this.relationshipWeightProperty = relationshipWeightProperty;
    this.maxIterations = maxIterations;
    initShim.partitioning(partitioning);
    this.arrowConnectionInfo = arrowConnectionInfo;
    initShim.writeConcurrency(writeConcurrency);
    initShim.mutateProperty(mutateProperty);
    initShim.writeProperty(writeProperty);
    initShim.seedProperty(seedProperty);
    this.configKeys = initShim.configKeys();
    this.logProgress = initShim.logProgress();
    this.sudo = initShim.sudo();
    this.toMap = initShim.toMap();
    this.concurrency = initShim.concurrency();
    this.minBatchSize = initShim.minBatchSize();
    this.jobId = initShim.jobId();
    this.nodeLabels = initShim.nodeLabels();
    this.relationshipTypes = initShim.relationshipTypes();
    this.hasRelationshipWeightProperty = initShim.hasRelationshipWeightProperty();
    this.partitioning = initShim.partitioning();
    this.useForkJoin = initShim.useForkJoin();
    this.writeConcurrency = initShim.writeConcurrency();
    this.mutateProperty = initShim.mutateProperty();
    this.writeProperty = initShim.writeProperty();
    this.seedProperty = initShim.seedProperty();
    this.initShim = null;
  }

  private static final byte STAGE_INITIALIZING = -1;
  private static final byte STAGE_UNINITIALIZED = 0;
  private static final byte STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  @Generated(from = "FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig", generator = "Immutables")
  private final class InitShim {
    private byte configKeysBuildStage = STAGE_UNINITIALIZED;
    private Collection<String> configKeys;

    Collection<String> configKeys() {
      if (configKeysBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (configKeysBuildStage == STAGE_UNINITIALIZED) {
        configKeysBuildStage = STAGE_INITIALIZING;
        this.configKeys = Objects.requireNonNull(configKeysInitialize(), "configKeys");
        configKeysBuildStage = STAGE_INITIALIZED;
      }
      return this.configKeys;
    }

    void configKeys(Collection<String> configKeys) {
      this.configKeys = configKeys;
      configKeysBuildStage = STAGE_INITIALIZED;
    }

    private byte logProgressBuildStage = STAGE_UNINITIALIZED;
    private boolean logProgress;

    boolean logProgress() {
      if (logProgressBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (logProgressBuildStage == STAGE_UNINITIALIZED) {
        logProgressBuildStage = STAGE_INITIALIZING;
        this.logProgress = logProgressInitialize();
        logProgressBuildStage = STAGE_INITIALIZED;
      }
      return this.logProgress;
    }

    void logProgress(boolean logProgress) {
      this.logProgress = logProgress;
      logProgressBuildStage = STAGE_INITIALIZED;
    }

    private byte sudoBuildStage = STAGE_UNINITIALIZED;
    private boolean sudo;

    boolean sudo() {
      if (sudoBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (sudoBuildStage == STAGE_UNINITIALIZED) {
        sudoBuildStage = STAGE_INITIALIZING;
        this.sudo = sudoInitialize();
        sudoBuildStage = STAGE_INITIALIZED;
      }
      return this.sudo;
    }

    void sudo(boolean sudo) {
      this.sudo = sudo;
      sudoBuildStage = STAGE_INITIALIZED;
    }

    private byte toMapBuildStage = STAGE_UNINITIALIZED;
    private Map<String, Object> toMap;

    Map<String, Object> toMap() {
      if (toMapBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (toMapBuildStage == STAGE_UNINITIALIZED) {
        toMapBuildStage = STAGE_INITIALIZING;
        this.toMap = Objects.requireNonNull(toMapInitialize(), "toMap");
        toMapBuildStage = STAGE_INITIALIZED;
      }
      return this.toMap;
    }

    private byte concurrencyBuildStage = STAGE_UNINITIALIZED;
    private int concurrency;

    int concurrency() {
      if (concurrencyBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (concurrencyBuildStage == STAGE_UNINITIALIZED) {
        concurrencyBuildStage = STAGE_INITIALIZING;
        this.concurrency = concurrencyInitialize();
        concurrencyBuildStage = STAGE_INITIALIZED;
      }
      return this.concurrency;
    }

    void concurrency(int concurrency) {
      this.concurrency = concurrency;
      concurrencyBuildStage = STAGE_INITIALIZED;
    }

    private byte minBatchSizeBuildStage = STAGE_UNINITIALIZED;
    private int minBatchSize;

    int minBatchSize() {
      if (minBatchSizeBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (minBatchSizeBuildStage == STAGE_UNINITIALIZED) {
        minBatchSizeBuildStage = STAGE_INITIALIZING;
        this.minBatchSize = minBatchSizeInitialize();
        minBatchSizeBuildStage = STAGE_INITIALIZED;
      }
      return this.minBatchSize;
    }

    void minBatchSize(int minBatchSize) {
      this.minBatchSize = minBatchSize;
      minBatchSizeBuildStage = STAGE_INITIALIZED;
    }

    private byte jobIdBuildStage = STAGE_UNINITIALIZED;
    private JobId jobId;

    JobId jobId() {
      if (jobIdBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (jobIdBuildStage == STAGE_UNINITIALIZED) {
        jobIdBuildStage = STAGE_INITIALIZING;
        this.jobId = Objects.requireNonNull(jobIdInitialize(), "jobId");
        jobIdBuildStage = STAGE_INITIALIZED;
      }
      return this.jobId;
    }

    void jobId(JobId jobId) {
      this.jobId = jobId;
      jobIdBuildStage = STAGE_INITIALIZED;
    }

    private byte nodeLabelsBuildStage = STAGE_UNINITIALIZED;
    private List<String> nodeLabels;

    List<String> nodeLabels() {
      if (nodeLabelsBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (nodeLabelsBuildStage == STAGE_UNINITIALIZED) {
        nodeLabelsBuildStage = STAGE_INITIALIZING;
        this.nodeLabels = createUnmodifiableList(false, createSafeList(nodeLabelsInitialize(), true, false));
        nodeLabelsBuildStage = STAGE_INITIALIZED;
      }
      return this.nodeLabels;
    }

    void nodeLabels(List<String> nodeLabels) {
      this.nodeLabels = nodeLabels;
      nodeLabelsBuildStage = STAGE_INITIALIZED;
    }

    private byte relationshipTypesBuildStage = STAGE_UNINITIALIZED;
    private List<String> relationshipTypes;

    List<String> relationshipTypes() {
      if (relationshipTypesBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (relationshipTypesBuildStage == STAGE_UNINITIALIZED) {
        relationshipTypesBuildStage = STAGE_INITIALIZING;
        this.relationshipTypes = createUnmodifiableList(false, createSafeList(relationshipTypesInitialize(), true, false));
        relationshipTypesBuildStage = STAGE_INITIALIZED;
      }
      return this.relationshipTypes;
    }

    void relationshipTypes(List<String> relationshipTypes) {
      this.relationshipTypes = relationshipTypes;
      relationshipTypesBuildStage = STAGE_INITIALIZED;
    }

    private byte hasRelationshipWeightPropertyBuildStage = STAGE_UNINITIALIZED;
    private boolean hasRelationshipWeightProperty;

    boolean hasRelationshipWeightProperty() {
      if (hasRelationshipWeightPropertyBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (hasRelationshipWeightPropertyBuildStage == STAGE_UNINITIALIZED) {
        hasRelationshipWeightPropertyBuildStage = STAGE_INITIALIZING;
        this.hasRelationshipWeightProperty = hasRelationshipWeightPropertyInitialize();
        hasRelationshipWeightPropertyBuildStage = STAGE_INITIALIZED;
      }
      return this.hasRelationshipWeightProperty;
    }

    private byte partitioningBuildStage = STAGE_UNINITIALIZED;
    private Partitioning partitioning;

    Partitioning partitioning() {
      if (partitioningBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (partitioningBuildStage == STAGE_UNINITIALIZED) {
        partitioningBuildStage = STAGE_INITIALIZING;
        this.partitioning = Objects.requireNonNull(partitioningInitialize(), "partitioning");
        partitioningBuildStage = STAGE_INITIALIZED;
      }
      return this.partitioning;
    }

    void partitioning(Partitioning partitioning) {
      this.partitioning = partitioning;
      partitioningBuildStage = STAGE_INITIALIZED;
    }

    private byte useForkJoinBuildStage = STAGE_UNINITIALIZED;
    private boolean useForkJoin;

    boolean useForkJoin() {
      if (useForkJoinBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (useForkJoinBuildStage == STAGE_UNINITIALIZED) {
        useForkJoinBuildStage = STAGE_INITIALIZING;
        this.useForkJoin = useForkJoinInitialize();
        useForkJoinBuildStage = STAGE_INITIALIZED;
      }
      return this.useForkJoin;
    }

    private byte writeConcurrencyBuildStage = STAGE_UNINITIALIZED;
    private int writeConcurrency;

    int writeConcurrency() {
      if (writeConcurrencyBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (writeConcurrencyBuildStage == STAGE_UNINITIALIZED) {
        writeConcurrencyBuildStage = STAGE_INITIALIZING;
        this.writeConcurrency = writeConcurrencyInitialize();
        writeConcurrencyBuildStage = STAGE_INITIALIZED;
      }
      return this.writeConcurrency;
    }

    void writeConcurrency(int writeConcurrency) {
      this.writeConcurrency = writeConcurrency;
      writeConcurrencyBuildStage = STAGE_INITIALIZED;
    }

    private byte mutatePropertyBuildStage = STAGE_UNINITIALIZED;
    private String mutateProperty;

    String mutateProperty() {
      if (mutatePropertyBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (mutatePropertyBuildStage == STAGE_UNINITIALIZED) {
        mutatePropertyBuildStage = STAGE_INITIALIZING;
        this.mutateProperty = Objects.requireNonNull(mutatePropertyInitialize(), "mutateProperty");
        mutatePropertyBuildStage = STAGE_INITIALIZED;
      }
      return this.mutateProperty;
    }

    void mutateProperty(String mutateProperty) {
      this.mutateProperty = mutateProperty;
      mutatePropertyBuildStage = STAGE_INITIALIZED;
    }

    private byte writePropertyBuildStage = STAGE_UNINITIALIZED;
    private String writeProperty;

    String writeProperty() {
      if (writePropertyBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (writePropertyBuildStage == STAGE_UNINITIALIZED) {
        writePropertyBuildStage = STAGE_INITIALIZING;
        this.writeProperty = Objects.requireNonNull(writePropertyInitialize(), "writeProperty");
        writePropertyBuildStage = STAGE_INITIALIZED;
      }
      return this.writeProperty;
    }

    void writeProperty(String writeProperty) {
      this.writeProperty = writeProperty;
      writePropertyBuildStage = STAGE_INITIALIZED;
    }

    private byte seedPropertyBuildStage = STAGE_UNINITIALIZED;
    private String seedProperty;

    String seedProperty() {
      if (seedPropertyBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (seedPropertyBuildStage == STAGE_UNINITIALIZED) {
        seedPropertyBuildStage = STAGE_INITIALIZING;
        this.seedProperty = seedPropertyInitialize();
        seedPropertyBuildStage = STAGE_INITIALIZED;
      }
      return this.seedProperty;
    }

    void seedProperty(String seedProperty) {
      this.seedProperty = seedProperty;
      seedPropertyBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      List<String> attributes = new ArrayList<>();
      if (configKeysBuildStage == STAGE_INITIALIZING) attributes.add("configKeys");
      if (logProgressBuildStage == STAGE_INITIALIZING) attributes.add("logProgress");
      if (sudoBuildStage == STAGE_INITIALIZING) attributes.add("sudo");
      if (toMapBuildStage == STAGE_INITIALIZING) attributes.add("toMap");
      if (concurrencyBuildStage == STAGE_INITIALIZING) attributes.add("concurrency");
      if (minBatchSizeBuildStage == STAGE_INITIALIZING) attributes.add("minBatchSize");
      if (jobIdBuildStage == STAGE_INITIALIZING) attributes.add("jobId");
      if (nodeLabelsBuildStage == STAGE_INITIALIZING) attributes.add("nodeLabels");
      if (relationshipTypesBuildStage == STAGE_INITIALIZING) attributes.add("relationshipTypes");
      if (hasRelationshipWeightPropertyBuildStage == STAGE_INITIALIZING) attributes.add("hasRelationshipWeightProperty");
      if (partitioningBuildStage == STAGE_INITIALIZING) attributes.add("partitioning");
      if (useForkJoinBuildStage == STAGE_INITIALIZING) attributes.add("useForkJoin");
      if (writeConcurrencyBuildStage == STAGE_INITIALIZING) attributes.add("writeConcurrency");
      if (mutatePropertyBuildStage == STAGE_INITIALIZING) attributes.add("mutateProperty");
      if (writePropertyBuildStage == STAGE_INITIALIZING) attributes.add("writeProperty");
      if (seedPropertyBuildStage == STAGE_INITIALIZING) attributes.add("seedProperty");
      return "Cannot build FrequentSubgraphMiningPregelConfig, attribute initializers form cycle " + attributes;
    }
  }

  private Collection<String> configKeysInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.configKeys();
  }

  private boolean logProgressInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.logProgress();
  }

  private boolean sudoInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.sudo();
  }

  private Map<String, Object> toMapInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.toMap();
  }

  private int concurrencyInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.concurrency();
  }

  private int minBatchSizeInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.minBatchSize();
  }

  private JobId jobIdInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.jobId();
  }

  private List<String> nodeLabelsInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.nodeLabels();
  }

  private List<String> relationshipTypesInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.relationshipTypes();
  }

  private boolean hasRelationshipWeightPropertyInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.hasRelationshipWeightProperty();
  }

  private Partitioning partitioningInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.partitioning();
  }

  private boolean useForkJoinInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.useForkJoin();
  }

  private int writeConcurrencyInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.writeConcurrency();
  }

  private String mutatePropertyInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.mutateProperty();
  }

  private String writePropertyInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.writeProperty();
  }

  private @Nullable String seedPropertyInitialize() {
    return FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig.super.seedProperty();
  }

  /**
   * @return The value of the {@code configKeys} attribute
   */
  @Override
  public Collection<String> configKeys() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.configKeys()
        : this.configKeys;
  }

  /**
   * @return The value of the {@code logProgress} attribute
   */
  @Override
  public boolean logProgress() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.logProgress()
        : this.logProgress;
  }

  /**
   * @return The value of the {@code sudo} attribute
   */
  @Override
  public boolean sudo() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.sudo()
        : this.sudo;
  }

  /**
   * @return The computed-at-construction value of the {@code toMap} attribute
   */
  @Override
  public Map<String, Object> toMap() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.toMap()
        : this.toMap;
  }

  /**
   * @return The value of the {@code usernameOverride} attribute
   */
  @Override
  public Optional<String> usernameOverride() {
    return Optional.ofNullable(usernameOverride);
  }

  /**
   * @return The value of the {@code concurrency} attribute
   */
  @Override
  public int concurrency() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.concurrency()
        : this.concurrency;
  }

  /**
   * @return The value of the {@code minBatchSize} attribute
   */
  @Override
  public int minBatchSize() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.minBatchSize()
        : this.minBatchSize;
  }

  /**
   * @return The value of the {@code jobId} attribute
   */
  @Override
  public JobId jobId() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.jobId()
        : this.jobId;
  }

  /**
   * @return The value of the {@code nodeLabels} attribute
   */
  @Override
  public List<String> nodeLabels() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.nodeLabels()
        : this.nodeLabels;
  }

  /**
   * @return The value of the {@code relationshipTypes} attribute
   */
  @Override
  public List<String> relationshipTypes() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.relationshipTypes()
        : this.relationshipTypes;
  }

  /**
   * @return The computed-at-construction value of the {@code hasRelationshipWeightProperty} attribute
   */
  @Override
  public boolean hasRelationshipWeightProperty() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.hasRelationshipWeightProperty()
        : this.hasRelationshipWeightProperty;
  }

  /**
   * @return The value of the {@code relationshipWeightProperty} attribute
   */
  @Override
  public Optional<String> relationshipWeightProperty() {
    return Optional.ofNullable(relationshipWeightProperty);
  }

  /**
   * @return The value of the {@code maxIterations} attribute
   */
  @Override
  public int maxIterations() {
    return maxIterations;
  }

  /**
   * @return The value of the {@code partitioning} attribute
   */
  @Override
  public Partitioning partitioning() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.partitioning()
        : this.partitioning;
  }

  /**
   * @return The computed-at-construction value of the {@code useForkJoin} attribute
   */
  @Override
  public boolean useForkJoin() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.useForkJoin()
        : this.useForkJoin;
  }

  /**
   * @return The value of the {@code arrowConnectionInfo} attribute
   */
  @Override
  public Optional<WriteConfig.ArrowConnectionInfo> arrowConnectionInfo() {
    return Optional.ofNullable(arrowConnectionInfo);
  }

  /**
   * @return The value of the {@code writeConcurrency} attribute
   */
  @Override
  public int writeConcurrency() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.writeConcurrency()
        : this.writeConcurrency;
  }

  /**
   * @return The value of the {@code mutateProperty} attribute
   */
  @Override
  public String mutateProperty() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.mutateProperty()
        : this.mutateProperty;
  }

  /**
   * @return The value of the {@code writeProperty} attribute
   */
  @Override
  public String writeProperty() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.writeProperty()
        : this.writeProperty;
  }

  /**
   * @return The value of the {@code seedProperty} attribute
   */
  @Override
  public @Nullable String seedProperty() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.seedProperty()
        : this.seedProperty;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#configKeys() configKeys} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for configKeys
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withConfigKeys(Collection<String> value) {
    if (this.configKeys == value) return this;
    Collection<String> newValue = Objects.requireNonNull(value, "configKeys");
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        newValue,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#logProgress() logProgress} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for logProgress
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withLogProgress(boolean value) {
    if (this.logProgress == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        value,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#sudo() sudo} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for sudo
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withSudo(boolean value) {
    if (this.sudo == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        value,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#usernameOverride() usernameOverride} attribute.
   * @param value The value for usernameOverride, {@code null} is accepted as {@code java.util.Optional.empty()}
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withUsernameOverride(String value) {
    String newValue = value;
    if (Objects.equals(this.usernameOverride, newValue)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        newValue,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#usernameOverride() usernameOverride} attribute.
   * An equality check is used on inner nullable value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for usernameOverride
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withUsernameOverride(Optional<String> optional) {
    String value = optional.orElse(null);
    if (Objects.equals(this.usernameOverride, value)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        value,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#concurrency() concurrency} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for concurrency
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withConcurrency(int value) {
    if (this.concurrency == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        value,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#minBatchSize() minBatchSize} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for minBatchSize
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withMinBatchSize(int value) {
    if (this.minBatchSize == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        value,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#jobId() jobId} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for jobId
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withJobId(JobId value) {
    if (this.jobId == value) return this;
    JobId newValue = Objects.requireNonNull(value, "jobId");
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        newValue,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withNodeLabels(String... elements) {
    List<String> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        newValue,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of nodeLabels elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withNodeLabels(Iterable<String> elements) {
    if (this.nodeLabels == elements) return this;
    List<String> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        newValue,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes}.
   * @param elements The elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withRelationshipTypes(String... elements) {
    List<String> newValue = createUnmodifiableList(false, createSafeList(Arrays.asList(elements), true, false));
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        newValue,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes}.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param elements An iterable of relationshipTypes elements to set
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withRelationshipTypes(Iterable<String> elements) {
    if (this.relationshipTypes == elements) return this;
    List<String> newValue = createUnmodifiableList(false, createSafeList(elements, true, false));
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        newValue,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipWeightProperty() relationshipWeightProperty} attribute.
   * @param value The value for relationshipWeightProperty, {@code null} is accepted as {@code java.util.Optional.empty()}
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withRelationshipWeightProperty(String value) {
    String newValue = value;
    if (Objects.equals(this.relationshipWeightProperty, newValue)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        newValue,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipWeightProperty() relationshipWeightProperty} attribute.
   * An equality check is used on inner nullable value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for relationshipWeightProperty
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withRelationshipWeightProperty(Optional<String> optional) {
    String value = optional.orElse(null);
    if (Objects.equals(this.relationshipWeightProperty, value)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        value,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#maxIterations() maxIterations} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for maxIterations
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withMaxIterations(int value) {
    if (this.maxIterations == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        value,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#partitioning() partitioning} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for partitioning
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withPartitioning(Partitioning value) {
    if (this.partitioning == value) return this;
    Partitioning newValue = Objects.requireNonNull(value, "partitioning");
    if (this.partitioning.equals(newValue)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        newValue,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#arrowConnectionInfo() arrowConnectionInfo} attribute.
   * @param value The value for arrowConnectionInfo, {@code null} is accepted as {@code java.util.Optional.empty()}
   * @return A modified copy of {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withArrowConnectionInfo(WriteConfig.ArrowConnectionInfo value) {
    WriteConfig.ArrowConnectionInfo newValue = value;
    if (this.arrowConnectionInfo == newValue) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        newValue,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#arrowConnectionInfo() arrowConnectionInfo} attribute.
   * A shallow reference equality check is used on unboxed optional value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for arrowConnectionInfo
   * @return A modified copy of {@code this} object
   */
  @SuppressWarnings("unchecked") // safe covariant cast
  public final ImmutableFrequentSubgraphMiningPregelConfig withArrowConnectionInfo(Optional<? extends WriteConfig.ArrowConnectionInfo> optional) {
    WriteConfig.ArrowConnectionInfo value = optional.orElse(null);
    if (this.arrowConnectionInfo == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        value,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeConcurrency() writeConcurrency} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for writeConcurrency
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withWriteConcurrency(int value) {
    if (this.writeConcurrency == value) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        value,
        this.mutateProperty,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#mutateProperty() mutateProperty} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for mutateProperty
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withMutateProperty(String value) {
    String newValue = Objects.requireNonNull(value, "mutateProperty");
    if (this.mutateProperty.equals(newValue)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        newValue,
        this.writeProperty,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeProperty() writeProperty} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for writeProperty
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withWriteProperty(String value) {
    String newValue = Objects.requireNonNull(value, "writeProperty");
    if (this.writeProperty.equals(newValue)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        newValue,
        this.seedProperty));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#seedProperty() seedProperty} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for seedProperty (can be {@code null})
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableFrequentSubgraphMiningPregelConfig withSeedProperty(@Nullable String value) {
    if (Objects.equals(this.seedProperty, value)) return this;
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(
        this.configKeys,
        this.logProgress,
        this.sudo,
        this.usernameOverride,
        this.concurrency,
        this.minBatchSize,
        this.jobId,
        this.nodeLabels,
        this.relationshipTypes,
        this.relationshipWeightProperty,
        this.maxIterations,
        this.partitioning,
        this.arrowConnectionInfo,
        this.writeConcurrency,
        this.mutateProperty,
        this.writeProperty,
        value));
  }

  /**
   * This instance is equal to all instances of {@code ImmutableFrequentSubgraphMiningPregelConfig} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableFrequentSubgraphMiningPregelConfig
        && equalTo((ImmutableFrequentSubgraphMiningPregelConfig) another);
  }

  private boolean equalTo(ImmutableFrequentSubgraphMiningPregelConfig another) {
    return logProgress == another.logProgress
        && sudo == another.sudo
        && Objects.equals(usernameOverride, another.usernameOverride)
        && concurrency == another.concurrency
        && minBatchSize == another.minBatchSize
        && jobId.equals(another.jobId)
        && nodeLabels.equals(another.nodeLabels)
        && relationshipTypes.equals(another.relationshipTypes)
        && hasRelationshipWeightProperty == another.hasRelationshipWeightProperty
        && Objects.equals(relationshipWeightProperty, another.relationshipWeightProperty)
        && maxIterations == another.maxIterations
        && partitioning.equals(another.partitioning)
        && useForkJoin == another.useForkJoin
        && Objects.equals(arrowConnectionInfo, another.arrowConnectionInfo)
        && writeConcurrency == another.writeConcurrency
        && mutateProperty.equals(another.mutateProperty)
        && writeProperty.equals(another.writeProperty)
        && Objects.equals(seedProperty, another.seedProperty);
  }

  /**
   * Computes a hash code from attributes: {@code logProgress}, {@code sudo}, {@code usernameOverride}, {@code concurrency}, {@code minBatchSize}, {@code jobId}, {@code nodeLabels}, {@code relationshipTypes}, {@code hasRelationshipWeightProperty}, {@code relationshipWeightProperty}, {@code maxIterations}, {@code partitioning}, {@code useForkJoin}, {@code arrowConnectionInfo}, {@code writeConcurrency}, {@code mutateProperty}, {@code writeProperty}, {@code seedProperty}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Boolean.hashCode(logProgress);
    h += (h << 5) + Boolean.hashCode(sudo);
    h += (h << 5) + Objects.hashCode(usernameOverride);
    h += (h << 5) + concurrency;
    h += (h << 5) + minBatchSize;
    h += (h << 5) + jobId.hashCode();
    h += (h << 5) + nodeLabels.hashCode();
    h += (h << 5) + relationshipTypes.hashCode();
    h += (h << 5) + Boolean.hashCode(hasRelationshipWeightProperty);
    h += (h << 5) + Objects.hashCode(relationshipWeightProperty);
    h += (h << 5) + maxIterations;
    h += (h << 5) + partitioning.hashCode();
    h += (h << 5) + Boolean.hashCode(useForkJoin);
    h += (h << 5) + Objects.hashCode(arrowConnectionInfo);
    h += (h << 5) + writeConcurrency;
    h += (h << 5) + mutateProperty.hashCode();
    h += (h << 5) + writeProperty.hashCode();
    h += (h << 5) + Objects.hashCode(seedProperty);
    return h;
  }


  /**
   * Prints the immutable value {@code FrequentSubgraphMiningPregelConfig} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("FrequentSubgraphMiningPregelConfig{");
    builder.append("logProgress=").append(logProgress);
    builder.append(", ");
    builder.append("sudo=").append(sudo);
    if (usernameOverride != null) {
      builder.append(", ");
      builder.append("usernameOverride=").append(usernameOverride);
    }
    builder.append(", ");
    builder.append("concurrency=").append(concurrency);
    builder.append(", ");
    builder.append("minBatchSize=").append(minBatchSize);
    builder.append(", ");
    builder.append("jobId=").append(jobId);
    builder.append(", ");
    builder.append("nodeLabels=").append(nodeLabels);
    builder.append(", ");
    builder.append("relationshipTypes=").append(relationshipTypes);
    builder.append(", ");
    builder.append("hasRelationshipWeightProperty=").append(hasRelationshipWeightProperty);
    if (relationshipWeightProperty != null) {
      builder.append(", ");
      builder.append("relationshipWeightProperty=").append(relationshipWeightProperty);
    }
    builder.append(", ");
    builder.append("maxIterations=").append(maxIterations);
    builder.append(", ");
    builder.append("partitioning=").append(partitioning);
    builder.append(", ");
    builder.append("useForkJoin=").append(useForkJoin);
    if (arrowConnectionInfo != null) {
      builder.append(", ");
      builder.append("arrowConnectionInfo=").append(arrowConnectionInfo);
    }
    builder.append(", ");
    builder.append("writeConcurrency=").append(writeConcurrency);
    builder.append(", ");
    builder.append("mutateProperty=").append(mutateProperty);
    builder.append(", ");
    builder.append("writeProperty=").append(writeProperty);
    if (seedProperty != null) {
      builder.append(", ");
      builder.append("seedProperty=").append(seedProperty);
    }
    return builder.append("}").toString();
  }

  /**
   * Construct a new immutable {@code FrequentSubgraphMiningPregelConfig} instance.
   * @param concurrency The value for the {@code concurrency} attribute
   * @param minBatchSize The value for the {@code minBatchSize} attribute
   * @param nodeLabels The value for the {@code nodeLabels} attribute
   * @param relationshipTypes The value for the {@code relationshipTypes} attribute
   * @param relationshipWeightProperty The value for the {@code relationshipWeightProperty} attribute
   * @param maxIterations The value for the {@code maxIterations} attribute
   * @param partitioning The value for the {@code partitioning} attribute
   * @param arrowConnectionInfo The value for the {@code arrowConnectionInfo} attribute
   * @param writeConcurrency The value for the {@code writeConcurrency} attribute
   * @param mutateProperty The value for the {@code mutateProperty} attribute
   * @param writeProperty The value for the {@code writeProperty} attribute
   * @param seedProperty The value for the {@code seedProperty} attribute
   * @return An immutable FrequentSubgraphMiningPregelConfig instance
   */
  public static FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig of(int concurrency, int minBatchSize, List<String> nodeLabels, List<String> relationshipTypes, Optional<String> relationshipWeightProperty, int maxIterations, Partitioning partitioning, Optional<WriteConfig.ArrowConnectionInfo> arrowConnectionInfo, int writeConcurrency, String mutateProperty, String writeProperty, @Nullable String seedProperty) {
    return of(concurrency, minBatchSize, (Iterable<String>) nodeLabels, (Iterable<String>) relationshipTypes, relationshipWeightProperty, maxIterations, partitioning, arrowConnectionInfo, writeConcurrency, mutateProperty, writeProperty, seedProperty);
  }

  /**
   * Construct a new immutable {@code FrequentSubgraphMiningPregelConfig} instance.
   * @param concurrency The value for the {@code concurrency} attribute
   * @param minBatchSize The value for the {@code minBatchSize} attribute
   * @param nodeLabels The value for the {@code nodeLabels} attribute
   * @param relationshipTypes The value for the {@code relationshipTypes} attribute
   * @param relationshipWeightProperty The value for the {@code relationshipWeightProperty} attribute
   * @param maxIterations The value for the {@code maxIterations} attribute
   * @param partitioning The value for the {@code partitioning} attribute
   * @param arrowConnectionInfo The value for the {@code arrowConnectionInfo} attribute
   * @param writeConcurrency The value for the {@code writeConcurrency} attribute
   * @param mutateProperty The value for the {@code mutateProperty} attribute
   * @param writeProperty The value for the {@code writeProperty} attribute
   * @param seedProperty The value for the {@code seedProperty} attribute
   * @return An immutable FrequentSubgraphMiningPregelConfig instance
   */
  public static FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig of(int concurrency, int minBatchSize, Iterable<String> nodeLabels, Iterable<String> relationshipTypes, Optional<String> relationshipWeightProperty, int maxIterations, Partitioning partitioning, Optional<? extends WriteConfig.ArrowConnectionInfo> arrowConnectionInfo, int writeConcurrency, String mutateProperty, String writeProperty, @Nullable String seedProperty) {
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(concurrency, minBatchSize, nodeLabels, relationshipTypes, relationshipWeightProperty, maxIterations, partitioning, arrowConnectionInfo, writeConcurrency, mutateProperty, writeProperty, seedProperty));
  }

  /**
   * Construct a new immutable {@code FrequentSubgraphMiningPregelConfig} instance.
   * @param concurrency The value for the {@code concurrency} attribute
   * @param minBatchSize The value for the {@code minBatchSize} attribute
   * @param nodeLabels The value for the {@code nodeLabels} attribute
   * @param relationshipTypes The value for the {@code relationshipTypes} attribute
   * @param relationshipWeightProperty The value for the {@code relationshipWeightProperty} attribute
   * @param maxIterations The value for the {@code maxIterations} attribute
   * @param partitioning The value for the {@code partitioning} attribute
   * @param arrowConnectionInfo The value for the {@code arrowConnectionInfo} attribute
   * @param writeConcurrency The value for the {@code writeConcurrency} attribute
   * @param mutateProperty The value for the {@code mutateProperty} attribute
   * @param writeProperty The value for the {@code writeProperty} attribute
   * @param seedProperty The value for the {@code seedProperty} attribute
   * @return An immutable FrequentSubgraphMiningPregelConfig instance
   */
  public static FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig of(int concurrency, int minBatchSize, Iterable<String> nodeLabels, Iterable<String> relationshipTypes, String relationshipWeightProperty, int maxIterations, Partitioning partitioning, WriteConfig.ArrowConnectionInfo arrowConnectionInfo, int writeConcurrency, String mutateProperty, String writeProperty, @Nullable String seedProperty) {
    return validate(new ImmutableFrequentSubgraphMiningPregelConfig(concurrency, minBatchSize, nodeLabels, relationshipTypes, relationshipWeightProperty, maxIterations, partitioning, arrowConnectionInfo, writeConcurrency, mutateProperty, writeProperty, seedProperty));
  }

  private static ImmutableFrequentSubgraphMiningPregelConfig validate(ImmutableFrequentSubgraphMiningPregelConfig instance) {
    instance.validateWriteConcurrency();
    instance.validateRelationshipWeightProperty();
    instance.validateConcurrency();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FrequentSubgraphMiningPregelConfig instance
   */
  public static FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig copyOf(FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig instance) {
    if (instance instanceof ImmutableFrequentSubgraphMiningPregelConfig) {
      return (ImmutableFrequentSubgraphMiningPregelConfig) instance;
    }
    return ImmutableFrequentSubgraphMiningPregelConfig.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig FrequentSubgraphMiningPregelConfig}.
   * <pre>
   * ImmutableFrequentSubgraphMiningPregelConfig.builder()
   *    .configKeys(Collection&amp;lt;String&amp;gt;) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#configKeys() configKeys}
   *    .logProgress(boolean) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#logProgress() logProgress}
   *    .sudo(boolean) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#sudo() sudo}
   *    .usernameOverride(String) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#usernameOverride() usernameOverride}
   *    .concurrency(int) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#concurrency() concurrency}
   *    .minBatchSize(int) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#minBatchSize() minBatchSize}
   *    .jobId(org.neo4j.gds.core.utils.progress.JobId) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#jobId() jobId}
   *    .addNodeLabel|addAllNodeLabels(String) // {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels} elements
   *    .addRelationshipType|addAllRelationshipTypes(String) // {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes} elements
   *    .relationshipWeightProperty(String) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipWeightProperty() relationshipWeightProperty}
   *    .maxIterations(int) // required {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#maxIterations() maxIterations}
   *    .partitioning(org.neo4j.gds.beta.pregel.Partitioning) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#partitioning() partitioning}
   *    .arrowConnectionInfo(org.neo4j.gds.config.WriteConfig.ArrowConnectionInfo) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#arrowConnectionInfo() arrowConnectionInfo}
   *    .writeConcurrency(int) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeConcurrency() writeConcurrency}
   *    .mutateProperty(String) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#mutateProperty() mutateProperty}
   *    .writeProperty(String) // optional {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeProperty() writeProperty}
   *    .seedProperty(String | null) // nullable {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#seedProperty() seedProperty}
   *    .build();
   * </pre>
   * @return A new FrequentSubgraphMiningPregelConfig builder
   */
  public static ImmutableFrequentSubgraphMiningPregelConfig.Builder builder() {
    return new ImmutableFrequentSubgraphMiningPregelConfig.Builder();
  }

  /**
   * Builds instances of type {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig FrequentSubgraphMiningPregelConfig}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_MAX_ITERATIONS = 0x1L;
    private static final long OPT_BIT_LOG_PROGRESS = 0x1L;
    private static final long OPT_BIT_SUDO = 0x2L;
    private static final long OPT_BIT_CONCURRENCY = 0x4L;
    private static final long OPT_BIT_MIN_BATCH_SIZE = 0x8L;
    private static final long OPT_BIT_NODE_LABELS = 0x10L;
    private static final long OPT_BIT_RELATIONSHIP_TYPES = 0x20L;
    private static final long OPT_BIT_WRITE_CONCURRENCY = 0x40L;
    private static final long OPT_BIT_SEED_PROPERTY = 0x80L;
    private long initBits = 0x1L;
    private long optBits;

    private Collection<String> configKeys;
    private boolean logProgress;
    private boolean sudo;
    private String usernameOverride;
    private int concurrency;
    private int minBatchSize;
    private JobId jobId;
    private List<String> nodeLabels = null;
    private List<String> relationshipTypes = null;
    private String relationshipWeightProperty;
    private int maxIterations;
    private Partitioning partitioning;
    private WriteConfig.ArrowConnectionInfo arrowConnectionInfo;
    private int writeConcurrency;
    private String mutateProperty;
    private String writeProperty;
    private String seedProperty;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.RelationshipWeightConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(RelationshipWeightConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.WriteConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(WriteConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.WritePropertyConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(WritePropertyConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.JobIdConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JobIdConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.ConcurrencyConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(ConcurrencyConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.beta.pregel.PregelProcedureConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(PregelProcedureConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.MutatePropertyConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(MutatePropertyConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.BaseConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(BaseConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.beta.pregel.PregelConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(PregelConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.AlgoBaseConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(AlgoBaseConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.IterationsConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(IterationsConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code org.neo4j.gds.config.SeedConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(SeedConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.neo4j.pregel.FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      long bits = 0;
      if (object instanceof RelationshipWeightConfig) {
        RelationshipWeightConfig instance = (RelationshipWeightConfig) object;
        Optional<String> relationshipWeightPropertyOptional = instance.relationshipWeightProperty();
        if (relationshipWeightPropertyOptional.isPresent()) {
          relationshipWeightProperty(relationshipWeightPropertyOptional);
        }
      }
      if (object instanceof WriteConfig) {
        WriteConfig instance = (WriteConfig) object;
        writeConcurrency(instance.writeConcurrency());
        Optional<WriteConfig.ArrowConnectionInfo> arrowConnectionInfoOptional = instance.arrowConnectionInfo();
        if (arrowConnectionInfoOptional.isPresent()) {
          arrowConnectionInfo(arrowConnectionInfoOptional);
        }
      }
      if (object instanceof WritePropertyConfig) {
        WritePropertyConfig instance = (WritePropertyConfig) object;
        if ((bits & 0x1L) == 0) {
          writeProperty(instance.writeProperty());
          bits |= 0x1L;
        }
      }
      if (object instanceof JobIdConfig) {
        JobIdConfig instance = (JobIdConfig) object;
        jobId(instance.jobId());
      }
      if (object instanceof ConcurrencyConfig) {
        ConcurrencyConfig instance = (ConcurrencyConfig) object;
        minBatchSize(instance.minBatchSize());
        concurrency(instance.concurrency());
      }
      if (object instanceof PregelProcedureConfig) {
        PregelProcedureConfig instance = (PregelProcedureConfig) object;
        if ((bits & 0x1L) == 0) {
          writeProperty(instance.writeProperty());
          bits |= 0x1L;
        }
        if ((bits & 0x2L) == 0) {
          mutateProperty(instance.mutateProperty());
          bits |= 0x2L;
        }
      }
      if (object instanceof MutatePropertyConfig) {
        MutatePropertyConfig instance = (MutatePropertyConfig) object;
        if ((bits & 0x2L) == 0) {
          mutateProperty(instance.mutateProperty());
          bits |= 0x2L;
        }
      }
      if (object instanceof BaseConfig) {
        BaseConfig instance = (BaseConfig) object;
        logProgress(instance.logProgress());
        sudo(instance.sudo());
        Optional<String> usernameOverrideOptional = instance.usernameOverride();
        if (usernameOverrideOptional.isPresent()) {
          usernameOverride(usernameOverrideOptional);
        }
        configKeys(instance.configKeys());
      }
      if (object instanceof PregelConfig) {
        PregelConfig instance = (PregelConfig) object;
        partitioning(instance.partitioning());
      }
      if (object instanceof AlgoBaseConfig) {
        AlgoBaseConfig instance = (AlgoBaseConfig) object;
        addAllRelationshipTypes(instance.relationshipTypes());
        addAllNodeLabels(instance.nodeLabels());
      }
      if (object instanceof IterationsConfig) {
        IterationsConfig instance = (IterationsConfig) object;
        maxIterations(instance.maxIterations());
      }
      if (object instanceof SeedConfig) {
        SeedConfig instance = (SeedConfig) object;
        @Nullable String seedPropertyValue = instance.seedProperty();
        if (seedPropertyValue != null) {
          seedProperty(seedPropertyValue);
        }
      }
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#configKeys() configKeys} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#configKeys() configKeys}.</em>
     * @param configKeys The value for configKeys 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder configKeys(Collection<String> configKeys) {
      this.configKeys = Objects.requireNonNull(configKeys, "configKeys");
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#logProgress() logProgress} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#logProgress() logProgress}.</em>
     * @param logProgress The value for logProgress 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder logProgress(boolean logProgress) {
      this.logProgress = logProgress;
      optBits |= OPT_BIT_LOG_PROGRESS;
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#sudo() sudo} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#sudo() sudo}.</em>
     * @param sudo The value for sudo 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder sudo(boolean sudo) {
      this.sudo = sudo;
      optBits |= OPT_BIT_SUDO;
      return this;
    }

    /**
     * Initializes the optional value {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#usernameOverride() usernameOverride} to usernameOverride.
     * @param usernameOverride The value for usernameOverride, {@code null} is accepted as {@code java.util.Optional.empty()}
     * @return {@code this} builder for chained invocation
     */
    public final Builder usernameOverride(String usernameOverride) {
      this.usernameOverride = usernameOverride;
      return this;
    }

    /**
     * Initializes the optional value {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#usernameOverride() usernameOverride} to usernameOverride.
     * @param usernameOverride The value for usernameOverride
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder usernameOverride(Optional<String> usernameOverride) {
      this.usernameOverride = usernameOverride.orElse(null);
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#concurrency() concurrency} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#concurrency() concurrency}.</em>
     * @param concurrency The value for concurrency 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder concurrency(int concurrency) {
      this.concurrency = concurrency;
      optBits |= OPT_BIT_CONCURRENCY;
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#minBatchSize() minBatchSize} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#minBatchSize() minBatchSize}.</em>
     * @param minBatchSize The value for minBatchSize 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder minBatchSize(int minBatchSize) {
      this.minBatchSize = minBatchSize;
      optBits |= OPT_BIT_MIN_BATCH_SIZE;
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#jobId() jobId} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#jobId() jobId}.</em>
     * @param jobId The value for jobId 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder jobId(JobId jobId) {
      this.jobId = Objects.requireNonNull(jobId, "jobId");
      return this;
    }

    /**
     * Adds one element to {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels} list.
     * @param element A nodeLabels element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addNodeLabel(String element) {
      if (this.nodeLabels == null) {
        this.nodeLabels = new ArrayList<String>();
      }
      this.nodeLabels.add(Objects.requireNonNull(element, "nodeLabels element"));
      optBits |= OPT_BIT_NODE_LABELS;
      return this;
    }

    /**
     * Adds elements to {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels} list.
     * @param elements An array of nodeLabels elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addNodeLabels(String... elements) {
      if (this.nodeLabels == null) {
        this.nodeLabels = new ArrayList<String>();
      }
      for (String element : elements) {
        this.nodeLabels.add(Objects.requireNonNull(element, "nodeLabels element"));
      }
      optBits |= OPT_BIT_NODE_LABELS;
      return this;
    }


    /**
     * Sets or replaces all elements for {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels} list.
     * @param elements An iterable of nodeLabels elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder nodeLabels(Iterable<String> elements) {
      this.nodeLabels = new ArrayList<String>();
      return addAllNodeLabels(elements);
    }

    /**
     * Adds elements to {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#nodeLabels() nodeLabels} list.
     * @param elements An iterable of nodeLabels elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllNodeLabels(Iterable<String> elements) {
      Objects.requireNonNull(elements, "nodeLabels element");
      if (this.nodeLabels == null) {
        this.nodeLabels = new ArrayList<String>();
      }
      for (String element : elements) {
        this.nodeLabels.add(Objects.requireNonNull(element, "nodeLabels element"));
      }
      optBits |= OPT_BIT_NODE_LABELS;
      return this;
    }

    /**
     * Adds one element to {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes} list.
     * @param element A relationshipTypes element
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addRelationshipType(String element) {
      if (this.relationshipTypes == null) {
        this.relationshipTypes = new ArrayList<String>();
      }
      this.relationshipTypes.add(Objects.requireNonNull(element, "relationshipTypes element"));
      optBits |= OPT_BIT_RELATIONSHIP_TYPES;
      return this;
    }

    /**
     * Adds elements to {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes} list.
     * @param elements An array of relationshipTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addRelationshipTypes(String... elements) {
      if (this.relationshipTypes == null) {
        this.relationshipTypes = new ArrayList<String>();
      }
      for (String element : elements) {
        this.relationshipTypes.add(Objects.requireNonNull(element, "relationshipTypes element"));
      }
      optBits |= OPT_BIT_RELATIONSHIP_TYPES;
      return this;
    }


    /**
     * Sets or replaces all elements for {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes} list.
     * @param elements An iterable of relationshipTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder relationshipTypes(Iterable<String> elements) {
      this.relationshipTypes = new ArrayList<String>();
      return addAllRelationshipTypes(elements);
    }

    /**
     * Adds elements to {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipTypes() relationshipTypes} list.
     * @param elements An iterable of relationshipTypes elements
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder addAllRelationshipTypes(Iterable<String> elements) {
      Objects.requireNonNull(elements, "relationshipTypes element");
      if (this.relationshipTypes == null) {
        this.relationshipTypes = new ArrayList<String>();
      }
      for (String element : elements) {
        this.relationshipTypes.add(Objects.requireNonNull(element, "relationshipTypes element"));
      }
      optBits |= OPT_BIT_RELATIONSHIP_TYPES;
      return this;
    }

    /**
     * Initializes the optional value {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipWeightProperty() relationshipWeightProperty} to relationshipWeightProperty.
     * @param relationshipWeightProperty The value for relationshipWeightProperty, {@code null} is accepted as {@code java.util.Optional.empty()}
     * @return {@code this} builder for chained invocation
     */
    public final Builder relationshipWeightProperty(String relationshipWeightProperty) {
      this.relationshipWeightProperty = relationshipWeightProperty;
      return this;
    }

    /**
     * Initializes the optional value {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#relationshipWeightProperty() relationshipWeightProperty} to relationshipWeightProperty.
     * @param relationshipWeightProperty The value for relationshipWeightProperty
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder relationshipWeightProperty(Optional<String> relationshipWeightProperty) {
      this.relationshipWeightProperty = relationshipWeightProperty.orElse(null);
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#maxIterations() maxIterations} attribute.
     * @param maxIterations The value for maxIterations 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder maxIterations(int maxIterations) {
      this.maxIterations = maxIterations;
      initBits &= ~INIT_BIT_MAX_ITERATIONS;
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#partitioning() partitioning} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#partitioning() partitioning}.</em>
     * @param partitioning The value for partitioning 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder partitioning(Partitioning partitioning) {
      this.partitioning = Objects.requireNonNull(partitioning, "partitioning");
      return this;
    }

    /**
     * Initializes the optional value {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#arrowConnectionInfo() arrowConnectionInfo} to arrowConnectionInfo.
     * @param arrowConnectionInfo The value for arrowConnectionInfo, {@code null} is accepted as {@code java.util.Optional.empty()}
     * @return {@code this} builder for chained invocation
     */
    public final Builder arrowConnectionInfo(WriteConfig.ArrowConnectionInfo arrowConnectionInfo) {
      this.arrowConnectionInfo = arrowConnectionInfo;
      return this;
    }

    /**
     * Initializes the optional value {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#arrowConnectionInfo() arrowConnectionInfo} to arrowConnectionInfo.
     * @param arrowConnectionInfo The value for arrowConnectionInfo
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder arrowConnectionInfo(Optional<? extends WriteConfig.ArrowConnectionInfo> arrowConnectionInfo) {
      this.arrowConnectionInfo = arrowConnectionInfo.orElse(null);
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeConcurrency() writeConcurrency} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeConcurrency() writeConcurrency}.</em>
     * @param writeConcurrency The value for writeConcurrency 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder writeConcurrency(int writeConcurrency) {
      this.writeConcurrency = writeConcurrency;
      optBits |= OPT_BIT_WRITE_CONCURRENCY;
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#mutateProperty() mutateProperty} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#mutateProperty() mutateProperty}.</em>
     * @param mutateProperty The value for mutateProperty 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder mutateProperty(String mutateProperty) {
      this.mutateProperty = Objects.requireNonNull(mutateProperty, "mutateProperty");
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeProperty() writeProperty} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#writeProperty() writeProperty}.</em>
     * @param writeProperty The value for writeProperty 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder writeProperty(String writeProperty) {
      this.writeProperty = Objects.requireNonNull(writeProperty, "writeProperty");
      return this;
    }

    /**
     * Initializes the value for the {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#seedProperty() seedProperty} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig#seedProperty() seedProperty}.</em>
     * @param seedProperty The value for seedProperty (can be {@code null})
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder seedProperty(@Nullable String seedProperty) {
      this.seedProperty = seedProperty;
      optBits |= OPT_BIT_SEED_PROPERTY;
      return this;
    }

    /**
     * Clear the builder to the initial state.
     * @return {@code this} builder for use in a chained invocation
     */
    public Builder clear() {
      initBits = 0x1L;
      optBits = 0;
      this.configKeys = null;
      this.logProgress = false;
      this.sudo = false;
      this.usernameOverride = null;
      this.concurrency = 0;
      this.minBatchSize = 0;
      this.jobId = null;
      if (nodeLabels != null) {
        nodeLabels.clear();
      }
      if (relationshipTypes != null) {
        relationshipTypes.clear();
      }
      this.relationshipWeightProperty = null;
      this.maxIterations = 0;
      this.partitioning = null;
      this.arrowConnectionInfo = null;
      this.writeConcurrency = 0;
      this.mutateProperty = null;
      this.writeProperty = null;
      this.seedProperty = null;
      return this;
    }

    /**
     * Builds a new {@link FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig FrequentSubgraphMiningPregelConfig}.
     * @return An immutable instance of FrequentSubgraphMiningPregelConfig
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public FrequentSubgraphMiningPregel.FrequentSubgraphMiningPregelConfig build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return ImmutableFrequentSubgraphMiningPregelConfig.validate(new ImmutableFrequentSubgraphMiningPregelConfig(this));
    }

    private boolean logProgressIsSet() {
      return (optBits & OPT_BIT_LOG_PROGRESS) != 0;
    }

    private boolean sudoIsSet() {
      return (optBits & OPT_BIT_SUDO) != 0;
    }

    private boolean concurrencyIsSet() {
      return (optBits & OPT_BIT_CONCURRENCY) != 0;
    }

    private boolean minBatchSizeIsSet() {
      return (optBits & OPT_BIT_MIN_BATCH_SIZE) != 0;
    }

    private boolean nodeLabelsIsSet() {
      return (optBits & OPT_BIT_NODE_LABELS) != 0;
    }

    private boolean relationshipTypesIsSet() {
      return (optBits & OPT_BIT_RELATIONSHIP_TYPES) != 0;
    }

    private boolean writeConcurrencyIsSet() {
      return (optBits & OPT_BIT_WRITE_CONCURRENCY) != 0;
    }

    private boolean seedPropertyIsSet() {
      return (optBits & OPT_BIT_SEED_PROPERTY) != 0;
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_MAX_ITERATIONS) != 0) attributes.add("maxIterations");
      return "Cannot build FrequentSubgraphMiningPregelConfig, some of required attributes are not set " + attributes;
    }
  }

  private static <T> List<T> createSafeList(Iterable<? extends T> iterable, boolean checkNulls, boolean skipNulls) {
    ArrayList<T> list;
    if (iterable instanceof Collection<?>) {
      int size = ((Collection<?>) iterable).size();
      if (size == 0) return Collections.emptyList();
      list = new ArrayList<>();
    } else {
      list = new ArrayList<>();
    }
    for (T element : iterable) {
      if (skipNulls && element == null) continue;
      if (checkNulls) Objects.requireNonNull(element, "element");
      list.add(element);
    }
    return list;
  }

  private static <T> List<T> createUnmodifiableList(boolean clone, List<T> list) {
    switch(list.size()) {
    case 0: return Collections.emptyList();
    case 1: return Collections.singletonList(list.get(0));
    default:
      if (clone) {
        return Collections.unmodifiableList(new ArrayList<>(list));
      } else {
        if (list instanceof ArrayList<?>) {
          ((ArrayList<?>) list).trimToSize();
        }
        return Collections.unmodifiableList(list);
      }
    }
  }
}
