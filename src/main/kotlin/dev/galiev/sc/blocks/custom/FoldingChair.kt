/*
package dev.galiev.sc.blocks.custom

import dev.galiev.sc.blocks.custom.entity.ChairEntity
import dev.galiev.sc.blocks.custom.entity.ChairEntity.Companion.OCCUPIED
import dev.galiev.sc.helper.EntityTypeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.event.GameEvent
import java.util.function.BinaryOperator
import java.util.stream.Stream

class FoldingChair(settings: Settings = FabricBlockSettings.of(Material.WOOD).nonOpaque()) : Block(settings), Waterloggable {

    companion object {
        val FACING = Properties.FACING
        val WATERLOGGED = Properties.WATERLOGGED
    }

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.NORTH).with(WATERLOGGED, false))
    }

    val SHAPE_N = Stream.of(
        createCuboidShape(2.5, 1.5, 2.5, 3.5, 11.5, 3.5),
        createCuboidShape(12.5, 1.5, 2.5, 13.5, 11.5, 3.5),
        createCuboidShape(12.499, 9.0, 5.5, 13.999, 10.0, 14),
        createCuboidShape(2, 9, 5.5, 3.501, 10, 14),
        createCuboidShape(2.5, -5.5, 13.5, 3.5, 4.5, 14.5),
        createCuboidShape(12.5, -5.5, 13.5, 13.5, 4.5, 14.5),
        createCuboidShape(3.5, 7, 3.5, 12.5, 7.5, 13),
        createCuboidShape(3.5, 11.5, 9, 12.5, 22, 9.5),
        createCuboidShape(3.5, 4.5, 5, 12.5, 5, 5.5)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    val SHAPE_W = Stream.of(
        createCuboidShape(4.25, 1.0, 10.5, 9.25, 7.0, 11.0),
        createCuboidShape(4.75, 7.0, 9.5, 5.25, 9.0, 10.0),
        createCuboidShape(4.75, 7.0, 6.0, 5.25, 9.0, 6.5),
        createCuboidShape(8.25, 7.0, 6.0, 8.75, 9.0, 6.5),
        createCuboidShape(8.25, 7.0, 9.5, 8.75, 9.0, 10.0),
        createCuboidShape(4.25, 1.0, 5.0, 9.25, 7.0, 5.5),
        createCuboidShape(5.25, 7.0, 5.5, 8.25, 9.0, 6.5),
        createCuboidShape(5.25, 7.0, 9.5, 8.25, 9.0, 10.5),
        createCuboidShape(11.75, 3.5, 7.25, 12.75, 5.0, 8.75),
        createCuboidShape(10.75, 2.5, 7.0, 11.75, 4.5, 9.0),
        createCuboidShape(12.75, 4.5, 7.5, 13.75, 5.5, 8.5),
        createCuboidShape(13.25, 5.5, 7.5, 14.25, 6.0, 8.5),
        createCuboidShape(13.75, 5.0, 7.5, 14.25, 5.5, 8.5),
        createCuboidShape(12.75, 4.0, 7.5, 13.25, 4.5, 8.5),
        createCuboidShape(9.75, 5.0, 6.5, 10.25, 5.5, 9.5),
        createCuboidShape(9.75, 1.5, 6.5, 10.25, 2.0, 9.5),
        createCuboidShape(4.25, 0.0, 5.5, 9.25, 1.0, 10.5),
        createCuboidShape(9.75, 2.0, 6.5, 10.75, 5.0, 9.5),
        createCuboidShape(4.25, 7.0, 6.5, 5.25, 9.0, 9.5),
        createCuboidShape(8.25, 7.0, 6.5, 9.25, 9.0, 9.5),
        createCuboidShape(8.25, 6.0, 5.5, 9.25, 7.0, 10.5),
        createCuboidShape(9.25, 1.0, 5.5, 9.75, 7.0, 10.5),
        createCuboidShape(3.25, 1.0, 5.5, 5.25, 7.0, 10.5),
        createCuboidShape(2.25, 6.5, 7.0, 3.25, 7.0, 9.0),
        createCuboidShape(2.25, 2.0, 7.0, 3.25, 2.5, 9.0),
        createCuboidShape(1.75, 2.5, 7.0, 2.25, 6.5, 9.0),
        createCuboidShape(5.25, 9.0, 6.5, 8.25, 9.5, 9.5),
        createCuboidShape(6.25, 9.5, 7.5, 7.25, 10.5, 8.5)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    val SHAPE_S = Stream.of(
        createCuboidShape(10.5, 1.0, 6.75, 11.0, 7.0, 11.75),
        createCuboidShape(9.5, 7.0, 10.75, 10.0, 9.0, 11.25),
        createCuboidShape(6.0, 7.0, 10.75, 6.5, 9.0, 11.25),
        createCuboidShape(6.0, 7.0, 7.25, 6.5, 9.0, 7.75),
        createCuboidShape(9.5, 7.0, 7.25, 10.0, 9.0, 7.75),
        createCuboidShape(5.0, 1.0, 6.75, 5.5, 7.0, 11.75),
        createCuboidShape(5.5, 7.0, 7.75, 6.5, 9.0, 10.75),
        createCuboidShape(9.5, 7.0, 7.75, 10.5, 9.0, 10.75),
        createCuboidShape(7.25, 3.5, 3.25, 8.75, 5.0, 4.25),
        createCuboidShape(7.0, 2.5, 4.25, 9.0, 4.5, 5.25),
        createCuboidShape(7.5, 4.5, 2.25, 8.5, 5.5, 3.25),
        createCuboidShape(7.5, 5.5, 1.75, 8.5, 6.0, 2.75),
        createCuboidShape(7.5, 5.0, 1.75, 8.5, 5.5, 2.25),
        createCuboidShape(7.5, 4.0, 2.75, 8.5, 4.5, 3.25),
        createCuboidShape(6.5, 5.0, 5.75, 9.5, 5.5, 6.25),
        createCuboidShape(6.5, 1.5, 5.75, 9.5, 2.0, 6.25),
        createCuboidShape(5.5, 0.0, 6.75, 10.5, 1.0, 11.75),
        createCuboidShape(6.5, 2.0, 5.25, 9.5, 5.0, 6.25),
        createCuboidShape(6.5, 7.0, 10.75, 9.5, 9.0, 11.75),
        createCuboidShape(6.5, 7.0, 6.75, 9.5, 9.0, 7.75),
        createCuboidShape(5.5, 6.0, 6.75, 10.5, 7.0, 7.75),
        createCuboidShape(5.5, 1.0, 6.25, 10.5, 7.0, 6.75),
        createCuboidShape(5.5, 1.0, 10.75, 10.5, 7.0, 12.75),
        createCuboidShape(7.0, 6.5, 12.75, 9.0, 7.0, 13.75),
        createCuboidShape(7.0, 2.0, 12.75, 9.0, 2.5, 13.75),
        createCuboidShape(7.0, 2.5, 13.75, 9.0, 6.5, 14.25),
        createCuboidShape(6.5, 9.0, 7.75, 9.5, 9.5, 10.75),
        createCuboidShape(7.5, 9.5, 8.75, 8.5, 10.5, 9.75)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    val SHAPE_E = Stream.of(
        createCuboidShape(6.75, 1.0, 5.0, 11.75, 7.0, 5.5),
        createCuboidShape(10.75, 7.0, 6.0, 11.25, 9.0, 6.5),
        createCuboidShape(10.75, 7.0, 9.5, 11.25, 9.0, 10.0),
        createCuboidShape(7.25, 7.0, 9.5, 7.75, 9.0, 10.0),
        createCuboidShape(7.25, 7.0, 6.0, 7.75, 9.0, 6.5),
        createCuboidShape(6.75, 1.0, 10.5, 11.75, 7.0, 11.0),
        createCuboidShape(7.75, 7.0, 9.5, 10.75, 9.0, 10.5),
        createCuboidShape(7.75, 7.0, 5.5, 10.75, 9.0, 6.5),
        createCuboidShape(3.25, 3.5, 7.25, 4.25, 5.0, 8.75),
        createCuboidShape(4.25, 2.5, 7.0, 5.25, 4.5, 9.0),
        createCuboidShape(2.25, 4.5, 7.5, 3.25, 5.5, 8.5),
        createCuboidShape(1.75, 5.5, 7.5, 2.75, 6.0, 8.5),
        createCuboidShape(1.75, 5.0, 7.5, 2.25, 5.5, 8.5),
        createCuboidShape(2.75, 4.0, 7.5, 3.25, 4.5, 8.5),
        createCuboidShape(5.75, 5.0, 6.5, 6.25, 5.5, 9.5),
        createCuboidShape(5.75, 1.5, 6.5, 6.25, 2.0, 9.5),
        createCuboidShape(6.75, 0.0, 5.5, 11.75, 1.0, 10.5),
        createCuboidShape(5.25, 2.0, 6.5, 6.25, 5.0, 9.5),
        createCuboidShape(10.75, 7.0, 6.5, 11.75, 9.0, 9.5),
        createCuboidShape(6.75, 7.0, 6.5, 7.75, 9.0, 9.5),
        createCuboidShape(6.75, 6.0, 5.5, 7.75, 7.0, 10.5),
        createCuboidShape(6.25, 1.0, 5.5, 6.75, 7.0, 10.5),
        createCuboidShape(10.75, 1.0, 5.5, 12.75, 7.0, 10.5),
        createCuboidShape(12.75, 6.5, 7.0, 13.75, 7.0, 9.0),
        createCuboidShape(12.75, 2.0, 7.0, 13.75, 2.5, 9.0),
        createCuboidShape(13.75, 2.5, 7.0, 14.25, 6.5, 9.0),
        createCuboidShape(7.75, 9.0, 6.5, 10.75, 9.5, 9.5),
        createCuboidShape(8.75, 9.5, 7.5, 9.75, 10.5, 8.5)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return when (state?.get(FACING)) {
            Direction.DOWN -> SHAPE_S
            Direction.UP -> SHAPE_S
            Direction.NORTH -> SHAPE_N
            Direction.SOUTH -> SHAPE_S
            Direction.WEST -> SHAPE_W
            Direction.EAST -> SHAPE_E
            null -> SHAPE_W
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return defaultState
            .with(FACING, ctx?.player?.horizontalFacing?.opposite)
            .with(WATERLOGGED, ctx!!.world.getFluidState(ctx.blockPos).isOf(Fluids.WATER))
    }

    @Deprecated("Deprecated in Java")
    override fun getFluidState(state: BlockState?): FluidState {
        return if (state!![WATERLOGGED]) Fluids.WATER.getStill(false) else super.getFluidState(state)
    }

    @Deprecated("Deprecated in Java")
    override fun getStateForNeighborUpdate(
        state: BlockState?,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess?,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState {
        if (state?.get(WATERLOGGED) == true) {
            world?.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Deprecated("Deprecated in Java")
    override fun rotate(state: BlockState?, rotation: BlockRotation?): BlockState {
        return state?.with(FACING, rotation?.rotate(state.get(FACING)))!!
    }

    @Deprecated("Deprecated in Java")
    override fun mirror(state: BlockState?, mirror: BlockMirror?): BlockState {
        return state?.rotate(mirror?.getRotation(state.get(FACING)))!!
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING, WATERLOGGED)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("ActionResult.CONSUME", "net.minecraft.util.ActionResult"))
    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        if (world?.isClient!!) {
            return ActionResult.PASS
        }
        if (!player?.isSneaking!! && player.getStackInHand(hand).isEmpty) {
            val comparePos = Vec3d(hit?.blockPos?.x?.toDouble()!!, hit.blockPos.y.toDouble(), hit.blockPos.z.toDouble())

            return spawnChair(world, player, hit.blockPos, 0.3, comparePos)
        }
        return ActionResult.PASS
    }

    override fun onBreak(world: World?, pos: BlockPos?, state: BlockState?, player: PlayerEntity?) {
        if (pos != null) {
            val x = pos.x.toDouble()
            val y = pos.y.toDouble()
            val z = pos.z.toDouble()

            val entities: MutableList<ChairEntity>? = world?.getEntitiesByClass(
                ChairEntity::class.java,
                Box(x, y, z, x + 1.0, y + 1.0, z + 1.0)
            ) { entity -> entity.hasPassengers() }

            if (entities != null) {
                for (entity in entities) {
                    entity.remove(Entity.RemovalReason.DISCARDED)
                }
            }
            this.spawnBreakParticles(world, player, pos, state)
            world?.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos)

            ChairEntity.OCCUPIED.remove(Vec3d(x,y,z))
        }
    }

    private fun spawnChair(world: World?, player: PlayerEntity?, pos: BlockPos?, yOffset: Double, comparePos: Vec3d): ActionResult {
        val chair = EntityTypeRegistry.CHAIR_ENTITY.create(world)
        if (chair != null) {
            val newPos = Vec3d(pos?.x!! + 0.5, pos.y + yOffset, pos.z + 0.5)
            OCCUPIED.put(comparePos, player?.blockPos!!)
            chair.updatePosition(newPos.x, newPos.y, newPos.z)
            world?.spawnEntity(chair)
            player.startRiding(chair)

            return ActionResult.SUCCESS
        }

        return ActionResult.PASS
    }
}*/
