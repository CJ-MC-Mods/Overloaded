package com.cjm721.overloaded.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class WorldUtil {

  public static RayTraceResult rayTraceWithEntities(
      @Nonnull World world,
      @Nonnull Vec3d startingLocation,
      @Nonnull Vec3d direction,
      @Nonnull Entity excludedEntity,
      double maxDistance) {
    Vec3d endingLocation = startingLocation.add(direction.scale(maxDistance));
    BlockRayTraceResult raytraceresult =
        world.rayTraceBlocks(
            new RayTraceContext(
                startingLocation,
                endingLocation,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                excludedEntity));

    if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
      endingLocation =
          new Vec3d(
              raytraceresult.getHitVec().x,
              raytraceresult.getHitVec().y,
              raytraceresult.getHitVec().z);
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

      AxisAlignedBB boundingBox =
          new AxisAlignedBB(
              vertexes[0], vertexes[1], vertexes[2], vertexes[3], vertexes[4], vertexes[5]);

      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(excludedEntity, boundingBox);
      double smallestEntityDistance = 0.0D;
      for (Entity entity1 : list) {
        if (entity1.canBeCollidedWith()
            && (!entity1.isEntityEqual(excludedEntity))
            && !entity1.noClip) {
          AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(0.30000001192092896D);
          //          RayTraceResult intercept =
          //              axisalignedbb.intersects(startingLocation, endingLocation);
          //
          //          if (intercept != null && intercept.hitVec != null) {
          //            double currentEntityDistance =
          // startingLocation.squareDistanceTo(intercept.hitVec);
          //
          //            if (currentEntityDistance < smallestEntityDistance || smallestEntityDistance
          // == 0.0D) {
          //              entity = entity1;
          //              smallestEntityDistance = currentEntityDistance;
          //            }
          //          }
        }
      }

      if (entity != null) {
        //        RayTraceResult intercept = boundingBox.calculateIntercept(startingLocation,
        // endingLocation);
        //
        //        if (intercept != null) {
        //          Vec3d hitVec = intercept.hitVec;
        //          return new RayTraceResult(entity, hitVec == null ? new Vec3d(0.5, 0.5, 0.5) :
        // hitVec);
        //        }
      }
    }
    return raytraceresult;
  }
}
