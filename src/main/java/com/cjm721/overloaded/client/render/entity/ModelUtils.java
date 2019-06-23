package com.cjm721.overloaded.client.render.entity;

/// **
// * Copied to different package.
// * Created by brandon3055 on 9/4/2016.
// * Used for general rendering stuff
// */
// @OnlyIn(Dist.CLIENT)
// class ModelUtils implements IResourceManagerReloadListener {
//    public static void renderQuads(List<BakedQuad> listQuads) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder vertexbuffer = tessellator.getBuffer();
//        int i = 0;
//        vertexbuffer.begin(7, DefaultVertexFormats.ITEM);
//        for (int j = listQuads.size(); i < j; ++i) {
//            BakedQuad bakedquad = listQuads.get(i);
//
//            vertexbuffer.addVertexData(bakedquad.getVertexData());
//
//            vertexbuffer.putColorRGB_F4(1, 1, 1);
//
//            Vec3i vec3i = bakedquad.getFace().getDirectionVec();
//            vertexbuffer.putNormal((float) vec3i.getX(), (float) vec3i.getY(), (float)
// vec3i.getZ());
//
//        }
//        tessellator.draw();
//    }
//
//    @Override
//    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
//    }
// }
