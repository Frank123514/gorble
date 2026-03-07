package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.context.ContextKey;
import net.minecraft.util.context.ContextKeySet;

/**
 * Context for validating loot tables. Loot tables are validated recursively by checking that all functions, conditions, etc. (implementing {@link LootContextUser}) are valid according to their LootTable's {@link LootContextParamSet}.
 */
public class ValidationContext {
    private final ProblemReporter reporter;
    private final ContextKeySet contextKeySet;
    private final Optional<HolderGetter.Provider> resolver;
    private final Set<ResourceKey<?>> visitedElements;

    public ValidationContext(ProblemReporter reporter, ContextKeySet contextKeySet, HolderGetter.Provider resolver) {
        this(reporter, contextKeySet, Optional.of(resolver), Set.of());
    }

    public ValidationContext(ProblemReporter reporter, ContextKeySet contextKeySet) {
        this(reporter, contextKeySet, Optional.empty(), Set.of());
    }

    private ValidationContext(ProblemReporter reporter, ContextKeySet contextKeySet, Optional<HolderGetter.Provider> resolver, Set<ResourceKey<?>> visitedElements) {
        this.reporter = reporter;
        this.contextKeySet = contextKeySet;
        this.resolver = resolver;
        this.visitedElements = visitedElements;
    }

    /**
     * Create a new ValidationContext with {@code childName} being added to the context.
     */
    public ValidationContext forChild(String childName) {
        return new ValidationContext(this.reporter.forChild(childName), this.contextKeySet, this.resolver, this.visitedElements);
    }

    public ValidationContext enterElement(String name, ResourceKey<?> key) {
        Set<ResourceKey<?>> set = ImmutableSet.<ResourceKey<?>>builder().addAll(this.visitedElements).add(key).build();
        return new ValidationContext(this.reporter.forChild(name), this.contextKeySet, this.resolver, set);
    }

    public boolean hasVisitedElement(ResourceKey<?> key) {
        return this.visitedElements.contains(key);
    }

    /**
     * Report a problem to this ValidationContext.
     */
    public void reportProblem(String problem) {
        this.reporter.report(problem);
    }

    public void validateContextUsage(LootContextUser user) {
        Set<ContextKey<?>> set = user.getReferencedContextParams();
        Set<ContextKey<?>> set1 = Sets.difference(set, this.contextKeySet.allowed());
        if (!set1.isEmpty()) {
            this.reporter.report("Parameters " + set1 + " are not provided in this context");
        }
    }

    public HolderGetter.Provider resolver() {
        return this.resolver.orElseThrow(() -> new UnsupportedOperationException("References not allowed"));
    }

    public boolean allowsReferences() {
        return this.resolver.isPresent();
    }

    public ValidationContext setContextKeySet(ContextKeySet contextKeySet) {
        return new ValidationContext(this.reporter, contextKeySet, this.resolver, this.visitedElements);
    }

    public ProblemReporter reporter() {
        return this.reporter;
    }
}
