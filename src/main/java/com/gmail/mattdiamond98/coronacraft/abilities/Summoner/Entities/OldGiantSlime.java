

package com.gmail.mattdiamond98.coronacraft.abilities.Summoner.Entities;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import javax.annotation.Nullable;

import com.gmail.mattdiamond98.coronacraft.abilities.Summoner.CustomEntity;
import com.gmail.mattdiamond98.coronacraft.abilities.Summoner.Utility.PathfinderGoalHelpTeam;
import com.tommytony.war.Team;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public class OldGiantSlime implements IMonster, CustomEntity {
    @Override
    public Player getPlayer() {
        return null;
    }
   /* private static final DataWatcherObject<Integer> bw;
    public float b;
    public float c;
    public float d;
    private boolean bx;
    private Player player;

    static {
        bw = DataWatcher.a(OldGiantSlime.class, DataWatcherRegistry.b);
    }


    public OldGiantSlime(Location loc, Player p, Team t) {
        super(EntityTypes.SLIME, (((CraftWorld)loc.getWorld()).getHandle()));
        this.player=p;


        this.setPosition(loc.getX(), loc.getY(), loc.getZ());
        if(t!=null){
            this.setCustomName(new ChatComponentText(t.getKind().getColor() +"GIANT SLIME - "+t.getName()+" Team"));
            this.setCustomNameVisible(true);
            Bukkit.broadcastMessage(this.getBukkitEntity().getType().name());
            Slime s=(Slime) this.getBukkitEntity();

          //java.lang.ClassCastException: org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity cannot be cast to org.bukkit.entity.Slime
            //21.08 21:42:39 [Server] INFO at com.gmail.mattdiamond98.coronacraft.abilities.Summoner.Entities.GiantSlime.<init>(GiantSlime.java:50) ~[?:?]
            s.setSize(10);
            s.setHealth(50);
          t.addNonPlayerEntity(this.getBukkitEntity());}else{
            this.setCustomName(new ChatComponentText("GIANT SLIME"));
        }
    }
    protected void initPathfinder() {
        this.goalSelector.a(1, new OldGiantSlime.PathfinderGoalSlimeRandomJump(this));

        this.goalSelector.a(2, new OldGiantSlime.PathfinderGoalSlimeRandomDirection(this));
        this.goalSelector.a(3, new OldGiantSlime.PathfinderGoalSlimeIdle(this));
       this.targetSelector.a(0, new PathfinderGoalHelpTeam<EntityLiving>(this, EntityLiving.class, true));
    }

    protected void initDatawatcher() {
        super.initDatawatcher();
        this.datawatcher.register(bw, 1);
    }



    public void setSize(int i, boolean flag) {
        this.datawatcher.set(bw, i);
        this.Z();
        this.updateSize();
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue((double)(i * i));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)(0.2F + 0.1F * (float)i));
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue((double)i);
        if (flag) {
            this.setHealth(this.getMaxHealth());
        }

        this.f = i;
    }

    public int getSize() {
        return (Integer)this.datawatcher.get(bw);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Size", this.getSize() - 1);
        nbttagcompound.setBoolean("wasOnGround", this.bx);
    }

    public void a(NBTTagCompound nbttagcompound) {
        int i = nbttagcompound.getInt("Size");
        if (i < 0) {
            i = 0;
        }

        this.setSize(i + 1, false);
        super.a(nbttagcompound);
        this.bx = nbttagcompound.getBoolean("wasOnGround");
    }

    public boolean ev() {
        return this.getSize() <= 1;
    }

    protected ParticleParam l() {
        return Particles.ITEM_SLIME;
    }

    protected boolean J() {
        return this.getSize() > 0;
    }

    public void tick() {
        this.c += (this.b - this.c) * 0.5F;
        this.d = this.c;
        super.tick();
        if (this.onGround && !this.bx) {
            int i = this.getSize();

            for(int j = 0; j < i * 8; ++j) {
                float f = this.random.nextFloat() * 6.2831855F;
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
                this.world.addParticle(this.l(), this.locX() + (double)f2, this.locY(), this.locZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            }

            this.a(this.getSoundSquish(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.b = -0.5F;
        } else if (!this.onGround && this.bx) {
            this.b = 1.0F;
        }

        this.bx = this.onGround;
        this.ep();
    }

    protected void ep() {
        this.b *= 0.6F;
    }

    protected int eo() {
        return this.random.nextInt(20) + 10;
    }

    public void updateSize() {
        double d0 = this.locX();
        double d1 = this.locY();
        double d2 = this.locZ();
        super.updateSize();
        this.setPosition(d0, d1, d2);
    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (bw.equals(datawatcherobject)) {
            this.updateSize();
            this.yaw = this.aK;
            this.aI = this.aK;
            if (this.isInWater() && this.random.nextInt(20) == 0) {
                this.aD();
            }
        }

        super.a(datawatcherobject);
    }

    public EntityTypes getEntityType() {
        return super.getEntityType();
    }

    public void die() {
        int i = this.getSize();
        if (!this.world.isClientSide && i > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.random.nextInt(3);
            SlimeSplitEvent event = new SlimeSplitEvent((Slime)this.getBukkitEntity(), j);
            this.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled() || event.getCount() <= 0) {
                super.die();
                return;
            }

            j = event.getCount();
            ArrayList slimes = new ArrayList(j);

            for(int k = 0; k < j; ++k) {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                OldGiantSlime OldGiantSlime = (OldGiantSlime)this.getEntityType().a(this.world);
                if (this.hasCustomName()) {
                    OldGiantSlime.setCustomName(this.getCustomName());
                }

                if (this.isPersistent()) {
                    OldGiantSlime.setPersistent();
                }

                OldGiantSlime.setInvulnerable(this.isInvulnerable());
                OldGiantSlime.setSize(i / 2, true);
                OldGiantSlime.setPositionRotation(this.locX() + (double)f, this.locY() + 0.5D, this.locZ() + (double)f1, this.random.nextFloat() * 360.0F, 0.0F);
                slimes.add(OldGiantSlime);
            }

            if (CraftEventFactory.callEntityTransformEvent(this, slimes, TransformReason.SPLIT).isCancelled()) {
                return;
            }

            Iterator var10 = slimes.iterator();

            while(var10.hasNext()) {
                EntityLiving living = (EntityLiving)var10.next();
                this.world.addEntity(living, SpawnReason.SLIME_SPLIT);
            }
        }

        super.die();
    }

    public void collide(Entity entity) {
        super.collide(entity);
        if (entity instanceof EntityIronGolem && this.eq()) {
            this.i((EntityLiving)entity);
        }

    }

    public void pickup(EntityHuman entityhuman) {
        if (this.eq()) {
            this.i(entityhuman);
        }

    }

    protected void i(EntityLiving entityliving) {
        if (this.isAlive()) {
            int i = this.getSize();
            if (this.h(entityliving) < 0.6D * (double)i * 0.6D * (double)i && this.hasLineOfSight(entityliving) && entityliving.damageEntity(DamageSource.mobAttack(this), this.er())) {
                this.a(SoundEffects.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.a(this, entityliving);
            }
        }

    }

    protected float b(EntityPose entitypose, EntitySize entitysize) {
        return 0.625F * entitysize.height;
    }

    protected boolean eq() {
        return !this.ev() && this.doAITick();
    }

    protected float er() {
        return (float)this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
    }

    protected SoundEffect getSoundHurt(DamageSource damagesource) {
        return this.ev() ? SoundEffects.ENTITY_SLIME_HURT_SMALL : SoundEffects.ENTITY_SLIME_HURT;
    }

    protected SoundEffect getSoundDeath() {
        return this.ev() ? SoundEffects.ENTITY_SLIME_DEATH_SMALL : SoundEffects.ENTITY_SLIME_DEATH;
    }

    protected SoundEffect getSoundSquish() {
        return this.ev() ? SoundEffects.ENTITY_SLIME_SQUISH_SMALL : SoundEffects.ENTITY_SLIME_SQUISH;
    }

    protected MinecraftKey getDefaultLootTable() {
        return this.getSize() == 1 ? this.getEntityType().h() : LootTables.a;
    }

    public static boolean c(EntityTypes<EntitySlime> entitytypes, GeneratorAccess generatoraccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, Random random) {
        if (generatoraccess.getWorldData().getType() == WorldType.FLAT && random.nextInt(4) != 1) {
            return false;
        } else {
            if (generatoraccess.getDifficulty() != EnumDifficulty.PEACEFUL) {
                BiomeBase biomebase = generatoraccess.getBiome(blockposition);
                if (biomebase == Biomes.SWAMP && blockposition.getY() > 50 && blockposition.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < generatoraccess.Y() && generatoraccess.getLightLevel(blockposition) <= random.nextInt(8)) {
                    return a(entitytypes, generatoraccess, enummobspawn, blockposition, random);
                }

                ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(blockposition);
                boolean flag = SeededRandom.a(chunkcoordintpair.x, chunkcoordintpair.z, generatoraccess.getSeed(), (long)generatoraccess.getMinecraftWorld().spigotConfig.slimeSeed).nextInt(10) == 0;
                if (random.nextInt(10) == 0 && flag && blockposition.getY() < 40) {
                    return a(entitytypes, generatoraccess, enummobspawn, blockposition, random);
                }
            }

            return false;
        }
    }

    protected float getSoundVolume() {
        return 0.4F * (float)this.getSize();
    }

    public int dU() {
        return 0;
    }

    protected boolean ew() {
        return this.getSize() > 0;
    }

    protected void jump() {
        Vec3D vec3d = this.getMot();
        this.setMot(vec3d.x, (double)this.dp(), vec3d.z);
        this.impulse = true;
    }

    @Nullable
    public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        int i = this.random.nextInt(3);
        if (i < 2 && this.random.nextFloat() < 0.5F * difficultydamagescaler.d()) {
            ++i;
        }

        int j = 1 << i;
        this.setSize(j, true);
        return super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
    }

    protected SoundEffect getSoundJump() {
        return this.ev() ? SoundEffects.ENTITY_SLIME_JUMP_SMALL : SoundEffects.ENTITY_SLIME_JUMP;
    }

    public EntitySize a(EntityPose entitypose) {
        return super.a(entitypose).a(0.255F * (float)this.getSize());
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    static class ControllerMoveSlime extends ControllerMove {
        private float i;
        private int j;
        private final OldGiantSlime k;
        private boolean l;

        public ControllerMoveSlime(OldGiantSlime OldGiantSlime) {
            super(OldGiantSlime);
            this.k = OldGiantSlime;
            this.i = 180.0F * OldGiantSlime.yaw / 3.1415927F;
        }

        public void a(float f, boolean flag) {
            this.i = f;
            this.l = flag;
        }

        public void a(double d0) {
            this.e = d0;
            this.h = Operation.MOVE_TO;
        }

        public void a() {
            this.a.yaw = this.a(this.a.yaw, this.i, 90.0F);
            this.a.aK = this.a.yaw;
            this.a.aI = this.a.yaw;
            if (this.h != Operation.MOVE_TO) {
                this.a.r(0.0F);
            } else {
                this.h = Operation.WAIT;
                if (this.a.onGround) {
                    this.a.o((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
                    if (this.j-- <= 0) {
                        this.j = this.k.eo();
                        if (this.l) {
                            this.j /= 3;
                        }

                        this.k.getControllerJump().jump();
                        if (this.k.ew()) {
                            this.k.a(this.k.getSoundJump(), this.k.getSoundVolume(), ((this.k.getRandom().nextFloat() - this.k.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                        }
                    } else {
                        this.k.aZ = 0.0F;
                        this.k.bb = 0.0F;
                        this.a.o(0.0F);
                    }
                } else {
                    this.a.o((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
                }
            }

        }
    }

    static class PathfinderGoalSlimeIdle extends PathfinderGoal {
        private final OldGiantSlime a;

        public PathfinderGoalSlimeIdle(OldGiantSlime OldGiantSlime) {
            this.a = OldGiantSlime;
            this.a(EnumSet.of(Type.JUMP, Type.MOVE));
        }

        public boolean a() {
            return !this.a.isPassenger();
        }

        public void e() {
            ((ControllerMoveSlime)this.a.getControllerMove()).a(1.0D);
        }
    }

    static class PathfinderGoalSlimeNearestPlayer extends PathfinderGoal {
        private final OldGiantSlime a;
        private int b;

        public PathfinderGoalSlimeNearestPlayer(OldGiantSlime OldGiantSlime) {
            this.a = OldGiantSlime;
            this.a(EnumSet.of(Type.LOOK));
        }

        public boolean a() {
            EntityLiving entityliving = this.a.getGoalTarget();
            return entityliving == null ? false : (!entityliving.isAlive() ? false : (entityliving instanceof EntityHuman && ((EntityHuman)entityliving).abilities.isInvulnerable ? false : this.a.getControllerMove() instanceof ControllerMoveSlime));
        }

        public void c() {
            this.b = 300;
            super.c();
        }

        public boolean b() {
            EntityLiving entityliving = this.a.getGoalTarget();
            return entityliving == null ? false : (!entityliving.isAlive() ? false : (entityliving instanceof EntityHuman && ((EntityHuman)entityliving).abilities.isInvulnerable ? false : --this.b > 0));
        }

        public void e() {
            this.a.a(this.a.getGoalTarget(), 10.0F, 10.0F);
            ((ControllerMoveSlime)this.a.getControllerMove()).a(this.a.yaw, this.a.eq());
        }
    }

    static class PathfinderGoalSlimeRandomDirection extends PathfinderGoal {
        private final OldGiantSlime a;
        private float b;
        private int c;

        public PathfinderGoalSlimeRandomDirection(OldGiantSlime OldGiantSlime) {
            this.a = OldGiantSlime;
            this.a(EnumSet.of(Type.LOOK));
        }

        public boolean a() {
            return this.a.getGoalTarget() == null && (this.a.onGround || this.a.isInWater() || this.a.aH() || this.a.hasEffect(MobEffects.LEVITATION)) && this.a.getControllerMove() instanceof ControllerMoveSlime;
        }

        public void e() {
            if (--this.c <= 0) {
                this.c = 40 + this.a.getRandom().nextInt(60);
                this.b = (float)this.a.getRandom().nextInt(360);
            }

            ((ControllerMoveSlime)this.a.getControllerMove()).a(this.b, false);
        }
    }

    static class PathfinderGoalSlimeRandomJump extends PathfinderGoal {
        private final OldGiantSlime a;

        public PathfinderGoalSlimeRandomJump(OldGiantSlime OldGiantSlime) {
            this.a = OldGiantSlime;
            this.a(EnumSet.of(Type.JUMP, Type.MOVE));
            OldGiantSlime.getNavigation().d(true);
        }

        public boolean a() {
            return (this.a.isInWater() || this.a.aH()) && this.a.getControllerMove() instanceof ControllerMoveSlime;
        }

        public void e() {
            if (this.a.getRandom().nextFloat() < 0.8F) {
                this.a.getControllerJump().jump();

            }

            ((ControllerMoveSlime)this.a.getControllerMove()).a(1.2D);
        }
    }*/
}
