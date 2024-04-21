package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderable
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.util.math.Box
import kakkoiichris.hypergame.view.View
import java.awt.Color

abstract class Module : Box(), Renderable {
    var parent: Module? = null

    var children = mutableListOf<Module>()

    var id = ""

    var padding = 0U
        set(value) {
            field = value
            paddingTop = value
            paddingRight = value
            paddingBottom = value
            paddingLeft = value
        }

    val paddingVertical get() = paddingTop + paddingBottom
    val paddingHorizontal get() = paddingLeft + paddingRight

    var paddingTop = 0U
    var paddingRight = 0U
    var paddingBottom = 0U
    var paddingLeft = 0U

    var margin = 0U
        set(value) {
            field = value
            marginTop = value
            marginRight = value
            marginBottom = value
            marginLeft = value
        }

    val marginVertical get() = marginTop + marginBottom
    val marginHorizontal get() = marginLeft + marginRight

    var marginTop = 0U
    var marginRight = 0U
    var marginBottom = 0U
    var marginLeft = 0U

    var foreground = Color.BLACK
    var background = Color.WHITE
    var accent = Color.GRAY

    fun setPaddings(top: UInt = 0U, right: UInt = 0U, bottom: UInt = 0U, left: UInt = 0U) {
        paddingTop = top
        paddingRight = right
        paddingBottom = bottom
        paddingLeft = left
    }

    fun setMargins(top: UInt = 0U, right: UInt = 0U, bottom: UInt = 0U, left: UInt = 0U) {
        marginTop = top
        marginRight = right
        marginBottom = bottom
        marginLeft = left
    }

    fun add(module: Module, option: UInt = 0U) {
        module.parent = this

        if (option == 0U) {
            children += module
        }
        else {
            children[option.toInt() - 1] = module
        }
    }

    fun remove(module: Module) =
        children.remove(module)

    fun find(id: String): Module? {
        if (this.id == id) return this

        if (children.isEmpty()) return null

        for (child in children) {
            return child.find(id) ?: continue
        }

        return null
    }

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        for (child in children) {
            child.update(view, manager, time, input)
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = background

        renderer.fillRect(this)

        renderer.color = foreground

        renderer.drawRect(this)

        renderer.color = accent

        renderer.drawRect(
            (left - marginLeft.toDouble()).toInt(),
            (top - marginTop.toDouble()).toInt(),
            (width + marginLeft.toDouble() + marginRight.toDouble()).toInt(),
            (height + marginTop.toDouble() + marginBottom.toDouble()).toInt()
        )

        for (child in children) {
            child.render(view, renderer)
        }
    }
}
