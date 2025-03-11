package dev.galiev.sc.blocks.custom

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import java.util.stream.Stream


class DartsDesk(settings: Settings = FabricBlockSettings.of(Material.WOOD).strength(1.0f).nonOpaque()) : Block(settings), Waterloggable {

    companion object {
        val FACING: DirectionProperty = Properties.HORIZONTAL_FACING
        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED
    }

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.NORTH)).with(WATERLOGGED, false)
    }

    private val SHAPE_N = Stream.of(
        createCuboidShape(0.5, 5.5, 15.1, 1.0, 10.5, 16.1),
        createCuboidShape(1.0, 4.5, 15.1, 1.5, 11.5, 16.1),
        createCuboidShape(13.5, 3.0, 15.1, 14.0, 13.0, 16.1),
        createCuboidShape(2.0, 3.0, 15.1, 2.5, 13.0, 16.1),
        createCuboidShape(1.5, 3.5, 15.1, 2.0, 12.5, 16.1),
        createCuboidShape(14.0, 3.5, 15.1, 14.5, 12.5, 16.1),
        createCuboidShape(14.5, 4.5, 15.1, 15.0, 11.5, 16.1),
        createCuboidShape(15.0, 5.5, 15.1, 15.5, 10.5, 16.1),
        createCuboidShape(3.0, 2.0, 15.1, 3.5, 14.0, 16.1),
        createCuboidShape(2.5, 2.5, 15.1, 3.0, 13.5, 16.1),
        createCuboidShape(13.0, 2.5, 15.1, 13.5, 13.5, 16.1),
        createCuboidShape(12.5, 2.0, 15.1, 13.0, 14.0, 16.1),
        createCuboidShape(11.5, 1.5, 15.1, 12.5, 14.5, 16.1),
        createCuboidShape(3.5, 1.5, 15.1, 4.5, 14.5, 16.1),
        createCuboidShape(4.5, 1.0, 15.1, 5.5, 15.0, 16.1),
        createCuboidShape(10.5, 1.0, 15.1, 11.5, 15.0, 16.1),
        createCuboidShape(5.5, 0.5, 15.1, 10.5, 15.5, 16.1)
    ).reduce { v1, v2 -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }.get()

    private val SHAPE_W = Stream.of(
        createCuboidShape(15.1, 5.5, 15.0, 16.1, 10.5, 15.5),
        createCuboidShape(15.1, 4.5, 14.5, 16.1, 11.5, 15.0),
        createCuboidShape(15.1, 3.0, 2.0, 16.1, 13.0, 2.5),
        createCuboidShape(15.1, 3.0, 13.5, 16.1, 13.0, 14.0),
        createCuboidShape(15.1, 3.5, 14.0, 16.1, 12.5, 14.5),
        createCuboidShape(15.1, 3.5, 1.5, 16.1, 12.5, 2.0),
        createCuboidShape(15.1, 4.5, 1.0, 16.1, 11.5, 1.5),
        createCuboidShape(15.1, 5.5, 0.5, 16.1, 10.5, 1.0),
        createCuboidShape(15.1, 2.0, 12.5, 16.1, 14.0, 13.0),
        createCuboidShape(15.1, 2.5, 13.0, 16.1, 13.5, 13.5),
        createCuboidShape(15.1, 2.5, 2.5, 16.1, 13.5, 3.0),
        createCuboidShape(15.1, 2.0, 3.0, 16.1, 14.0, 3.5),
        createCuboidShape(15.1, 1.5, 3.5, 16.1, 14.5, 4.5),
        createCuboidShape(15.1, 1.5, 11.5, 16.1, 14.5, 12.5),
        createCuboidShape(15.1, 1.0, 10.5, 16.1, 15.0, 11.5),
        createCuboidShape(15.1, 1.0, 4.5, 16.1, 15.0, 5.5),
        createCuboidShape(15.1, 0.5, 5.5, 16.1, 15.5, 10.5)
    ).reduce { v1, v2 -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }.get()

    private val SHAPE_S = Stream.of(
        createCuboidShape(15.0, 5.5, 0.0, 15.5, 10.5, 0.8),
        createCuboidShape(14.5, 4.5, 0.0, 15.0, 11.5, 0.8),
        createCuboidShape(2.0, 3.0, 0.0, 2.5, 13.0, 0.8),
        createCuboidShape(13.5, 3.0, 0.0, 14.0, 13.0, 0.8),
        createCuboidShape(14.0, 3.5, 0.0, 14.5, 12.5, 0.8),
        createCuboidShape(1.5, 3.5, 0.0, 2.0, 12.5, 0.8),
        createCuboidShape(1.0, 4.5, 0.0, 1.5, 11.5, 0.8),
        createCuboidShape(0.5, 5.5, 0.0, 1.0, 10.5, 0.8),
        createCuboidShape(12.5, 2.0, 0.0, 13.0, 14.0, 0.8),
        createCuboidShape(13.0, 2.5, 0.0, 13.5, 13.5, 0.8),
        createCuboidShape(2.5, 2.5, 0.0, 3.0, 13.5, 0.8),
        createCuboidShape(3.0, 2.0, 0.0, 3.5, 14.0, 0.8),
        createCuboidShape(3.5, 1.5, 0.0, 4.5, 14.5, 0.8),
        createCuboidShape(11.5, 1.5, 0.0, 12.5, 14.5, 0.8),
        createCuboidShape(10.5, 1.0, 0.0, 11.5, 15.0, 0.8),
        createCuboidShape(4.5, 1.0, 0.0, 5.5, 15.0, 0.8),
        createCuboidShape(5.5, 0.5, 0.0, 10.5, 15.5, 0.8)
    ).reduce { v1, v2 -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }.get()

    private val SHAPE_E = Stream.of(
        createCuboidShape(0.0, 5.5, 0.5, 0.8, 10.5, 1.0),
        createCuboidShape(0.0, 4.5, 1.0, 0.8, 11.5, 1.5),
        createCuboidShape(0.0, 3.0, 13.5, 0.8, 13.0, 14.0),
        createCuboidShape(0.0, 3.0, 2.0, 0.8, 13.0, 2.5),
        createCuboidShape(0.0, 3.5, 1.5, 0.8, 12.5, 2.0),
        createCuboidShape(0.0, 3.5, 14.0, 0.8, 12.5, 14.5),
        createCuboidShape(0.0, 4.5, 14.5, 0.8, 11.5, 15.0),
        createCuboidShape(0.0, 5.5, 15.0, 0.8, 10.5, 15.5),
        createCuboidShape(0.0, 2.0, 3.0, 0.8, 14.0, 3.5),
        createCuboidShape(0.0, 2.5, 2.5, 0.8, 13.5, 3.0),
        createCuboidShape(0.0, 2.5, 13.0, 0.8, 13.5, 13.5),
        createCuboidShape(0.0, 2.0, 12.5, 0.8, 14.0, 13.0),
        createCuboidShape(0.0, 1.5, 11.5, 0.8, 14.5, 12.5),
        createCuboidShape(0.0, 1.5, 3.5, 0.8, 14.5, 4.5),
        createCuboidShape(0.0, 1.0, 4.5, 0.8, 15.0, 5.5),
        createCuboidShape(0.0, 1.0, 10.5, 0.8, 15.0, 11.5),
        createCuboidShape(0.0, 0.5, 5.5, 0.8, 15.5, 10.5)
    ).reduce { v1, v2 -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }.get()

    @Deprecated("Deprecated in Java")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return when (state?.get(FACING)) {
            Direction.DOWN -> SHAPE_N
            Direction.UP -> SHAPE_N
            Direction.NORTH -> SHAPE_N
            Direction.SOUTH -> SHAPE_S
            Direction.WEST -> SHAPE_W
            Direction.EAST -> SHAPE_E
            null -> SHAPE_N
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
}