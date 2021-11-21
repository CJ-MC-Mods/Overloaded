package com.cjm721.overloaded.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class WorldUtil {

  public static RayTraceResult rayTraceWithEntities(
      @Nonnull World world,
      @Nonnull Vector3d startingLocation,
      @Nonnull Vector3d direction,
      @Nonnull Entity excludedEntity,
      double maxDistance) {
    Vector3d endingLocation = startingLocation.add(direction.scale(maxDistance));
    BlockRayTraceResult rayTraceResult =
        world.clip(
            new RayTraceContext(
                startingLocation,
                endingLocation,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                excludedEntity));

    if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
      endingLocation =
          new Vector3d(
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

      AxisAlignedBB boundingBox =
          new AxisAlignedBB(
              vertexes[0], vertexes[1], vertexes[2], vertexes[3], vertexes[4], vertexes[5]);

      List<Entity> list = world.getEntities(excludedEntity, boundingBox);
      double smallestEntityDistance = 0.0D;
      for (Entity entity1 : list) {
        if (entity1.isPickable()
            && (!entity1.is(excludedEntity))
            && !entity1.noPhysics) {
          AxisAlignedBB axisalignedbb = entity1.getBoundingBox().inflate(0.30000001192092896D);
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
    return rayTraceResult;
  }
}
