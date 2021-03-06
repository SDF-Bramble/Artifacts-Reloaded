package archived.artifactsreloaded.common.gui.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;




public class AntibuilderParticle extends EntityFX {

float reddustParticleScale;
//ResourceLocation rl = new ResourceLocation("artifacts:textures/items/radarparticle.png");
//ResourceLocation rl2 = new ResourceLocation("minecraft:textures/particle/particles.png");

public AntibuilderParticle(World par1World, double x, double y, double z, int maxAge)
{
	this(par1World, x, y, z, 1.0F, maxAge);
}

public AntibuilderParticle(World par1World, double x, double y, double z, float scale, int maxAge) {
	this(par1World, x, y, z, scale, 0, maxAge);
}

public AntibuilderParticle(World par1World, double x, double y, double z, float scale, int age, int maxAge)
{
	super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
	this.motionX *= .10000000149011612D;
	this.motionY *= .10000000149011612D;
	this.motionZ *= .10000000149011612D;

	float var12 = (float)Math.random() * 0.4F + 0.6F;
	float f4 = (float)Math.random() * 0.2F + 0.3F;
	this.particleRed = 1;//((float)(Math.random() * 0.20000000298023224D) + 0.8F) * f4;
	this.particleGreen = 0;//((float)(Math.random() * 0.20000000298023224D) + 1F) * f4;
	this.particleBlue = 0;//((float)(Math.random() * 0.20000000298023224D) + 0.8F) * f4;
	this.particleScale *= scale;
	this.reddustParticleScale = this.particleScale;
	this.particleMaxAge = maxAge;
	this.noClip = true;
	this.particleAlpha = 1;// - 0.035F * age;
	this.particleAge = age;
	//System.out.println((Icon)ArtifactsAPI.itemicons.icons.get("radar"));
	//this.setParticleIcon((Icon)ArtifactsAPI.itemicons.icons.get("radar"));
}

public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
{
	par1Tessellator.draw();
	GL11.glPushMatrix();

	ParticleUtils.bindTexture("textures/items/radarparticle.png");

	//GL11.glDepthMask(false);
	GL11.glDepthFunc(GL11.GL_ALWAYS);
	//GL11.glEnable(3042);
	//GL11.glBlendFunc(770, 1);
	GL11.glEnable(GL11.GL_BLEND);
	GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_SRC_ALPHA);
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
	//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	par1Tessellator.startDrawingQuads();
		/*int bright = -1;
		try {
			bright = ReflectionHelper.getPrivateValue(Tessellator.class, par1Tessellator, new String[] { "brightness", "b", "field_78401_l" });
		}
		catch (Exception e) {
			System.out.println("Bad reflection");
		}*/

	par1Tessellator.setBrightness(255);

	float var8 = ((float)this.particleAge + par2) / (float)this.particleMaxAge * 32.0F;

	if (var8 < 0.0F)
	{
		var8 = 0.0F;
	}

	if (var8 > 1.0F)
	{
		var8 = 1.0F;
	}

	this.particleScale = this.reddustParticleScale;// * var8;
	//replace this
	//super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);

	//Minecraft.getMinecraft().renderEngine.bindTexture(rl);

	float f6 = (float)this.particleTextureIndexX / 32.0F;
	float f7 = f6 + 0.029375F;
	//              0.0624375
	//              0.0625
	float f8 = (float)this.particleTextureIndexY / 32.0F;
	float f9 = f8 + 0.029375F;
	float f10 = 0.15F;// * this.particleScale;

	float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
	float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
	float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
	float f14 = 1.0F;
	//par1Tessellator.draw();
	//GL11.glDepthFunc(GL11.GL_ALWAYS);
	//par1Tessellator.startDrawingQuads();
	//par1Tessellator.setBrightness(255);
	//GL11.glDisable(GL11.GL_DEPTH_TEST);
	float al = 0.0F;
	EntityPlayer player = (EntityPlayer) this.worldObj.findNearestEntityWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.posX - 16, this.posY-16, this.posZ-16, this.posX+16, this.posY+16, this.posZ+16), this);
	if(player != null) {
		double d = player.getDistanceToEntity(this);
		if(d < 6 && d > 1) {
			al = (float)(d / 8D);
		}
		else if(d > 1)
			al = 6F / 8F;
	}/**/
	par1Tessellator.setColorRGBA_F(this.particleRed * f14 - al/2, this.particleGreen * f14, this.particleBlue * f14 + al, 0.05F);
	par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
	par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
	par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
	par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
	par1Tessellator.draw();
	//GL11.glDepthFunc(GL11.GL_LEQUAL);
	//par1Tessellator.startDrawingQuads();
	//rl = new ResourceLocation("textures/particle/particles.png");
	//Minecraft.getMinecraft().renderEngine.bindTexture(rl2);
	//GL11.glEnable(GL11.GL_DEPTH_TEST);

	GL11.glDepthFunc(GL11.GL_LEQUAL);
	GL11.glDisable(GL11.GL_BLEND);
	GL11.glPopMatrix();
	Minecraft.getMinecraft().renderEngine.bindTexture(ParticleUtils.getParticleTexture());

	par1Tessellator.startDrawingQuads();
	par1Tessellator.setBrightness(0);
}

public void onUpdate()
{
	this.prevPosX = this.posX;
	this.prevPosY = this.posY;
	this.prevPosZ = this.posZ;

	if (this.particleAge++ >= this.particleMaxAge)
	{
		this.setDead();
	}
	this.setParticleTextureIndex(particleAge+48);
}
}

