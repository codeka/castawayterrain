package com.codeka.castawayterrain.world;

import net.minecraft.world.World;
import net.minecraft.world.level.LevelGeneratorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CastawayLevelType {
    private static final Logger L = LogManager.getLogger();

    public static LevelGeneratorType getType() {
        LevelGeneratorType val;
        int id=7;
        Field types = null;

        for (Field f : LevelGeneratorType.class.getDeclaredFields()) {
            if (f.getType()==LevelGeneratorType[].class) {
                types = f;
            }
        }

        if (types != null) {
            L.info("count =  " + LevelGeneratorType.TYPES.length);
            L.info("field found");

            try {
                LevelGeneratorType newTypes[] = new LevelGeneratorType[LevelGeneratorType.TYPES.length+1];

                System.arraycopy(LevelGeneratorType.TYPES, 0, newTypes, 0, LevelGeneratorType.TYPES.length);
                newTypes[newTypes.length-1] = null;

                types.setAccessible(true);
                Field modifies = Field.class.getDeclaredField("modifiers");
                modifies.setAccessible(true);

                modifies.setInt(types, types.getModifiers() & ~Modifier.FINAL);
                types.set(null,newTypes);
                id=LevelGeneratorType.TYPES.length - 1;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                L.error("Unable to find Generator Type Field");
                return null;
            }

            L.info("count =  " + LevelGeneratorType.TYPES.length + " / id = " + id);
        } else {
            L.error("Unable to find Generator Type Field");
            return null;
        }

        try {
            Constructor<LevelGeneratorType> c =
                    LevelGeneratorType.class.getDeclaredConstructor(int.class, String.class);
            c.setAccessible(true);
            L.info("Level Type Constructor Found");
            val = c.newInstance(id, "castaway");
            val.setCustomizable(true);
        } catch (Exception e) {
            L.error("Unable to get and call LevelGeneratorType Constructor",e);
            return null;
        }

        return val;
    }
}
