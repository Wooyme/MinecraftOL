package com.game.customrpg.client

import java.util.UUID
import org.lwjgl.opengl.GL11
import cpw.mods.fml.common.registry.VillagerRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.client.model.ModelVillager
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTUtil
import com.game.customrpg.entity.EntityP2PPlayer
import com.mojang.authlib.GameProfile
import net.minecraft.util.ResourceLocation
import net.minecraft.util.StringUtils

@SideOnly(Side.CLIENT)
class RenderCustomNPC : RenderLiving(ModelVillager(0.0f), 0.5f) {
	/** Model of the villager. */
	protected var villagerModel: ModelVillager? = null

	init {
		this.villagerModel = this.mainModel as ModelVillager
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected fun shouldRenderPass(p_77032_1_: com.game.customrpg.entity.EntityP2PPlayer?, p_77032_2_: Int, p_77032_3_: Float): Int {
		return -1
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	fun doRender(p_76986_1_: com.game.customrpg.entity.EntityP2PPlayer?, p_76986_2_: Double, p_76986_4_: Double, p_76986_6_: Double, p_76986_8_: Float, p_76986_9_: Float) {
		super.doRender(p_76986_1_ as EntityLiving?, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_)
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected fun getEntityTexture(p_110775_1_: com.game.customrpg.entity.EntityP2PPlayer?): ResourceLocation? {
		when (p_110775_1_!!.getProfession()) {
			0 -> return farmerVillagerTextures
			1 -> return librarianVillagerTextures
			2 -> return priestVillagerTextures
			3 -> return smithVillagerTextures
			4 -> return butcherVillagerTextures
			else -> return VillagerRegistry.getVillagerSkin(p_110775_1_!!.getProfession(), villagerTextures)
		}
	}

	protected fun renderEquippedItems(p_77029_1_: com.game.customrpg.entity.EntityP2PPlayer?, p_77029_2_: Float) {
//super.renderEquippedItems(p_77029_1_, p_77029_2_);
		this.renderHeldItems(p_77029_1_, p_77029_2_)
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	protected fun preRenderCallback(p_77041_1_: com.game.customrpg.entity.EntityP2PPlayer?, p_77041_2_: Float) {
		val f1 = 0.9375f
		this.shadowSize = 0.5f
		GL11.glScalef(f1, f1, f1)
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	override fun doRender(p_76986_1_: EntityLiving?, p_76986_2_: Double, p_76986_4_: Double, p_76986_6_: Double, p_76986_8_: Float, p_76986_9_: Float) {
		this.doRender(p_76986_1_ as com.game.customrpg.entity.EntityP2PPlayer?, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_)
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	override protected fun preRenderCallback(p_77041_1_: EntityLivingBase?, p_77041_2_: Float) {
		this.preRenderCallback(p_77041_1_ as com.game.customrpg.entity.EntityP2PPlayer?, p_77041_2_)
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	override protected fun shouldRenderPass(p_77032_1_: EntityLivingBase?, p_77032_2_: Int, p_77032_3_: Float): Int {
		return this.shouldRenderPass(p_77032_1_ as com.game.customrpg.entity.EntityP2PPlayer?, p_77032_2_, p_77032_3_)
	}

	override protected fun renderEquippedItems(p_77029_1_: EntityLivingBase?, p_77029_2_: Float) {
		this.renderEquippedItems(p_77029_1_ as com.game.customrpg.entity.EntityP2PPlayer?, p_77029_2_)
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	override fun doRender(p_76986_1_: EntityLivingBase?, p_76986_2_: Double, p_76986_4_: Double, p_76986_6_: Double, p_76986_8_: Float, p_76986_9_: Float) {
		this.doRender(p_76986_1_ as com.game.customrpg.entity.EntityP2PPlayer?, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_)
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	override protected fun getEntityTexture(p_110775_1_: Entity?): ResourceLocation? {
		return this.getEntityTexture(p_110775_1_ as com.game.customrpg.entity.EntityP2PPlayer?)
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	override fun doRender(p_76986_1_: Entity?, p_76986_2_: Double, p_76986_4_: Double, p_76986_6_: Double, p_76986_8_: Float, p_76986_9_: Float) {
		this.doRender(p_76986_1_ as com.game.customrpg.entity.EntityP2PPlayer?, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_)
	}

	protected fun renderHeldItems(p_77029_1_: EntityLiving?, p_77029_2_: Float) {
		GL11.glColor3f(1.0f, 1.0f, 1.0f)
		super.renderEquippedItems(p_77029_1_, p_77029_2_)
		val itemstack = p_77029_1_!!.getHeldItem()
		val item: Item?
		var f1: Float
		if (itemstack != null && itemstack.getItem() != null) {
			item = itemstack.getItem()
			GL11.glPushMatrix()
//this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f)
			val customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(itemstack, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED)
			val is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, itemstack, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D))
			if (item is ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
				f1 = 0.5f
				GL11.glTranslatef(0.065f, 0.0f, -0.5f)
				f1 *= 0.75f
//GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
//GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-f1, -f1, f1)
			} else if (item === Items.bow) {
				f1 = 0.625f
				GL11.glTranslatef(0.0f, 0.125f, 0.3125f)
				GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f)
				GL11.glScalef(f1, -f1, f1)
				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f)
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f)
			} else if (item!!.isFull3D()) {
				f1 = 0.625f
				if (item.shouldRotateAroundWhenRendering()) {
					GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f)
					GL11.glTranslatef(0.0f, -0.125f, 0.0f)
				}
//GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glTranslatef(0.065f, 0.0f, -0.5f)
				GL11.glScalef(f1, -f1, f1)
				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f)
				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f)
			} else {
				f1 = 0.375f
//GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glTranslatef(-0.1f, 0.0f, -0.5f)
				GL11.glScalef(f1, f1, f1)
				GL11.glRotatef(30.0f, 0.0f, 0.0f, 1.0f)
				GL11.glRotatef(-180.0f, 1.0f, 0.0f, 0.0f)
				GL11.glRotatef(120.0f, 0.0f, 1.0f, 0.0f)
			}
			var f2: Float
			var i: Int
			var f5: Float
			if (itemstack.getItem().requiresMultipleRenderPasses()) {
				i = 0
				while (i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage())) {
					val j = itemstack.getItem().getColorFromItemStack(itemstack, i)
					f5 = ((j shr 16) and 255).toFloat() / 255.0f
					f2 = ((j shr 8) and 255).toFloat() / 255.0f
					val f3 = (j and 255).toFloat() / 255.0f
					GL11.glColor4f(f5, f2, f3, 1.0f)
					this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, i)
					++i
				}
			} else {
				i = itemstack.getItem().getColorFromItemStack(itemstack, 0)
				val f4 = ((i shr 16) and 255).toFloat() / 255.0f
				f5 = ((i shr 8) and 255).toFloat() / 255.0f
				f2 = (i and 255).toFloat() / 255.0f
				GL11.glColor4f(f4, f5, f2, 1.0f)
				this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0)
			}
			GL11.glPopMatrix()
		}
	}

	companion object {
		private val villagerTextures = ResourceLocation("textures/entity/villager/villager.png")
		private val farmerVillagerTextures = ResourceLocation("textures/entity/villager/farmer.png")
		private val librarianVillagerTextures = ResourceLocation("textures/entity/villager/librarian.png")
		private val priestVillagerTextures = ResourceLocation("textures/entity/villager/priest.png")
		private val smithVillagerTextures = ResourceLocation("textures/entity/villager/smith.png")
		private val butcherVillagerTextures = ResourceLocation("textures/entity/villager/butcher.png")
		private val __OBFID = "CL_00001032"
	}
}