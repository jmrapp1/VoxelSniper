package com.thevoxelbox.voxelsniper.brush;

import com.thevoxelbox.voxelsniper.vData;
import com.thevoxelbox.voxelsniper.vMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;

/**
 *
 * @author Mick
 */
public class TreeSnipe extends Brush {

    private TreeType treeType = TreeType.TREE;

    public TreeSnipe() {
        name = "Tree Snipe";
    }

    @Override
    protected void arrow(com.thevoxelbox.voxelsniper.vData v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        by = getLocation(v);
        single(v);
    }

    @Override
    protected void powder(com.thevoxelbox.voxelsniper.vData v) {
        bx = tb.getX();
        by = tb.getY();
        bz = tb.getZ();
        single(v);
    }

    @Override
    public void info(vMessage vm) {
        vm.brushName(name);
        printTreeType(vm);
    }

    @Override
    public void parameters(String[] par, com.thevoxelbox.voxelsniper.vData v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Tree snipe brush:");
            v.sendMessage(ChatColor.AQUA + "/b t treetype");
            printTreeType(v.vm);
            return;
        }
        for (int x = 1; x < par.length; x++) {
            if (par[x].equals("bigtree")) {
                treeType = TreeType.BIG_TREE;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("birch")) {
                treeType = TreeType.BIRCH;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("redwood")) {
                treeType = TreeType.REDWOOD;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("tallredwood")) {
                treeType = TreeType.TALL_REDWOOD;
                printTreeType(v.vm);
                break;
            }
            if (par[x].equals("tree")) {
                treeType = TreeType.TREE;
                printTreeType(v.vm);
                break;
            }
        }
    }

    public void single(vData v) {
        try {
            //int id = clampY(bx,by,bz).getTypeId();
            if (clampY(bx, tb.getY(), bz).getTypeId() != 18) {
                clampY(bx, tb.getY(), bz).setTypeId(3);
            } //makes its own dirt so it can go anywhere
            w.generateTree(new Location(w, (double) bx, (double) by, (double) bz), treeType);
            //s.getBlockAt(bx,by,bz).setTypeId(id); //replace the original block that was there.
        } catch (Exception e) {
            v.sendMessage("Nope");
        }
    }

    private int getLocation(vData v) {
        for (int i = 1; i < (127 - by); i++) {
            if (clampY(bx, by + i, bz).getType() == Material.AIR) { // Dont you mean != AIR ?  -- prz
                return by + i;                                            // But why are you even grabbing the highest Y ?
            }
        }
        return by;
    }

    private void printTreeType(vMessage vm) {
        switch (treeType) {
            case BIG_TREE:
                vm.custom(ChatColor.GRAY + "bigtree " + ChatColor.DARK_GRAY + "birch " + "redwood " + "tallredwood " + "tree");
                break;

            case BIRCH:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + ChatColor.GRAY + "birch " + ChatColor.DARK_GRAY + "redwood " + "tallredwood " + "tree");
                break;

            case REDWOOD:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + "birch " + ChatColor.GRAY + "redwood " + ChatColor.DARK_GRAY + "tallredwood " + "tree");
                break;

            case TALL_REDWOOD:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + "birch " + "redwood " + ChatColor.GRAY + "tallredwood " + ChatColor.DARK_GRAY + "tree");
                break;

            case TREE:
                vm.custom(ChatColor.DARK_GRAY + "bigtree " + "birch " + "redwood " + "tallredwood " + ChatColor.GRAY + "tree");
                break;
        }
    }
}
