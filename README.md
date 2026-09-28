# NosiFix

NosiFix adds a random assortment of fixes for other mods that have not been updated by their authors.

## Current Fixes
[Chisels & Bits](https://www.curseforge.com/minecraft/mc-mods/chisels-bits): Chisels & Bits pins its vanilla textures to the top of the resource pack list and makes it impossible to move them down, which prevents users of resource packs with modded addons, such as Sphax PureBDcraft, from using their custom textures for Chisels & Bits. NosiFix removes this fixed setting and allows users to move it down so modded textures can load above it.

[WTHIT](https://www.curseforge.com/minecraft/mc-mods/wthit-forge): WTHIT has a bug where block capabilities do not update in the tooltip and will only display the information of the first block the player looks at. NosiFix implements [PR #372](https://github.com/badasintended/wthit/pull/372), which fixes this issue.
