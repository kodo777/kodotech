package kodo777.btatech.block;

import net.minecraft.src.BlockRotatable;
import net.minecraft.src.Material;

public class BlockSteamBoiler extends BlockRotatable {

    public BlockSteamBoiler(int i, Material material){ super(i, material);}
    @Override
    public int getBlockTextureFromSideAndMetadata(int side, int meta) {

        if (side == 0) {
            return texCoordToIndex(17, 6);
        } else {
            if (side == 1) {
                return texCoordToIndex(31, 2);
            }
            switch (meta) {

                case 2:
                    if (side == 2)
                        return texCoordToIndex(31, 0);
                    else return texCoordToIndex(31, 1);

                case 3:
                    if (side == 3)
                        return texCoordToIndex(31, 0);
                    else return texCoordToIndex(31, 1);

                default:
                    if (side == 4)
                        return texCoordToIndex(31, 0);
                    else return texCoordToIndex(31, 1);

                case 5:
                    if (side == 5)
                        return texCoordToIndex(31, 0);
                    else return texCoordToIndex(31, 1);
            }
        }
    }
}
