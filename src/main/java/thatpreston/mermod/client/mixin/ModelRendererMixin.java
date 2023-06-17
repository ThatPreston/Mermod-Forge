package thatpreston.mermod.client.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.model.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelRenderer.class)
public interface ModelRendererMixin {
    @Accessor("children")
    ObjectList<ModelRenderer> getChildren();
    @Invoker("compile")
    void callCompile(MatrixStack.Entry entry, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float alpha);
}