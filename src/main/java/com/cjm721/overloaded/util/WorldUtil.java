package com.cjm721.overloaded.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class WorldUtil {

  public static HitResult rayTraceWithEntities(
      @Nonnull Level world,
      @Nonnull Vec3 startingLocation,
      @Nonnull Vec3 direction,
      @Nonnull Entity excludedEntity,
      double maxDistance) {
      Vec3 endingLocation = startingLocation.add(direction.scale(maxDistance));
      BlockHitResult rayTraceResult =
        world.clip(
            new ClipContext(
                startingLocation,
                endingLocation,
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                excludedEntity));

    if (rayTraceResult.getType() != HitResult.Type.MISS) {
      endingLocation =
          new Vec3(
              rayTraceResult.getLocation().x,
              rayTraceResult.getLocation().y,
              rayTraceResult.getLocation().z);
    }

    Entity entity = null;

    double[] vertexes = new double[6];

    vertexes[0] = startingLocation.x - 0.5D;
    vertexes[1] = startingLocation.y - 0.5D;
    vertexes[2] = startingLocation.z - 0.5D;
    vertexes[3] = startingLocation.x + 0.5D;
    vertexes[4] = startingLocation.y + 0.5D;
    vertexes[5] = startingLocation.z + 0.5D;

    int length = (int) Math.ceil(startingLocation.subtract(endingLocation).length());

    for (double i = 0; i < length; i += 0.1) {
      vertexes[0] += direction.x * i;
      vertexes[1] += direction.y * i;
      vertexes[2] += direction.z * i;
      vertexes[3] += direction.x * i;
      vertexes[4] += direction.y * i;
      vertexes[5] += direction.z * i;

        AABB boundingBox =
          new AABB(
              vertexes[0], vertexes[1], vertexes[2], vertexes[3], vertexes[4], vertexes[5]);

      List<Entity> list = world.getEntities(excludedEntity, boundingBox);
      double smallestEntityDistance = 0.0D;
      for (Entity entity1 : list) {
        if (entity1.isPickable()
            && (!entity1.is(excludedEntity))
            && !entity1.noPhysics) {
            AABB axisalignedbb = entity1.getBoundingBox().inflate(0.30000001192092896D);
            world.getEntities(entity,axisalignedbb);
                    Optional<Vec3> intercept =
                        axisalignedbb.clip(startingLocation, endingLocation);
                    if (intercept.isPresent()) {
                      double currentEntityDistance =
                        startingLocation.distanceToSqr(intercept.get());

                      if (currentEntityDistance < smallestEntityDistance || smallestEntityDistance
           == 0.0D) {
                        entity = entity1;
                        smallestEntityDistance = currentEntityDistance;
                      }
                    }
        }
      }

      if (entity != null) {
                Optional<Vec3> intercept = boundingBox.clip(startingLocation, endingLocation);

                if (intercept.isPresent()) {
                  Vec3 hitVec = intercept.get();
                  return new EntityHitResult(entity, hitVec);
                }
      }
    }
    return rayTraceResult;
  }
}
