package thatpreston.mermod.client.render;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class TailModel<T extends LivingEntity> extends AgeableListModel<T> {
    private final ModelPart main;
    private final ModelPart waist;
    private final ModelPart bra;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart tail5;
    private final ModelPart tail6;
    private final ModelPart tail7;
    private final ModelPart fin1;
    private final ModelPart fin2;
    private static final float r = (float)(Math.PI / 180);
    public TailModel(ModelPart root) {
        main = root.getChild("main");
        waist = main.getChild("waist");
        bra = main.getChild("bra");
        tail1 = main.getChild("tail1");
        tail2 = tail1.getChild("tail2");
        tail3 = tail2.getChild("tail3");
        tail4 = tail3.getChild("tail4");
        tail5 = tail4.getChild("tail5");
        tail6 = tail5.getChild("tail6");
        tail7 = tail6.getChild("tail7");
        fin1 = tail7.getChild("fin1");
        fin2 = fin1.getChild("fin2");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition main = modelPartData.addOrReplaceChild("main", CubeListBuilder.create().addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        main.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(24, 0).addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        main.addOrReplaceChild("bra", CubeListBuilder.create().texOffs(24, 16).addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        PartDefinition tail1 = main.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 0).addBox(-4, 0, -2, 8, 4, 4), PartPose.offset(0, 12, 0));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 8).addBox(-3.75F, 0, -1.75F, 7.5F, 3, 3.5F), PartPose.offset(0, 4, 0));
        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, 0, -1.5F, 7, 2, 3), PartPose.offset(0, 3, 0));
        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(0, 20).addBox(-3.25F, 0, -1.25F, 6.5F, 2, 2.5F), PartPose.offset(0, 2, 0));
        PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(0, 25).addBox(-3, 0, -1, 6, 2, 2), PartPose.offset(0, 2, 0));
        PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(0, 29).addBox(-2.75F, 0, -0.75F, 5.5F, 2, 1.5F), PartPose.offset(0, 2, 0));
        PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(0, 33).addBox(-2.5F, 0, -0.5F, 5, 2, 1), PartPose.offset(0, 2, 0));
        PartDefinition fin1 = tail7.addOrReplaceChild("fin1", CubeListBuilder.create().texOffs(0, 36).addBox(-11.5F, 0, 0.02F, 23, 24, 1), PartPose.offset(0, 2, 0));
        fin1.addOrReplaceChild("fin2", CubeListBuilder.create().texOffs(0, 36).addBox(-11.5F, 0, -0.04F, 23, 24, 1), PartPose.rotation(0, 180 * r, 0));
        return LayerDefinition.create(modelData, 48, 61);
    }
    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float yaw, float pitch) {}
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(main);
    }
    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }
    private static float sine(int a, int b, float t) {
        return (float)((-(b - a)) / 2 * (Math.cos(Math.PI * t) - 1) + a);
    }
    private static float angle(float a, int b, float c) {
        float d = ((a - b) * c) % 40;
        float e = ((a - b) * c) % 80;
        return e < 40 ? sine(-20, 20, d / 40) : sine(20, -20, d / 40);
    }
    private void swimPose(float age) {
        tail1.xRot = tail2.xRot = angle(age, 0, 2.5F) * r;
        tail3.xRot = angle(age, 5, 2.5F) * r;
        tail4.xRot = tail5.xRot = angle(age, 10, 2.5F) * r;
        tail6.xRot = tail7.xRot = fin1.xRot = angle(age, 15, 2.5F) * r;
    }
    private void idlePose(float angle) {
        tail1.xRot = angle * r;
        tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = fin1.xRot = 0;
    }
    private void landPose() {
        tail1.xRot = tail2.xRot = tail3.xRot = tail4.xRot = tail5.xRot = tail6.xRot = tail7.xRot = 14 * r;
        fin1.xRot = 0;
    }
    public void updatePose(Player entity, HumanoidModel model, float age) {
        model.copyPropertiesTo(this);
        this.main.copyFrom(model.body);
        if(entity.isSleeping()) {
            idlePose(0);
        } else if(model.riding) {
            idlePose(-90);
        } else if(entity.isOnGround() && !entity.isSwimming()) {
            landPose();
        } else {
            swimPose(age);
        }
    }
    public void renderTail(PoseStack stack, MultiBufferSource source, int light, int overlay, MermaidTailStyle style) {
        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(source, RenderType.entityTranslucentCull(style.getTexture()), false, style.getHasGlint());
        float tailRed = (style.getTailColor() >> 16 & 255) / 255.0F;
        float tailGreen = (style.getTailColor() >> 8 & 255) / 255.0F;
        float tailBlue = (style.getTailColor() & 255) / 255.0F;
        float braRed = (style.getBraColor() >> 16 & 255) / 255.0F;
        float braGreen = (style.getBraColor() >> 8 & 255) / 255.0F;
        float braBlue = (style.getBraColor() & 255) / 255.0F;
        float gradientRed = (style.getGradientColor() >> 16 & 255) / 255.0F;
        float gradientGreen = (style.getGradientColor() >> 8 & 255) / 255.0F;
        float gradientBlue = (style.getGradientColor() & 255) / 255.0F;
        this.main.translateAndRotate(stack);
        stack.scale(1.01F, 1.01F, 1.01F);
        stack.translate(0, -0.00375F, 0);
        this.waist.render(stack, consumer, light, overlay, tailRed, tailGreen, tailBlue, 1);
        if(style.getHasBra()) {
            this.bra.render(stack, consumer, light, overlay, braRed, braGreen, braBlue, 1);
        }
        AtomicInteger index = new AtomicInteger();
        this.tail1.visit(stack, (entry, path, cuboidIndex, cuboid) -> {
            float red = tailRed;
            float green = tailGreen;
            float blue = tailBlue;
            if(style.getHasGradient()) {
                if(index.get() < 7) {
                    float delta = index.get() / 7.0F;
                    red = Mth.lerp(delta, red, gradientRed);
                    green = Mth.lerp(delta, green, gradientGreen);
                    blue = Mth.lerp(delta, blue, gradientBlue);
                } else {
                    red = gradientRed;
                    green = gradientGreen;
                    blue = gradientBlue;
                }
            }
            cuboid.compile(entry, consumer, light, overlay, red, green, blue, 1);
            index.getAndIncrement();
        });
    }
}