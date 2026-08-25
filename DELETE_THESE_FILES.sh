#!/usr/bin/env bash
# Run this from the root of your gorble project (where /src lives).
# Deletes the now-unused female_10.png ... female_16.png textures
# for every culture, since the game only loads female_01-09 now
# (FEMALE_VARIANT_COUNT was dropped from 12 to 9 in the entity classes).

set -e

CULTURES="northman stormlorder reachman valeman dornishman ironborn riverlander westerman"
TEX_ROOT="src/main/resources/assets/got/textures/entity/npc/smallfolk"

for cu in $CULTURES; do
  for i in 10 11 12 13 14 15 16; do
    f="$TEX_ROOT/$cu/generated/female_${i}.png"
    if [ -f "$f" ]; then
      rm "$f"
      echo "deleted $f"
    fi
  done
done

echo "done."
