package net.minecraft.world.entity.player;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public class StackedContents<T> {
    public final Reference2IntOpenHashMap<T> amounts = new Reference2IntOpenHashMap<>();

    boolean hasAnyAmount(T item) {
        return this.amounts.getInt(item) > 0;
    }

    boolean hasAtLeast(T item, int amount) {
        return this.amounts.getInt(item) >= amount;
    }

    void take(T item, int amount) {
        int i = this.amounts.addTo(item, -amount);
        if (i < amount) {
            throw new IllegalStateException("Took " + amount + " items, but only had " + i);
        }
    }

    void put(T item, int amount) {
        this.amounts.addTo(item, amount);
    }

    public boolean tryPick(List<StackedContents.IngredientInfo<T>> ingredients, int amount, @Nullable StackedContents.Output<T> output) {
        return new StackedContents.RecipePicker(ingredients).tryPick(amount, output);
    }

    public int tryPickAll(List<StackedContents.IngredientInfo<T>> ingredients, int amount, @Nullable StackedContents.Output<T> output) {
        return new StackedContents.RecipePicker(ingredients).tryPickAll(amount, output);
    }

    public void clear() {
        this.amounts.clear();
    }

    public void account(T item, int amount) {
        this.put(item, amount);
    }

    public static record IngredientInfo<T>(List<T> allowedItems) {
        public IngredientInfo(List<T> allowedItems) {
            if (allowedItems.isEmpty()) {
                throw new IllegalArgumentException("Ingredients can't be empty");
            } else {
                this.allowedItems = allowedItems;
            }
        }
    }

    @FunctionalInterface
    public interface Output<T> {
        void accept(T item);
    }

    class RecipePicker {
        private final List<StackedContents.IngredientInfo<T>> ingredients;
        private final int ingredientCount;
        private final List<T> items;
        private final int itemCount;
        private final BitSet data;
        private final IntList path = new IntArrayList();

        public RecipePicker(List<StackedContents.IngredientInfo<T>> ingredients) {
            this.ingredients = ingredients;
            this.ingredientCount = this.ingredients.size();
            this.items = this.getUniqueAvailableIngredientItems();
            this.itemCount = this.items.size();
            this.data = new BitSet(
                this.visitedIngredientCount() + this.visitedItemCount() + this.satisfiedCount() + this.connectionCount() + this.residualCount()
            );
            this.setInitialConnections();
        }

        private void setInitialConnections() {
            for (int i = 0; i < this.ingredientCount; i++) {
                List<T> list = this.ingredients.get(i).allowedItems();

                for (int j = 0; j < this.itemCount; j++) {
                    if (list.contains(this.items.get(j))) {
                        this.setConnection(j, i);
                    }
                }
            }
        }

        public boolean tryPick(int amount, @Nullable StackedContents.Output<T> output) {
            if (amount <= 0) {
                return true;
            } else {
                int i = 0;

                while (true) {
                    IntList intlist = this.tryAssigningNewItem(amount);
                    if (intlist == null) {
                        boolean flag = i == this.ingredientCount;
                        boolean flag1 = flag && output != null;
                        this.clearAllVisited();
                        this.clearSatisfied();

                        for (int k1 = 0; k1 < this.ingredientCount; k1++) {
                            for (int l1 = 0; l1 < this.itemCount; l1++) {
                                if (this.isAssigned(l1, k1)) {
                                    this.unassign(l1, k1);
                                    StackedContents.this.put(this.items.get(l1), amount);
                                    if (flag1) {
                                        output.accept(this.items.get(l1));
                                    }
                                    break;
                                }
                            }
                        }

                        assert this.data.get(this.residualOffset(), this.residualOffset() + this.residualCount()).isEmpty();

                        return flag;
                    }

                    int j = intlist.getInt(0);
                    StackedContents.this.take(this.items.get(j), amount);
                    int k = intlist.size() - 1;
                    this.setSatisfied(intlist.getInt(k));
                    i++;

                    for (int l = 0; l < intlist.size() - 1; l++) {
                        if (isPathIndexItem(l)) {
                            int i1 = intlist.getInt(l);
                            int j1 = intlist.getInt(l + 1);
                            this.assign(i1, j1);
                        } else {
                            int i2 = intlist.getInt(l + 1);
                            int j2 = intlist.getInt(l);
                            this.unassign(i2, j2);
                        }
                    }
                }
            }
        }

        private static boolean isPathIndexItem(int index) {
            return (index & 1) == 0;
        }

        private List<T> getUniqueAvailableIngredientItems() {
            Set<T> set = new ReferenceOpenHashSet<>();

            for (StackedContents.IngredientInfo<T> ingredientinfo : this.ingredients) {
                set.addAll(ingredientinfo.allowedItems());
            }

            set.removeIf(p_365026_ -> !StackedContents.this.hasAnyAmount((T)p_365026_));
            return List.copyOf(set);
        }

        @Nullable
        private IntList tryAssigningNewItem(int amount) {
            this.clearAllVisited();

            for (int i = 0; i < this.itemCount; i++) {
                if (StackedContents.this.hasAtLeast(this.items.get(i), amount)) {
                    IntList intlist = this.findNewItemAssignmentPath(i);
                    if (intlist != null) {
                        return intlist;
                    }
                }
            }

            return null;
        }

        @Nullable
        private IntList findNewItemAssignmentPath(int amount) {
            this.path.clear();
            this.visitItem(amount);
            this.path.add(amount);

            while (!this.path.isEmpty()) {
                int i = this.path.size();
                if (isPathIndexItem(i - 1)) {
                    int l = this.path.getInt(i - 1);

                    for (int j1 = 0; j1 < this.ingredientCount; j1++) {
                        if (!this.hasVisitedIngredient(j1) && this.hasConnection(l, j1) && !this.isAssigned(l, j1)) {
                            this.visitIngredient(j1);
                            this.path.add(j1);
                            break;
                        }
                    }
                } else {
                    int j = this.path.getInt(i - 1);
                    if (!this.isSatisfied(j)) {
                        return this.path;
                    }

                    for (int k = 0; k < this.itemCount; k++) {
                        if (!this.hasVisitedItem(k) && this.isAssigned(k, j)) {
                            assert this.hasConnection(k, j);

                            this.visitItem(k);
                            this.path.add(k);
                            break;
                        }
                    }
                }

                int i1 = this.path.size();
                if (i1 == i) {
                    this.path.removeInt(i1 - 1);
                }
            }

            return null;
        }

        private int visitedIngredientOffset() {
            return 0;
        }

        private int visitedIngredientCount() {
            return this.ingredientCount;
        }

        private int visitedItemOffset() {
            return this.visitedIngredientOffset() + this.visitedIngredientCount();
        }

        private int visitedItemCount() {
            return this.itemCount;
        }

        private int satisfiedOffset() {
            return this.visitedItemOffset() + this.visitedItemCount();
        }

        private int satisfiedCount() {
            return this.ingredientCount;
        }

        private int connectionOffset() {
            return this.satisfiedOffset() + this.satisfiedCount();
        }

        private int connectionCount() {
            return this.ingredientCount * this.itemCount;
        }

        private int residualOffset() {
            return this.connectionOffset() + this.connectionCount();
        }

        private int residualCount() {
            return this.ingredientCount * this.itemCount;
        }

        private boolean isSatisfied(int stackingIndex) {
            return this.data.get(this.getSatisfiedIndex(stackingIndex));
        }

        private void setSatisfied(int stackingIndex) {
            this.data.set(this.getSatisfiedIndex(stackingIndex));
        }

        private int getSatisfiedIndex(int stackingIndex) {
            assert stackingIndex >= 0 && stackingIndex < this.ingredientCount;

            return this.satisfiedOffset() + stackingIndex;
        }

        private void clearSatisfied() {
            this.clearRange(this.satisfiedOffset(), this.satisfiedCount());
        }

        private void setConnection(int itemIndex, int ingredientIndex) {
            this.data.set(this.getConnectionIndex(itemIndex, ingredientIndex));
        }

        private boolean hasConnection(int itemIndex, int ingredientIndex) {
            return this.data.get(this.getConnectionIndex(itemIndex, ingredientIndex));
        }

        private int getConnectionIndex(int itemIndex, int ingredientIndex) {
            assert itemIndex >= 0 && itemIndex < this.itemCount;

            assert ingredientIndex >= 0 && ingredientIndex < this.ingredientCount;

            return this.connectionOffset() + itemIndex * this.ingredientCount + ingredientIndex;
        }

        private boolean isAssigned(int itemIndex, int ingredientIndex) {
            return this.data.get(this.getResidualIndex(itemIndex, ingredientIndex));
        }

        private void assign(int itemIndex, int ingredientIndex) {
            int i = this.getResidualIndex(itemIndex, ingredientIndex);

            assert !this.data.get(i);

            this.data.set(i);
        }

        private void unassign(int itemIndex, int ingredientIndex) {
            int i = this.getResidualIndex(itemIndex, ingredientIndex);

            assert this.data.get(i);

            this.data.clear(i);
        }

        private int getResidualIndex(int itemIndex, int ingredientIndex) {
            assert itemIndex >= 0 && itemIndex < this.itemCount;

            assert ingredientIndex >= 0 && ingredientIndex < this.ingredientCount;

            return this.residualOffset() + itemIndex * this.ingredientCount + ingredientIndex;
        }

        private void visitIngredient(int ingredientIndex) {
            this.data.set(this.getVisitedIngredientIndex(ingredientIndex));
        }

        private boolean hasVisitedIngredient(int ingredientIndex) {
            return this.data.get(this.getVisitedIngredientIndex(ingredientIndex));
        }

        private int getVisitedIngredientIndex(int ingredientIndex) {
            assert ingredientIndex >= 0 && ingredientIndex < this.ingredientCount;

            return this.visitedIngredientOffset() + ingredientIndex;
        }

        private void visitItem(int itemIndex) {
            this.data.set(this.getVisitiedItemIndex(itemIndex));
        }

        private boolean hasVisitedItem(int itemIndex) {
            return this.data.get(this.getVisitiedItemIndex(itemIndex));
        }

        private int getVisitiedItemIndex(int itemIndex) {
            assert itemIndex >= 0 && itemIndex < this.itemCount;

            return this.visitedItemOffset() + itemIndex;
        }

        private void clearAllVisited() {
            this.clearRange(this.visitedIngredientOffset(), this.visitedIngredientCount());
            this.clearRange(this.visitedItemOffset(), this.visitedItemCount());
        }

        private void clearRange(int offset, int count) {
            this.data.clear(offset, offset + count);
        }

        public int tryPickAll(int amount, @Nullable StackedContents.Output<T> output) {
            int i = 0;
            int j = Math.min(amount, this.getMinIngredientCount()) + 1;

            while (true) {
                int k = (i + j) / 2;
                if (this.tryPick(k, null)) {
                    if (j - i <= 1) {
                        if (k > 0) {
                            this.tryPick(k, output);
                        }

                        return k;
                    }

                    i = k;
                } else {
                    j = k;
                }
            }
        }

        private int getMinIngredientCount() {
            int i = Integer.MAX_VALUE;

            for (StackedContents.IngredientInfo<T> ingredientinfo : this.ingredients) {
                int j = 0;

                for (T t : ingredientinfo.allowedItems()) {
                    j = Math.max(j, StackedContents.this.amounts.getInt(t));
                }

                if (i > 0) {
                    i = Math.min(i, j);
                }
            }

            return i;
        }
    }
}
