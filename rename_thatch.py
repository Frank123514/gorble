#!/usr/bin/env python3
"""
rename_thatch.py

Renames every reference/instance of the "light_thatch" and "dark_thatch"
blocks to "thatch" and "weathered_thatch" respectively, across:
  - Java source (constant names, registerBlock string ids, ModBlocks/
    ModItems/ModTabs references)
  - Resource JSON (blockstates, block/item models, item defs, loot
    tables, recipes, lang entries) -- including string values inside
    the JSON, not just filenames
  - Texture files (renamed on disk, e.g. light_thatch.png -> thatch.png)
  - Any other text file that happens to reference these ids

It does NOT touch anything unrelated -- only exact matches of the
light_thatch / dark_thatch tokens (in snake_case, UPPER_SNAKE_CASE,
PascalCase, and "Title Case With Spaces" forms) are replaced.

USAGE
-----
Run this from IntelliJ (right-click > Run 'rename_thatch.py'), or from
a terminal, from your project root (the folder that contains "src"):

    python rename_thatch.py

By default it operates on the current working directory. To point it
at a specific project root instead:

    python rename_thatch.py /path/to/gorble-master

Add --dry-run to preview every change without touching any files:

    python rename_thatch.py --dry-run
"""

import os
import sys
from pathlib import Path

# ---------------------------------------------------------------------
# 1. Replacement pairs, longest/most-specific first so nothing partially
#    matches and gets left in a broken state.
# ---------------------------------------------------------------------
REPLACEMENTS = [
    # UPPER_SNAKE_CASE (Java constants: LIGHT_THATCH_SLAB -> THATCH_SLAB)
    ("LIGHT_THATCH", "THATCH"),
    ("DARK_THATCH", "WEATHERED_THATCH"),
    # snake_case (block/item ids, file paths, json keys: light_thatch_slab -> thatch_slab)
    ("light_thatch", "thatch"),
    ("dark_thatch", "weathered_thatch"),
    # "Title Case With Spaces" (lang.json display names: "Light Thatch Slab" -> "Thatch Slab")
    ("Light Thatch", "Thatch"),
    ("Dark Thatch", "Weathered Thatch"),
    # PascalCase / no-separator (in case any class or method names use it)
    ("LightThatch", "Thatch"),
    ("DarkThatch", "WeatheredThatch"),
]

# Directories we never want to touch.
SKIP_DIRS = {".git", ".gradle", ".idea", "build", "out", "target", "node_modules", ".vscode"}

# Only attempt a text-content rewrite on files with these extensions.
# (Everything else -- .png, .jar, .class, etc -- is still eligible for
# renaming, just not content-edited.)
TEXT_EXTENSIONS = {
    ".java", ".json", ".json5", ".mcmeta", ".txt", ".md",
    ".properties", ".toml", ".gradle", ".kts", ".cfg", ".yml", ".yaml",
}


def apply_replacements(text):
    """Apply every replacement pair to text. Returns (new_text, hit_count)."""
    hits = 0
    for old, new in REPLACEMENTS:
        count = text.count(old)
        if count:
            hits += count
            text = text.replace(old, new)
    return text, hits


def new_filename(name):
    for old, new in REPLACEMENTS:
        name = name.replace(old, new)
    return name


def rewrite_file_contents(path, dry_run):
    if path.suffix.lower() not in TEXT_EXTENSIONS:
        return 0
    try:
        original = path.read_text(encoding="utf-8")
    except (UnicodeDecodeError, PermissionError):
        return 0

    new_text, hits = apply_replacements(original)
    if hits and not dry_run:
        path.write_text(new_text, encoding="utf-8")
    return hits


def collect_files(root):
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames[:] = [d for d in dirnames if d not in SKIP_DIRS]
        for fname in filenames:
            yield Path(dirpath) / fname


def main():
    args = sys.argv[1:]
    dry_run = "--dry-run" in args
    args = [a for a in args if a != "--dry-run"]
    root = Path(args[0]).resolve() if args else Path.cwd()

    if not root.exists():
        print(f"ERROR: path does not exist: {root}")
        sys.exit(1)

    print(f"{'[DRY RUN] ' if dry_run else ''}Scanning: {root}\n")

    # ---- Pass 1: rewrite file CONTENTS first (paths inside files still
    #      reference the OLD filenames at this point, which is fine --
    #      we're replacing the token, not the path structure) ----
    content_hits_total = 0
    files_with_content_changes = []
    for path in collect_files(root):
        hits = rewrite_file_contents(path, dry_run)
        if hits:
            content_hits_total += hits
            files_with_content_changes.append((path, hits))

    # ---- Pass 2: rename files/dirs whose name contains the old tokens.
    #      Walk bottom-up so we rename files before their parent dirs. ----
    renamed = []
    for dirpath, dirnames, filenames in os.walk(root, topdown=False):
        dirnames[:] = [d for d in dirnames if d not in SKIP_DIRS]
        dp = Path(dirpath)

        for fname in filenames:
            new_name = new_filename(fname)
            if new_name != fname:
                src = dp / fname
                dst = dp / new_name
                if dst.exists():
                    print(f"  !! SKIPPING rename, target already exists: {dst}")
                    continue
                renamed.append((src, dst))
                if not dry_run:
                    src.rename(dst)

        # directory itself (rare, but handle it: e.g. a folder literally
        # named "light_thatch")
        base = dp.name
        new_base = new_filename(base)
        if new_base != base and dp != root:
            new_dp = dp.parent / new_base
            if not new_dp.exists():
                renamed.append((dp, new_dp))
                if not dry_run:
                    dp.rename(new_dp)

    # ---- report ----
    print(f"Content changes: {len(files_with_content_changes)} file(s), {content_hits_total} replacement(s)")
    for path, hits in files_with_content_changes:
        print(f"  ~ {path.relative_to(root)}  ({hits} replacement{'s' if hits != 1 else ''})")

    print(f"\nRenamed: {len(renamed)} file(s)/folder(s)")
    for src, dst in renamed:
        print(f"  -> {src.relative_to(root)}  =>  {dst.relative_to(root)}")

    if dry_run:
        print("\nDry run complete -- nothing was actually written. Re-run without --dry-run to apply.")
    else:
        print("\nDone.")


if __name__ == "__main__":
    main()
