package net.voidarkana.fintastic.common.entity.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.PartEntity;
import net.voidarkana.fintastic.Fintastic;
import net.voidarkana.fintastic.common.item.YAFMItems;
import net.voidarkana.fintastic.server.MessageHurtMultipart;
import net.voidarkana.fintastic.server.MultipartEntityMessage;

import javax.annotation.Nullable;
import java.util.List;

public class ArapaimaPart extends PartEntity<ArapaimaEntity> {

    private final EntityDimensions size;
    public float scale = 1;

    public ArapaimaPart(ArapaimaEntity parent, float pWidth, float pHeight) {
        super(parent);

        this.blocksBuilding = true;

        this.size = EntityDimensions.scalable(pWidth, pHeight);
        this.refreshDimensions();
    }

    public ArapaimaPart(ArapaimaEntity arapaima, float sizeX, float sizeY, EntityDimensions size) {
        super(arapaima);
        this.size = size;
    }

    protected void collideWithNearbyEntities() {
        final List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(0.2D, 0.0D, 0.2D));
        Entity parent = this.getParent();
        if (parent != null) {
            entities.stream().filter(entity -> entity != parent && !(entity instanceof ArapaimaPart && ((ArapaimaPart) entity).getParent() == parent) && entity.isPushable()).forEach(entity -> entity.push(parent));
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        Entity parent = this.getParent();
        return parent != null && parent.canBeCollidedWith();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        Entity parent = this.getParent();
        if (parent == null) {
            return InteractionResult.PASS;
        } else {
            if (player.level().isClientSide) {
                Fintastic.sendMSGToServer(new MultipartEntityMessage(parent.getId(), player.getId(), 0, 0));
            }
            return parent.interact(player, hand);
        }
    }

//    @Override
//    public InteractionResult interact(Player player, InteractionHand hand) {
//        if(this.level().isClientSide && this.getParent() != null){
//            Fintastic.sendMSGToServer(new MultipartEntityMessage(this.getParent().getId(), hand == InteractionHand.OFF_HAND));
//        }
//        return this.getParent() == null ? InteractionResult.PASS : this.getParent().interactEntityPartFrom(this, player, hand);
//    }

    protected void collideWithEntity(Entity entityIn) {
        entityIn.push(this);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {}


    @Nullable
    public ItemStack getPickResult() {
        Entity parent = this.getParent();
        return parent != null ? parent.getPickResult() : ItemStack.EMPTY;
    }

    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity parent = this.getParent();
        if (!this.isInvulnerableTo(source) && parent != null) {
            Entity player = source.getEntity();
            if (player != null && player.level().isClientSide) {
                Fintastic.sendMSGToServer(new MultipartEntityMessage(parent.getId(), player.getId(), 1, amount));
            }
        }
        return false;
    }

    public boolean fireImmune() {
        return true;
    }

    public boolean is(Entity entityIn) {
        return this == entityIn || this.getParent() == entityIn;
    }

    public void tick(){
        super.tick();
    }

    public EntityDimensions getDimensions(Pose poseIn) {
        return this.size == null ? EntityDimensions.scalable(0, 0) : this.size.scale(scale);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    public void onAttackedFromServer(LivingEntity parent, float damage, DamageSource damageSource) {
        parent.hurt(damageSource, damage);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(YAFMItems.ARAPAIMA_SPAWN_EGG.get());
    }

    @Override
    public boolean save(CompoundTag tag) {
        return false;
    }
}
