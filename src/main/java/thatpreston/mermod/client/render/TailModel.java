package thatpreston.mermod.client.render;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import thatpreston.mermod.client.mixin.ModelRendererMixin;

import java.util.concurrent.atomic.AtomicInteger;

public class TailModel<T extends LivingEntity> extends AgeableModel<T> {
    private final ModelRenderer main;
    private final ModelRenderer waist;
    private final ModelRenderer bra;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer tail4;
    private final ModelRenderer tail5;
    private final ModelRenderer tail6;
    private final ModelRenderer tail7;
    private final ModelRenderer fin1;
    private final ModelRenderer fin2;
    private static final float r = (float)(Math.PI / 180);
    public TailModel() {
        this.texWidth = 48;
        this.texHeight = 61;
        main = new ModelRenderer(this).addBox(-4, 0, -2, 8, 12, 4);
        waist = new ModelRenderer(this, 24, 0).addBox(-4, 0, -2, 8, 12, 4);
        bra = new ModelRenderer(this, 24, 16).addBox(-4, 0, -2, 8, 12, 4);
        tail1 = new ModelRenderer(this, 0, 0).addBox(-4, 0, -2, 8, 4, 4);
        tail1.setPos(0, 12, 0);
        tail2 = new ModelRenderer(this, 0, 8).addBox(-3.75F, 0, -1.75F, 7.5F, 3, 3.5F);
        tail2.setPos(0, 4, 0);
        tail3 = new ModelRenderer(this, 0, 15).addBox(-3.5F, 0, -1.5F, 7, 2, 3);
        tail3.setPos(0, 3, 0);
        tail4 = new ModelRenderer(this, 0, 20).addBox(-3.25F, 0, -1.25F, 6.5F, 2, 2.5F);
        tail4.setPos(0, 2, 0);
        tail5 = new ModelRenderer(this, 0, 25).addBox(-3, 0, -1, 6, 2, 2);
        tail5.setPos(0, 2, 0);
        tail6 = new ModelRenderer(this, 0, 29).addBox(-2.75F, 0, -0.75F, 5.5F, 2, 1.5F);
        tail6.setPos(0, 2, 0);
        tail7 = new ModelRenderer(this, 0, 33).addBox(-2.5F, 0, -0.5F, 5, 2, 1);
        tail7.setPos(0, 2, 0);
        fin1 = new ModelRenderer(this, 0, 36).addBox(-11.5F, 0, 0.02F, 23, 24, 1);
        fin1.setPos(0, 2, 0);
        fin2 = new ModelRenderer(this, 0, 36).addBox(-11.5F, 0, -0.04F, 23, 24, 1);
        fin2.yRot = 180 * r;
        main.addChild(waist);
        main.addChild(bra);
        main.addChild(tail1);
        tail1.addChild(tail2);
        tail2.addChild(tail3);
        tail3.addChild(tail4);
        tail4.addChild(tail5);
        tail5.addChild(tail6);
        tail6.addChild(tail7);
        tail7.addChild(fin1);
        fin1.addChild(fin2);
    }
    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float yaw, float pitch) {}
    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(main);
    }
    @Override
    protected Iterable<ModelRenderer> headParts() {
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
    public void updatePose(PlayerEntity entity, BipedModel model, float age) {
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
    public void renderTail(MatrixStack stack, IRenderTypeBuffer buffer, int light, int overlay, MermaidTailStyle style) {
        IVertexBuilder builder = ItemRenderer.getFoilBufferDirect(buffer, RenderType.entityTranslucentCull(style.getTexture()), false, style.getHasGlint());
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
        this.waist.render(stack, builder, light, overlay, tailRed, tailGreen, tailBlue, 1);
        if(style.getHasBra()) {
            this.bra.render(stack, builder, light, overlay, braRed, braGreen, braBlue, 1);
        }
        AtomicInteger index = new AtomicInteger();
        forEachPart(stack, tail1, (entry, part) -> {
            float red = tailRed;
            float green = tailGreen;
            float blue = tailBlue;
            if(style.getHasGradient()) {
                if(index.get() < 7) {
                    float delta = index.get() / 7.0F;
                    red = MathHelper.lerp(delta, red, gradientRed);
                    green = MathHelper.lerp(delta, green, gradientGreen);
                    blue = MathHelper.lerp(delta, blue, gradientBlue);
                } else {
                    red = gradientRed;
                    green = gradientGreen;
                    blue = gradientBlue;
                }
            }
            part.callCompile(entry, builder, light, overlay, red, green, blue, 1);
            index.getAndIncrement();
        });
    }
    private static void forEachPart(MatrixStack stack, ModelRenderer part, PartConsumer consumer) {
        ModelRendererMixin mixin = (ModelRendererMixin)part;
        stack.pushPose();
        part.translateAndRotate(stack);
        MatrixStack.Entry entry = stack.last();
        consumer.accept(entry, mixin);
        mixin.getChildren().forEach(child -> forEachPart(stack, child, consumer));
        stack.popPose();
    }
    @FunctionalInterface
    private interface PartConsumer {
        void accept(MatrixStack.Entry entry, ModelRendererMixin part);
    }
}